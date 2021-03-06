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

class TotalsServiceTests extends GrailsUnitTestCase {
    
    def totalsService

    protected void setUp() {
        super.setUp()

        mockLogging(TotalsService, true)
        mockLogging(PublishersService, true)

	mockDomain(Member)
	mockDomain(ServiceReport)
        mockDomain(ActivePublisherCount)
        mockDomain(ServiceReportTotals)

	totalsService = new TotalsService()
	totalsService.publishersService = new PublishersService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testBuild() {
        ServiceReportTotalsTableDto serviceReportTotals = totalsService.build(3, 2009)
        assertNotNull serviceReportTotals
        assertNotNull serviceReportTotals.rows
        assertNotNull serviceReportTotals.activePubCount
        assertEquals 6, serviceReportTotals.activePubCount
        assertNotNull serviceReportTotals.rows[3]
        assertEquals 161, serviceReportTotals.rows[3].hours
    }
}
