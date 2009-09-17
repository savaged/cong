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

class MemberController {
    
    def scaffold = true

    def search = {
        if (request.method == 'POST') {
            log.debug "Searching using lastname:${params?.lastname} & firstname:${params?.firstname}"
            def member = Member.findByLastnameLikeAndFirstnameLike(params?.lastname, params?.firstname)
            redirect(uri:"/member/show/${member?.id}")
        }
    }

    def inactive = {
        if (request.method == 'POST') {
            def from = DateUtils.convert(
                Integer.parseInt(params.starting_year),
                Integer.parseInt(params.starting_month))
            
	    def publishers = Member.findAllByIsPublisher(true)

	    log.debug "filtering out long term inactive from the list of [${publishers.size()}] publishers"

	    def publishersNotIncludingLongTermInactive = []
	    def fromLessOneYear = from - 1000
	    
	    publishers.each { publisher ->

	        log.debug "checking for inactive state(s) for [${publisher}]"

		def isLongTermInactive = false
		publisher?.states.each {

		    log.debug "checking [${it}] state for inactive type"
		    
		    if (it.name == States.INACTIVE) {

			log.debug "checking if inactive state has not ended"

			if (!it.ended) {
			    def cal = Calendar.instance
			    cal.setTime it.starting
			
			    log.debug "checking if the open inactive state started more than a year ago"

			    def starting = DateUtils.convert(
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH))
			    if (starting < fromLessOneYear) {

				log.debug "the open inactive state started more than a year ago, and is therefore flagged long term inactive"

				isLongTermInactive = true
			    }
			}
		    }
		}
		if (!isLongTermInactive) {
				
		    log.debug "the member is not long term inactive, therefore is added for further checking"
			
		    publishersNotIncludingLongTermInactive << publisher
		}
	    }
	    log.debug "filtered out long term inactive from the list of publishers, leaving [${publishersNotIncludingLongTermInactive.size()}]"

	    def inactiveMembers = []
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

	    render view:'list', model:[
	        memberInstanceList:inactiveMembers, 
		memberInstanceTotal:inactiveMembers.size()]
	}
    }

}
