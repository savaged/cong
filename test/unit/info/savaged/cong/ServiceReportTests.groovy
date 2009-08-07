package info.savaged.cong

import grails.test.*

class ServiceReportTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(ServiceReport)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
        def serviceReport = new ServiceReport()
        assertFalse 'validation should have failed', serviceReport.validate()
        assertEquals 'hours is null', 'nullable', serviceReport.errors.hours
        assertEquals 'publisher is null', 'nullable', serviceReport.errors.publisher
        assertEquals 'month is null', 'nullable', serviceReport.errors.month
        assertEquals 'year is null', 'nullable', serviceReport.errors.year

        assertNull 'isAuxPioneer should not have an error', serviceReport.errors.isAuxPioneer
        assertNull 'books should not have an error', serviceReport.errors.books
        assertNull 'brochures should not have an error', serviceReport.errors.brochures
        assertNull 'magazines should not have an error', serviceReport.errors.magazines
        assertNull 'returnVisits should not have an error', serviceReport.errors.returnVisits
        assertNull 'studies should not have an error', serviceReport.errors.studies
        assertNull 'comments should not have an error', serviceReport.errors.comments
        
        serviceReport.hours = 0.4
        assertFalse 'validation should have failed', serviceReport.validate()
        assertEquals 'hours is below minimum', 'min', serviceReport.errors.hours
    }
}
