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

import grails.test.*

class MonthServiceReportTotalsServiceTests extends GrailsUnitTestCase {
    
    def monthServiceReportTotalsService

    protected void setUp() {
        super.setUp()

        mockLogging(MonthServiceReportTotalsService, true)

        def activePublisherCounts = [
            new ActivePublisherCount(yyyymm:200905, publishers:4)
        ]
        mockDomain(ActivePublisherCount, activePublisherCounts)

        def serviceReportTotals = [
            new ServiceReportTotals(category:Categories.PUBLISHERS, yyyymm:200905, publishers:2, books:1, brochures:2, hours:25, magazines:20, returnVisits:22, studies:2),
            new ServiceReportTotals(category:Categories.AUXILIARY_PIONEERS, yyyymm:200905, publishers:1, hours:50, magazines:10, returnVisits:28),
            new ServiceReportTotals(category:Categories.REGULAR_PIONEERS, yyyymm:200905, publishers:1, books:1, hours:75, magazines:40, returnVisits:42, studies:3)
        ]
        mockDomain(ServiceReportTotals, serviceReportTotals)

        monthServiceReportTotalsService = new MonthServiceReportTotalsService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testBuild() {
        ServiceReportTotalsTableDto serviceReportTotals = monthServiceReportTotalsService.build(200905)
        assertNotNull serviceReportTotals
        assertNotNull serviceReportTotals.rows
        assertNotNull serviceReportTotals.activePubCount
        assertEquals 4, serviceReportTotals.activePubCount
        assertNotNull serviceReportTotals.rows[3]
        assertEquals 150, serviceReportTotals.rows[3].hours
    }
}
