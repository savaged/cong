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

class MemberServiceYearTotalsControllerTests extends ControllerUnitTestCase {

    protected void setUp() {
        super.setUp()
        mockLogging MemberServiceYearTotalsController, true
        mockLogging TotalsService, true
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        
        controller.with {
	    totalsService = new TotalsService()
            request.method = 'POST'
            params.with {
                lastname = 'Savage'
                firstname = 'Gaile'
		serviceYear_year = '2008'
            }
            index()
        }
        assertEquals 'show', renderArgs.view
        assertNotNull renderArgs.model
        assertNotNull renderArgs.model.serviceYearTotals
        assertEquals 'Savage, Gaile', renderArgs.model.serviceYearTotals.publisher.toString()
        assertNotNull renderArgs.model.serviceYearTotals.hours
        assertEquals 686, renderArgs.model.serviceYearTotals.hours

        controller.with {
            params.serviceYear_year = '2009'
            index()
        }
        assertNotNull renderArgs.model
        assertNotNull renderArgs.model.serviceYearTotals
        assertEquals 'Savage, Gaile', renderArgs.model.serviceYearTotals.publisher.toString()
        assertNotNull renderArgs.model.serviceYearTotals.hours
        assertEquals 100, renderArgs.model.serviceYearTotals.hours
    }
}
