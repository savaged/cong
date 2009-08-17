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
package info.savaged.cong.utils

import grails.test.*

class SplitFullnameTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExtractLastname() {
        assertEquals 'Savage', SplitFullname.extractLastname('Savage, David')
        assertEquals 'De Sousa', SplitFullname.extractLastname('De Sousa, Elsa')
    }

    void testExtractFirstname() {
        assertEquals 'David', SplitFullname.extractFirstname('Savage, David')
        assertEquals 'Elsa', SplitFullname.extractFirstname('De Sousa, Elsa')
    }
}

