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

class MemberStateTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(MemberState)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
        def memberState = new MemberState()
        assertFalse 'validation should have failed', memberState.validate()
        assertEquals 'name is null', 'nullable', memberState.errors.name
        assertEquals 'starting Date is null', 'nullable', memberState.errors.starting
        assertNull 'ending should not have an error', memberState.errors.ending
        assertEquals 'member is null', 'nullable', memberState.errors.member
    }

    void testEnded() {
	def cal = Calendar.instance
	cal.set 2008,2,1
	def memberState = new MemberState(
	    name:States.INACTIVE,
	    starting:cal.time
	)
	assertFalse 'the state should not be flagged as ended', memberState.ended
	cal.clear()
	memberState.ending = cal.time
	assertFalse 'the state should not be flagged as ended', memberState.ended
	cal.set 2009,1,1
	memberState.ending = cal.time
	assertTrue 'the state should be flagged as ended', memberState.ended
    } 
}
