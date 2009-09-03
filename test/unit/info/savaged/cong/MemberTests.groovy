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

class MemberTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(Member)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
        def member = new Member()
        member.lastname = member.firstname = ''
        assertFalse 'validation should have failed', member.validate()

        assertEquals 'firstname is blank', 'blank', member.errors.firstname
        assertEquals 'lastname is blank', 'blank', member.errors.lastname
        assertEquals 'birth is null', 'nullable', member.errors.birth

        assertNull 'isPublisher should not have an error', member.errors.isPublisher
        assertNull 'isMale should not have an error', member.errors.isMale

        assertNull 'groupUnit should not have an error', member.errors.groupUnit

        assertNull 'bibleStudyConductor should not have an error', member.errors.bibleStudyConductor
        assertNull 'pioneerSchoolDate should not have an error', member.errors.pioneerSchoolDate
        assertNull 'historisation should not have an error', member.errors.historisation
    }

    void testFullname() {
        def member = new Member()
        member.lastname = 'Savage'
        member.firstname = 'David'
        assertEquals 'Savage, David', member.fullname
    }

    void testGetEnded() {
        def memberState = new MemberState()
        assertFalse memberState.ended
 
        def cal = Calendar.instance
        cal.clear()
        memberState.ending = cal.time
        assertFalse memberState.ended
 
        cal.set 2009,8,1
        memberState.ending = cal.time
        assertTrue memberState.ended
    }
    
}
