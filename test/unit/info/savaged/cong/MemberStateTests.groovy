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
}
