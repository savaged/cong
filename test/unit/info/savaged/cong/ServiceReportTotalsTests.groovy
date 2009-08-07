package info.savaged.cong

import grails.test.*

class ServiceReportTotalsTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(ServiceReportTotals)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
        def serviceReportTotals = new ServiceReportTotals()
        assertFalse 'validation should have failed', serviceReportTotals.validate()
        assertEquals 'month is null', 'nullable', serviceReportTotals.errors.month
        assertEquals 'year is null', 'nullable', serviceReportTotals.errors.year
        assertEquals 'category is null', 'nullable', serviceReportTotals.errors.category
        
        assertNull 'books should not have an error', serviceReportTotals.errors.books
        assertNull 'brochures should not have an error', serviceReportTotals.errors.brochures
        assertNull 'magazines should not have an error', serviceReportTotals.errors.magazines
        assertNull 'returnVisits should not have an error', serviceReportTotals.errors.returnVisits
        assertNull 'studies should not have an error', serviceReportTotals.errors.studies
    }
}
