package info.savaged.cong

import grails.test.*

class MonthServiceReportTotalsControllerTests extends ControllerUnitTestCase {

    def monthServiceReportTotalsController
    
    protected void setUp() {
        super.setUp()
        mockLogging(MonthServiceReportTotalsController, true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndex() {
        controller.index()
    }

    void testShow() {
        controller.show()
    }
}
