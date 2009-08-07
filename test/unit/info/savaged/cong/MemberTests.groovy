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
}
