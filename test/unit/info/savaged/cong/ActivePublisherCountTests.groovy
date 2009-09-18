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

class ActivePublisherCountTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(ActivePublisherCount)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
        def activePublisherCount = new ActivePublisherCount()
        assertFalse 'validation should have failed', activePublisherCount.validate()
        assertEquals 'publishers is null', 'nullable', activePublisherCount.errors.publishers
        assertEquals 'yyyymm is null', 'nullable', activePublisherCount.errors.yyyymm

        activePublisherCount.publishers = 0
        assertFalse 'validation should have failed', activePublisherCount.validate()
        assertEquals 'publishers is below minimum', 'min', activePublisherCount.errors.publishers
    }
}
