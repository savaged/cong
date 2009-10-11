/*
Copyright (c) 2009 David Savage.

This file is part of "cong".

cong is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

cong is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with cong.  If not, see <http://www.gnu.org/licenses/>.
*/
package info.savaged.cong

import info.savaged.cong.utils.DateUtils

class PublishersService {

    boolean transactional = true

    List loadActive() {
	def publishers = Member.findAllByIsPublisher(true)
	def activePublishers = publishers.findAll { !it.isInactive }
	activePublishers
    }

    List loadInactive(Integer month, Integer year) {

	def from = DateUtils.convert(year, month)

	def publishers = Member.findAllByIsPublisher(true)

	log.debug "filtering out long term inactive from the list of [${publishers.size()}] publishers"

	def publishersNotIncludingLongTermInactive = []
	def fromLessOneYear = from - 1000
	
	publishers.each { publisher ->

	    log.debug "checking for inactive state for [${publisher}]"

	    def isLongTermInactive = false

	    if (publisher.isInactive) {

		log.debug "[${publisher}] is flagged inactive, now checking if the open inactive state started more than a year ago"

		def cal = Calendar.instance
		cal.setTime publisher.inactiveStarting
		def starting = DateUtils.convert(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH))
		if (starting < fromLessOneYear) {
		    log.debug "the open inactive state started more than a year ago, and is therefore flagged long term inactive"
		    isLongTermInactive = true
		}
	    }
	    if (!isLongTermInactive) {
			    
		log.debug "the member is not long term inactive, therefore is added for further checking"
		publishersNotIncludingLongTermInactive << publisher
	    }
	}
	log.debug "filtered out long term inactive from the list of publishers, leaving [${publishersNotIncludingLongTermInactive.size()}]"

	def inactiveMembers = []

	if (ServiceReport.findAll()) {
	    log.debug 'this is a new and empty database therefore returning nothing'
	    inactiveMembers
	}

	def previousMonthsRange = DateUtils.previousMonthsRange(6, from)

	log.debug "counting any not reporting in the previous six months since [${from}], from the list of publishers"

	publishersNotIncludingLongTermInactive.each {
	    def hours = 0
	    it?.serviceReports.each {
		if (previousMonthsRange.contains(it.yyyymm)) {
		    hours += it.hours
		}
	    }
	    if (hours < 1) {
	
		log.debug "[${it}] has not reported in the previous six months since [${from}] and is therefore added to the inactive list"

		inactiveMembers << it
	    }
	}	    
	log.debug "counted [${inactiveMembers.size()}] not reporting in the previous six months since [${from}]"
	
	inactiveMembers
    }

    private Integer retrieveActiveBaptizedPublisherCount(List activePublishers) {
        activePublishers.findAll({it.baptized}).size()
    }

    void persistActivePublisherCounts(Integer yyyymm) {

        log.debug "Persisting active publisher count for [${yyyymm}]..."

	def activePublishers = loadActive()
	def activeBaptizedPublisherCount = retrieveActiveBaptizedPublisherCount(activePublishers)

        def activePublisherCount
	activePublisherCount = ActivePublisherCount.findByYyyymm(yyyymm)
	if (activePublisherCount) {
	    activePublisherCount.publishers = activePublishers.size()
	    activePublisherCount.baptizedPublishers = activeBaptizedPublisherCount
	} else {
	    activePublisherCount = new ActivePublisherCount(
		yyyymm:yyyymm, 
		publishers:activePublishers.size(),
		baptizedPublishers:activeBaptizedPublisherCount)
        }
        if (activePublisherCount.save(flush:true)) {
            log.debug(
		"Persisting active publisher total of ${activePublisherCount.publishers} for ${activePublisherCount.yyyymm}"
            )
        } else {
            activePublisherCount.errors.each {
                log.debug "failed to save ${activePublisherCount} due to: ${it}"
            }
        }
    }
}
