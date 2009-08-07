package info.savaged.cong

import grails.test.*

class MonthServiceReportTotalsServiceTests extends GrailsUnitTestCase {
    
    def monthServiceReportTotalsService

    protected void setUp() {
        super.setUp()

        mockLogging(MonthServiceReportTotalsService, true)

        def activePublisherCounts = [
            new ActivePublisherCount(month:5, year:2009, publishers:4)
        ]
        mockDomain(ActivePublisherCount, activePublisherCounts)

        def serviceReportTotals = [
            new ServiceReportTotals(category:Categories.PUBLISHERS, month:5, year:2009, publishers:2, books:1, brochures:2, hours:25, magazines:20, returnVisits:22, studies:2),
            new ServiceReportTotals(category:Categories.AUXILIARY_PIONEERS, month:5, year:2009, publishers:1, hours:50, magazines:10, returnVisits:28),
            new ServiceReportTotals(category:Categories.REGULAR_PIONEERS, month:5, year:2009, publishers:1, books:1, hours:75, magazines:40, returnVisits:42, studies:3)
        ]
        mockDomain(ServiceReportTotals, serviceReportTotals)

        monthServiceReportTotalsService = new MonthServiceReportTotalsService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testBuild() {
        ServiceReportTotalsTableDto serviceReportTotals = monthServiceReportTotalsService.build(5, 2009)
        assertNotNull serviceReportTotals
        assertNotNull serviceReportTotals.rows
        assertNotNull serviceReportTotals.activePubCount
        assertEquals 4, serviceReportTotals.activePubCount
        assertNotNull serviceReportTotals.rows[3]
        assertEquals 150, serviceReportTotals.rows[3].hours
    }
}
