package info.savaged.cong

import org.springframework.util.Assert

class MonthServiceReportTotalsService {

    ServiceReportTotalsTableDto build(Integer month, Integer year) {

        log.debug """Building service report totals and active publisher count for ${month}/${year}..."""

        def table = new ServiceReportTotalsTableDto()
        def activePublisherCount = ActivePublisherCount.findByMonthAndYear(month, year)
        if (!activePublisherCount) {
            log.debug """No active publishers count found for ${month}/${year}"""
        } else {
            table.activePubCount = activePublisherCount.publishers
        }
        def publishersRow // = ServiceReportTotals.findByCategoryAndMonthAndYear(Categories.PUBLISHERS.toString(), month, year)
        def auxPioneersRow // = ServiceReportTotals.findByCategoryAndMonthAndYear(Categories.AUXILIARY_PIONEERS.toString(), month, year)
        def regPioneersRow // = ServiceReportTotals.findByCategoryAndMonthAndYear(Categories.REGULAR_PIONEERS.toString(), month, year)
        def serviceReportTotals = ServiceReportTotals.findAllByMonthAndYear(month, year)
        
        if (serviceReportTotals.size() <= 0) {
            log.debug """No service report totals found for ${month}/${year}"""
        } else {

            log.debug """summing ${serviceReportTotals.size()} service report totals}"""
        
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
