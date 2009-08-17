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
    }
}
