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
import java.text.SimpleDateFormat

class MemberControllerTests extends ControllerUnitTestCase {

    protected void setUp() {
        super.setUp()
        // BootStrap has all the data loaded already
        mockLogging(MemberController, true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSearch() {
        // Setting up for the work-around below
        def redirectParams
        controller.class.metaClass.redirect = { Map args -> redirectParams = args }
        
        controller.with {
            request.method = 'POST'
            params.with {
                lastname = 'Savage'
                firstname = 'David'
            }
            search()
        }
        // Below is a work-around because the following doesn't work:
        // assertEquals '/member/show', controller.response.redirectedUrl
        // See http://jira.codehaus.org/browse/GRAILS-4985
        assertEquals '/member/show/1', redirectParams.uri

	controller.params.with {
	    lastname = 'Savage'
	    firstname = 'D%'
	}
	controller.search()
	assertEquals '/member/show/1', redirectParams.uri
    }

    void testInactive() {
        controller.with {
            request.method = 'POST'
            params.with {
                starting_year = '2009'
                starting_month = '8'
            }
            inactive()
        }
        assertEquals 'list', renderArgs.view
        assertEquals 1, renderArgs.model.memberInstanceTotal

	controller.params.with {
	    starting_year = '2009'
	    starting_month = '9'
	}
	controller.inactive()
        assertEquals 3, renderArgs.model.memberInstanceTotal
    }
}
