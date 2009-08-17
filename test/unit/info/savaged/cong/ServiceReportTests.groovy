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
