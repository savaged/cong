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

class MonthServiceReportTotalsService {

    boolean transactional = true

    ServiceReportTotalsTableDto build(Integer month, Integer year) {

	def yyyymm = DateUtils.convert(year, month)

        def serviceReportTotals = calc(yyyymm)
        persist(serviceReportTotals)
	load(yyyymm)
    }

    private List calc(Integer yyyymm) {

        log.debug "Calculating month totals for [${yyyymm}]..."

        def serviceReportTotals = [
            new ServiceReportTotals(category:Categories.PUBLISHERS, yyyymm:yyyymm),
            new ServiceReportTotals(category:Categories.AUXILIARY_PIONEERS, yyyymm:yyyymm),
            new ServiceReportTotals(category:Categories.REGULAR_PIONEERS, yyyymm:yyyymm)
        ]

	def serviceReports = ServiceReport.findAllByYyyymm(yyyymm)
	def activePublishers = retrieveActivePublishers(yyyymm)

        log.debug( 
	    "processing [${serviceReports?.size()}] service reports and [${activePublishers?.size()}] active publishers"
        )
        for (serviceReport in serviceReports) {

            if (activePublishers.get(serviceReport.publisher.fullname)) {

                def regPioneer = MemberState.findByNameAndMember(
		    States.REGULAR_PIONEER.toString(), serviceReport.publisher)

                if (regPioneer) {
                    if (!regPioneer.ending) {
                        updateRow(serviceReportTotals[2], serviceReport)
                        log.debug(
			    "Added service report for ${serviceReport?.publisher} as regular pioneer"
                        )
                    }
                } else {
                    if (serviceReport.isAuxPioneer) {
                        updateRow(serviceReportTotals[1], serviceReport)
                        log.debug(
			    "Added service report for  ${serviceReport?.publisher} as auxiliary pioneer"
                        )
                    } else {
                        updateRow(serviceReportTotals[0], serviceReport)
                        log.debug(
			    "Added service report for ${serviceReport?.publisher} as publisher"
                        )
                    }
                }
            } else {
		throw new Exception("No active publisher matched to service report [${serviceReport}]")
	    }
        }
	serviceReportTotals
    }

    private void updateRow(row, serviceReport) {
        row.publishers++
        row.books += (serviceReport.books ? serviceReport.books : 0)
        row.brochures += (serviceReport.brochures ? serviceReport.brochures : 0)
        row.hours += serviceReport.hours
        row.magazines += (serviceReport.magazines ? serviceReport.magazines : 0)
        row.returnVisits += (serviceReport.returnVisits ? serviceReport.returnVisits : 0)
        row.studies += (serviceReport.studies ? serviceReport.studies : 0)
    }

    private Map retrieveActivePublishers(Integer yyyymm) {
	
	def activePublishers = [:]
        
	def publishers = Member.findAllByIsPublisher(true)

        for (publisher in publishers) {
            def inactive = MemberState.findByNameAndMember(States.INACTIVE.toString(), publisher)
            if (inactive) {
                if (!inactive.ended) {
                    log.debug(
			"${publisher} not counted as active publisher due to being in an inactive state"
                    )
                    continue
                }
            }
            def disfellowshipped = MemberState.findByNameAndMember(States.DISFELLOWSHIPPED.toString(), publisher)
            if (disfellowshipped) {
                if (!disfellowshipped.ended) {
                    log.debug(
			"${publisher} not counted as active publisher due to being in a disfellowshipped state"
                    )
                    continue
                }
            }
            activePublishers.put(publisher.fullname, publisher)
            log.debug "${publisher} counted as active publisher"
        }
        activePublishers
    }

    private Integer retrieveActiveBaptizedPublisherCount(Map activePublishers) {
	
	def activeBaptizedPublisherCount = 0

        for (publisher in activePublishers.values()) {

	    if (publisher.baptized) { 
		activeBaptizedPublisherCount++
		log.debug "${publisher} counted as baptized active publisher"
	    }
	}
	activeBaptizedPublisherCount 
    }

    private void persist(List serviceReportTotals) {

        log.debug 'Persisting ' + serviceReportTotals.size() + ' service report totals...'

	def yyyymm

        for (totals in serviceReportTotals) {

	    yyyymm = totals?.yyyymm

            if (totals.save(flush:true)) {
                log.debug(
		    "Persisting service report totals: [${totals}] with [${totals?.hours}] hours, for [${yyyymm}]"
                )
            } else {
                totals.errors.each {
                    log.debug "failed to save ${totals} due to: ${it}"
                }
            }
        }

        log.debug 'Persisting active publisher count...'

	def activePublishers = retrieveActivePublishers(yyyymm)
	def activeBaptizedPublisherCount = retrieveActiveBaptizedPublisherCount(activePublishers)

        def activePublisherCount = new ActivePublisherCount(
	    yyyymm:yyyymm, 
	    publishers:activePublishers.size(),
	    baptizedPublishers:activeBaptizedPublisherCount)
        
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

    private ServiceReportTotalsTableDto load(Integer yyyymm) {

        log.debug "Loading service report totals and active publisher count for year and month [${yyyymm}]..."

        def table = new ServiceReportTotalsTableDto()
        def activePublisherCount = ActivePublisherCount.findByYyyymm(yyyymm)
        if (!activePublisherCount) {
            log.debug "No active publishers count found for year and month [${yyyymm}]"
        } else {
            table.activePubCount = activePublisherCount.publishers
        }
        def publishersRow
        def auxPioneersRow
        def regPioneersRow

        def serviceReportTotals = ServiceReportTotals.findAllByYyyymm(yyyymm)
        
        if (serviceReportTotals.size() <= 0) {
            log.debug "No service report totals found for ${yyyymm}"
        } else {

            log.debug "summing ${serviceReportTotals.size()} service report totals}"
        
            serviceReportTotals.each {
                switch (it.category) {
                    case Categories.PUBLISHERS:
                    publishersRow = it
                    case Categories.AUXILIARY_PIONEERS:
                    auxPioneersRow = it
                    case Categories.REGULAR_PIONEERS:
                    regPioneersRow = it
                }
            }

            def totalsRow = new ServiceReportTotalsRowDto()
            totalsRow.publishers = publishersRow.publishers + auxPioneersRow?.publishers + regPioneersRow?.publishers
            totalsRow.books = publishersRow?.books + auxPioneersRow?.books + regPioneersRow?.books
            totalsRow.brochures = publishersRow?.brochures + auxPioneersRow?.brochures + regPioneersRow?.brochures
            totalsRow.hours = publishersRow.hours + auxPioneersRow?.hours + regPioneersRow?.hours
            totalsRow.magazines = publishersRow?.magazines + auxPioneersRow?.magazines + regPioneersRow?.magazines
            totalsRow.returnVisits = publishersRow?.returnVisits + auxPioneersRow?.returnVisits + regPioneersRow?.returnVisits
            totalsRow.studies = publishersRow?.studies + auxPioneersRow?.studies + regPioneersRow?.studies
        
            table.rows.add(publishersRow)
            table.rows.add(auxPioneersRow)
            table.rows.add(regPioneersRow)
            table.rows.add(totalsRow)
        }
        return table
    }
}

class ServiceReportTotalsTableDto {

    def activePubCount = 0
    def rows = []
}

class ServiceReportTotalsRowDto {

    Categories category
    Integer publishers = 0
    Integer books = 0
    Integer brochures = 0
    Integer hours = 0
    Integer magazines = 0
    Integer returnVisits = 0
    Integer studies = 0
}
