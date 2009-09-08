import info.savaged.cong.*

import java.text.SimpleDateFormat
import grails.util.*

class BootStrap {

    def init = { servletContext ->
        if (Environment.current != Environment.PRODUCTION) {
            
	    log.debug 'bootstrapping a sample data-set for testing...'

            def save = {
		if (!it.validate()) {
		    it.errors.allErrors.each {
			log.debug it
		    }
		} else {
                    it.save()
		}
	    }

            def df = new SimpleDateFormat('yyyy-MM-dd')

            def members = [
                new Member(lastname:'Savage', firstname:'David', birth:df.parse('1959-12-02'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1986-07-12')),
                new Member(lastname:'Savage', firstname:'Gaile', birth:df.parse('1963-01-09'), isMale:false, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1990-09-23')),
                new Member(lastname:'Savage', firstname:'Jude', birth:df.parse('1980-01-16'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30')),
                new Member(lastname:'Savage', firstname:'Saskia', birth:df.parse('1975-04-23'), isMale:false, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30')),
                new Member(lastname:'Savage', firstname:'Kieran', birth:df.parse('1970-05-11'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30')),
                new Member(lastname:'Savage', firstname:'John', birth:df.parse('1968-05-23'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1979-12-30')),
                new Member(lastname:'Savage', firstname:'Ethan', birth:df.parse('1960-09-01'), isMale:true, isPublisher:false, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1989-12-30')),
                new Member(lastname:'Savage', firstname:'Allison', birth:df.parse('1968-02-21'), isMale:false, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1987-11-20'))
            ]
            members.each save
            
            def cal = Calendar.instance
            cal.set 2008,8,1
            def regPioneers = [
                new MemberState(name:States.REGULAR_PIONEER, starting:cal.getTime(), member:members[1])
            ]
            regPioneers.each save

            cal.set 2007,4,1
	    def servants = [
                new MemberState(name:States.SERVANT, starting:cal.getTime(), member:members[4])
            ]
            servants.each save

	    def cal1 = cal.clone()
            cal1.set 2008,1,15
	    def inactive = [
                new MemberState(name:States.INACTIVE, starting:cal.getTime(), ending:cal1.getTime(), member:members[0]),
                new MemberState(name:States.INACTIVE, starting:cal.getTime(), member:members[6])
            ]
            inactive.each save

            def serviceReports = [
                new ServiceReport(month:1, year:2009, hours:16, publisher:members[0]),

                new ServiceReport(month:1, year:2009, hours:75, publisher:members[1]),
                new ServiceReport(month:2, year:2009, hours:76, publisher:members[1]),
                new ServiceReport(month:3, year:2009, hours:75, publisher:members[1]),
                new ServiceReport(month:4, year:2009, hours:74, publisher:members[1]),
                new ServiceReport(month:5, year:2009, hours:73, publisher:members[1]),
                new ServiceReport(month:6, year:2009, hours:72, publisher:members[1]),
                new ServiceReport(month:7, year:2009, hours:71, publisher:members[1]),

                new ServiceReport(month:1, year:2009, hours:50, publisher:members[2], comments:'Aux pio'),
                new ServiceReport(month:2, year:2009, hours:16, publisher:members[2]),
                new ServiceReport(month:3, year:2009, hours:50, publisher:members[2], comments:'Aux pio'),
                new ServiceReport(month:4, year:2009, hours:14, publisher:members[2]),
                new ServiceReport(month:5, year:2009, hours:13, publisher:members[2]),
                new ServiceReport(month:6, year:2009, hours:12, publisher:members[2]),
                new ServiceReport(month:7, year:2009, hours:11, publisher:members[2]),

                new ServiceReport(month:1, year:2009, hours:15, publisher:members[3]),
                new ServiceReport(month:2, year:2009, hours:16, publisher:members[3]),
                new ServiceReport(month:3, year:2009, hours:15, publisher:members[3]),
                new ServiceReport(month:4, year:2009, hours:14, publisher:members[3]),
                new ServiceReport(month:5, year:2009, hours:13, publisher:members[3]),
                new ServiceReport(month:6, year:2009, hours:12, publisher:members[3]),
                new ServiceReport(month:7, year:2009, hours:11, publisher:members[3]),

                new ServiceReport(month:1, year:2009, hours:15, publisher:members[4]),
                new ServiceReport(month:2, year:2009, hours:16, publisher:members[4]),
                new ServiceReport(month:3, year:2009, hours:15, publisher:members[4]),
                new ServiceReport(month:4, year:2009, hours:14, publisher:members[4]),
                new ServiceReport(month:5, year:2009, hours:13, publisher:members[4]),
                new ServiceReport(month:6, year:2009, hours:12, publisher:members[4]),
                new ServiceReport(month:7, year:2009, hours:11, publisher:members[4]),
                
		new ServiceReport(month:6, year:2009, hours:1, publisher:members[5])
            ]
            serviceReports.each save

	    def activePublisherCounts = [
	        new ActivePublisherCount(month:1, year:2009, publishers:5),
	        new ActivePublisherCount(month:2, year:2009, publishers:4),
	        new ActivePublisherCount(month:3, year:2009, publishers:4),
	        new ActivePublisherCount(month:4, year:2009, publishers:4),
	        new ActivePublisherCount(month:5, year:2009, publishers:4),
	        new ActivePublisherCount(month:6, year:2009, publishers:5),
	        new ActivePublisherCount(month:7, year:2009, publishers:4)
	    ]
	    activePublisherCounts.each save

	    def serviceReportTotals = [
	        new ServiceReportTotals(month:1, year:2009, publishers:3, hours:46, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(month:1, year:2009, publishers:1, hours:50, category:Categories.AUXILIARY_PIONEERS),
	        new ServiceReportTotals(month:1, year:2009, publishers:1, hours:75, category:Categories.REGULAR_PIONEERS),
		
		new ServiceReportTotals(month:2, year:2009, publishers:3, hours:48, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(month:2, year:2009, publishers:1, hours:76, category:Categories.REGULAR_PIONEERS),
		
                new ServiceReportTotals(month:3, year:2009, publishers:2, hours:30, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(month:3, year:2009, publishers:1, hours:50, category:Categories.AUXILIARY_PIONEERS),
	        new ServiceReportTotals(month:3, year:2009, publishers:1, hours:75, category:Categories.REGULAR_PIONEERS),
		
                new ServiceReportTotals(month:4, year:2009, publishers:3, hours:42, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(month:4, year:2009, publishers:1, hours:74, category:Categories.REGULAR_PIONEERS),

                new ServiceReportTotals(month:5, year:2009, publishers:3, hours:39, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(month:5, year:2009, publishers:1, hours:73, category:Categories.REGULAR_PIONEERS),

                new ServiceReportTotals(month:6, year:2009, publishers:4, hours:37, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(month:6, year:2009, publishers:1, hours:72, category:Categories.REGULAR_PIONEERS),
	
                new ServiceReportTotals(month:7, year:2009, publishers:3, hours:33, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(month:7, year:2009, publishers:1, hours:71, category:Categories.REGULAR_PIONEERS)
	    ]
	    serviceReportTotals.each save

	    def meetingAttendances = [
	        new MeetingAttendance(month:1, year:2009, meeting:Meeting.CBS, meetings:3, total:280),
	        new MeetingAttendance(month:2, year:2009, meeting:Meeting.CBS, meetings:4, total:360),
	        new MeetingAttendance(month:3, year:2009, meeting:Meeting.CBS, meetings:4, total:360),
	        new MeetingAttendance(month:4, year:2009, meeting:Meeting.CBS, meetings:5, total:420),
	        new MeetingAttendance(month:5, year:2009, meeting:Meeting.CBS, meetings:4, total:360),
	        new MeetingAttendance(month:6, year:2009, meeting:Meeting.CBS, meetings:4, total:360),
	        new MeetingAttendance(month:7, year:2009, meeting:Meeting.CBS, meetings:4, total:360),

		new MeetingAttendance(month:1, year:2009, meeting:Meeting.SM, meetings:3, total:280),
	        new MeetingAttendance(month:2, year:2009, meeting:Meeting.SM, meetings:4, total:360),
	        new MeetingAttendance(month:3, year:2009, meeting:Meeting.SM, meetings:4, total:360),
	        new MeetingAttendance(month:4, year:2009, meeting:Meeting.SM, meetings:5, total:420),
	        new MeetingAttendance(month:5, year:2009, meeting:Meeting.SM, meetings:4, total:360),
	        new MeetingAttendance(month:6, year:2009, meeting:Meeting.SM, meetings:4, total:360),
	        new MeetingAttendance(month:7, year:2009, meeting:Meeting.SM, meetings:4, total:360),

                new MeetingAttendance(month:1, year:2009, meeting:Meeting.PT, meetings:3, total:280),
	        new MeetingAttendance(month:2, year:2009, meeting:Meeting.PT, meetings:4, total:360),
	        new MeetingAttendance(month:3, year:2009, meeting:Meeting.PT, meetings:4, total:360),
	        new MeetingAttendance(month:4, year:2009, meeting:Meeting.PT, meetings:5, total:420),
	        new MeetingAttendance(month:5, year:2009, meeting:Meeting.PT, meetings:4, total:360),
	        new MeetingAttendance(month:6, year:2009, meeting:Meeting.PT, meetings:4, total:360),
	        new MeetingAttendance(month:7, year:2009, meeting:Meeting.PT, meetings:4, total:360),

		new MeetingAttendance(month:1, year:2009, meeting:Meeting.WT, meetings:3, total:280),
	        new MeetingAttendance(month:2, year:2009, meeting:Meeting.WT, meetings:4, total:360),
	        new MeetingAttendance(month:3, year:2009, meeting:Meeting.WT, meetings:4, total:360),
	        new MeetingAttendance(month:4, year:2009, meeting:Meeting.WT, meetings:5, total:420),
	        new MeetingAttendance(month:5, year:2009, meeting:Meeting.WT, meetings:4, total:360),
	        new MeetingAttendance(month:6, year:2009, meeting:Meeting.WT, meetings:4, total:360),
	        new MeetingAttendance(month:7, year:2009, meeting:Meeting.WT, meetings:4, total:360)
	    ]
	    meetingAttendances.each save
        }

	log.debug 'bootstrapping complete'
    }
    
    def destroy = {
    }
}
