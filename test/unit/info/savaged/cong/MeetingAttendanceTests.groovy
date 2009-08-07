package info.savaged.cong

import grails.test.*

class MeetingAttendanceTests extends GrailsUnitTestCase {

    def meetingAttendance

    protected void setUp() {
        super.setUp()
        mockDomain(MeetingAttendance)
        meetingAttendance = new MeetingAttendance()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
        assertFalse 'validation should have failed', meetingAttendance.validate()
        assertEquals 'meeting is null', 'nullable', meetingAttendance.errors.meeting
        assertEquals 'meeting is null', 'nullable', meetingAttendance.errors.meeting
        assertEquals 'meetings is null', 'nullable', meetingAttendance.errors.meetings
        assertEquals 'total is null', 'nullable', meetingAttendance.errors.total
        assertEquals 'month is null', 'nullable', meetingAttendance.errors.month
        assertEquals 'year is null', 'nullable', meetingAttendance.errors.year
    }

    void testGetAverage() {
        meetingAttendance.meetings = 4
        meetingAttendance.total = 399
        assertEquals 'average should calculate correctly', 100, meetingAttendance.getAverage()
    }
}
