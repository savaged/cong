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

import org.springframework.util.Assert

class MonthServiceReportTotalsService {

    ServiceReportTotalsTableDto build(Integer yyyymm) {

        log.debug "Building service report totals and active publisher count for ${yyyymm}..."

        def table = new ServiceReportTotalsTableDto()
        def activePublisherCount = ActivePublisherCount.findByYyyymm(yyyymm)
        if (!activePublisherCount) {
            log.debug "No active publishers count found for ${yyyymm}"
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
