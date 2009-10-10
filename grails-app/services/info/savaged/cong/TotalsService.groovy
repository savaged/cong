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

class TotalsService {

    def publishersService

    boolean transactional = true

    Map build(String lastname, String firstname, Integer serviceYear) {
	def member = Member.findByLastnameLikeAndFirstnameLike(lastname, firstname)
	    
        def serviceYearStart = DateUtils.convert(serviceYear, 9)
	    
	    def serviceReports = member?.serviceReports.findAll( {
		it.yyyymm >= serviceYearStart
	    } )
	    
	    def serviceYearTotals = [
		publisher:member,
		books:0,
		brochures:0,
		hours:0,
	        magazines:0,
	        returnVisits:0,
	        studies:0
	    ]
	    serviceReports.each {
	    serviceYearTotals.books += (it.books ? it.books : 0)
	    serviceYearTotals.brochures += (it.brochures ? it.brochures : 0)
	    serviceYearTotals.hours += it.hours 
	    serviceYearTotals.magazines += (it.magazines ? it.magazines : 0)
            serviceYearTotals.returnVisits += (it.returnVisits ? it.returnVisits : 0)
            serviceYearTotals.studies += (it.studies ? it.studies : 0)
	}
	serviceYearTotals
    }

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
	def activePublishers = publishersService.loadActive() 

        log.debug( 
	    "processing [${serviceReports?.size()}] service reports and [${activePublishers?.size()}] active publishers"
        )
        for (serviceReport in serviceReports) {

	    def matching = activePublishers.findAll {
		it.fullname == serviceReport.publisher.fullname
	    }

            if (matching && matching[0]) {

                if (serviceReport.publisher.isRegularPioneer) {
		    updateRow(serviceReportTotals[2], serviceReport)
		    log.debug(
			"Added service report for ${serviceReport?.publisher} as regular pioneer"
		    )
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

    private void persist(List serviceReportTotals) {

        log.debug "Persisting [${serviceReportTotals.size()}] service report totals..."

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
        publishersService.persistActivePublisherCounts(yyyymm)
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
