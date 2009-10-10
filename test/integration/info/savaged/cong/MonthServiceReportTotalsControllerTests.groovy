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

class MonthServiceReportTotalsControllerTests extends ControllerUnitTestCase {

    protected void setUp() {
        super.setUp()
        // BootStrap has all the data loaded already
        mockLogging(TotalsService, true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        controller.with {
	    totalsService = new TotalsService()
	    totalsService.publishersService = new PublishersService()
            request.method = 'POST'
            params.with {
                starting_year = '2009'
                starting_month = '7'
            }
            index()
        }
        assertEquals 'show', renderArgs.view
        assertEquals 4, renderArgs.model.serviceReportTotals.rows.size()
        assertEquals Categories.PUBLISHERS, renderArgs.model.serviceReportTotals.rows[0].category
        assertEquals Categories.AUXILIARY_PIONEERS, renderArgs.model.serviceReportTotals.rows[1].category
        assertEquals Categories.REGULAR_PIONEERS, renderArgs.model.serviceReportTotals.rows[2].category
        assertEquals 104, renderArgs.model.serviceReportTotals.rows[3].hours
        assertEquals 7, renderArgs.model.serviceReportTotals.activePubCount 
    }

    void testShow() {
        controller.show()
    }
}
