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
        mockLogging(PublishersService, true)

	mockDomain(Member)
	mockDomain(ServiceReport)
        mockDomain(ActivePublisherCount)
        mockDomain(ServiceReportTotals)

	monthServiceReportTotalsService = new MonthServiceReportTotalsService()
	monthServiceReportTotalsService.publishersService = new PublishersService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testBuild() {
        ServiceReportTotalsTableDto serviceReportTotals = monthServiceReportTotalsService.build(5, 2009)
        assertNotNull serviceReportTotals
        assertNotNull serviceReportTotals.rows
        assertNotNull serviceReportTotals.activePubCount
        assertEquals 0, serviceReportTotals.activePubCount
        assertNotNull serviceReportTotals.rows[3]
        assertEquals 0, serviceReportTotals.rows[3].hours
    }
}
