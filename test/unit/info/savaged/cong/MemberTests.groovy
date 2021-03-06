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

    void testBaptized() {
	def member = new Member()
        member.lastname = member.firstname = ''
	assertFalse 'the baptized flag should be derived as false', member.baptized

        def dob = Calendar.instance
	dob.set 1968, 9, 30
	def bap = Calendar.instance
	bap.set 1987, 6, 12
	def anotherMember = new Member(
	    lastname:'Savage',
	    firstname:'David',
	    birth:dob.time,
	    immersion:bap.time
	)
	assertTrue 'the baptized flag should be derived as true', anotherMember.baptized
    }

    void testConstraints() {
        def member = new Member()
        member.lastname = member.firstname = ''
        assertFalse 'validation should have failed', member.validate()

        assertEquals 'firstname is blank', 'blank', member.errors.firstname
        assertEquals 'lastname is blank', 'blank', member.errors.lastname
        assertEquals 'birth is null', 'nullable', member.errors.birth

        assertNull 'groupUnit should not have an error', member.errors.groupUnit
        assertNull 'immersion should not have an error', member.errors.immersion

        def dob = Calendar.instance
	dob.set 1968, 9, 30
	def bap = Calendar.instance
	bap.set 1987, 6, 12
	def anotherMember = new Member(
	    lastname:'Savage',
	    firstname:'David',
	    birth:dob.time,
	    immersion:bap.time,
	    isElder:true,
	    isMale:true,
	    isPublisher:true,
	    isInactive:true
	)
	assertFalse 'validation should have failed', anotherMember.validate()

	anotherMember = new Member(
	    lastname:'Savage',
	    firstname:'David',
	    birth:dob.time,
	    immersion:bap.time,
	    isElder:true,
	    isMale:true,
	    isPublisher:true
	)
	anotherMember.validate()
	println anotherMember.errors
	assertTrue 'validation should not have failed', anotherMember.validate()

	anotherMember = new Member(
	    lastname:'Savage',
	    firstname:'David',
	    birth:dob.time,
	    immersion:bap.time,
	    isElder:true,
	    isMale:true,
	    isPublisher:true,
	    isInactive:true,
	    inactiveStarting:Calendar.instance.time
	)
	assertTrue 'validation should not have failed', anotherMember.validate()
	anotherMember.save()

	def dupe = new Member(
	    lastname:'Savage', 
	    firstname:'David', 
	    birth:dob.time, 
	    immersion:bap.time,
	    isElder:true,
	    isMale:true,
	    isPublisher:true,
	    isInactive:true,
	    inactiveStarting:Calendar.instance.time
	)
        assertFalse 'validation should have failed', dupe.validate()
        assertEquals 'birth, lastname & firstname not unique', 'unique', dupe.errors.birth
    }

    void testFullname() {
        def member = new Member()
        member.lastname = 'Savage'
        member.firstname = 'David'
        assertEquals 'Savage, David', member.fullname
    }
}
