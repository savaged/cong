import info.savaged.cong.*

import java.text.SimpleDateFormat
import grails.util.*

class BootStrap {

    def init = { servletContext ->
        if (Environment.current != Environment.PRODUCTION &&
	    Environment.current != Environment.CUSTOM) { 
            
	    log.debug "bootstrapping a sample data-set for testing, for the [${Environment.current}] environment..."

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
                new Member(lastname:'Savage', firstname:'David', birth:df.parse('1959-12-02'), isElder:true, isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1986-07-12')),
                new Member(lastname:'Savage', firstname:'Gaile', birth:df.parse('1963-01-09'), isRegularPioneer:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1990-09-23')),
                new Member(lastname:'Savage', firstname:'Jude', birth:df.parse('1980-01-16'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1914-01-01')),
                new Member(lastname:'Savage', firstname:'Saskia', birth:df.parse('1975-04-23'), isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1914-01-01')),
                new Member(lastname:'Savage', firstname:'Kieran', birth:df.parse('1970-05-11'), isServant:true, isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1914-01-01')),
                new Member(lastname:'Savage', firstname:'John', birth:df.parse('1968-05-23'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1979-12-30')),
                new Member(lastname:'Savage', firstname:'Ethan', birth:df.parse('1960-09-01'), isMale:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1989-12-30'), inactiveStarting:df.parse('2007-04-01')),
                new Member(lastname:'Savage', firstname:'Allison', birth:df.parse('1968-02-21'), isInactive:true, inactiveStarting:df.parse('2007-04-01'), isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1987-11-20')),
                new Member(lastname:'Savage', firstname:'Dave', birth:df.parse('1969-09-30'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1986-04-10'))
            ]
            members.each save
          
            def serviceReports = [
                new ServiceReport(yyyymm:200901, hours:16, publisher:members[0]),
                new ServiceReport(yyyymm:200902, hours:6, publisher:members[0]),
                new ServiceReport(yyyymm:200903, hours:5, publisher:members[0]),

                new ServiceReport(yyyymm:200901, hours:75, publisher:members[1]),
                new ServiceReport(yyyymm:200902, hours:76, publisher:members[1]),
                new ServiceReport(yyyymm:200903, hours:75, publisher:members[1]),
                new ServiceReport(yyyymm:200904, hours:74, publisher:members[1]),
                new ServiceReport(yyyymm:200905, hours:73, publisher:members[1]),
                new ServiceReport(yyyymm:200906, hours:72, publisher:members[1]),
                new ServiceReport(yyyymm:200907, hours:71, publisher:members[1]),

                new ServiceReport(yyyymm:200901, hours:50, publisher:members[2], comments:'Aux pio'),
                new ServiceReport(yyyymm:200902, hours:16, publisher:members[2]),
                new ServiceReport(yyyymm:200903, hours:50, publisher:members[2], comments:'Aux pio'),
                new ServiceReport(yyyymm:200904, hours:14, publisher:members[2]),
                new ServiceReport(yyyymm:200905, hours:13, publisher:members[2]),
                new ServiceReport(yyyymm:200906, hours:12, publisher:members[2]),
                new ServiceReport(yyyymm:200907, hours:11, publisher:members[2]),

                new ServiceReport(yyyymm:200901, hours:15, publisher:members[3]),
                new ServiceReport(yyyymm:200902, hours:16, publisher:members[3]),
                new ServiceReport(yyyymm:200903, hours:15, publisher:members[3]),
                new ServiceReport(yyyymm:200904, hours:14, publisher:members[3]),
                new ServiceReport(yyyymm:200905, hours:13, publisher:members[3]),
                new ServiceReport(yyyymm:200906, hours:12, publisher:members[3]),
                new ServiceReport(yyyymm:200907, hours:11, publisher:members[3]),

                new ServiceReport(yyyymm:200901, hours:15, publisher:members[4]),
                new ServiceReport(yyyymm:200902, hours:16, publisher:members[4]),
                new ServiceReport(yyyymm:200903, hours:15, publisher:members[4]),
                new ServiceReport(yyyymm:200904, hours:14, publisher:members[4]),
                new ServiceReport(yyyymm:200905, hours:13, publisher:members[4]),
                new ServiceReport(yyyymm:200906, hours:12, publisher:members[4]),
                new ServiceReport(yyyymm:200907, hours:11, publisher:members[4]),
                
		new ServiceReport(yyyymm:200906, hours:1, publisher:members[5]),

		new ServiceReport(yyyymm:200901, hours:1, publisher:members[7]),
		new ServiceReport(yyyymm:200902, hours:1, publisher:members[7]),
		new ServiceReport(yyyymm:200903, hours:1, publisher:members[7])
            ]
            serviceReports.each save

	    def activePublisherCounts = [
	        new ActivePublisherCount(yyyymm:200901, publishers:6, baptizedPublishers:3),
	        new ActivePublisherCount(yyyymm:200902, publishers:6, baptizedPublishers:3),
	        new ActivePublisherCount(yyyymm:200903, publishers:6, baptizedPublishers:3),
	        new ActivePublisherCount(yyyymm:200904, publishers:4, baptizedPublishers:1),
	        new ActivePublisherCount(yyyymm:200905, publishers:4, baptizedPublishers:1),
	        new ActivePublisherCount(yyyymm:200906, publishers:5, baptizedPublishers:2),
	        new ActivePublisherCount(yyyymm:200907, publishers:4, baptizedPublishers:1)
	    ]
	    activePublisherCounts.each save

	    def serviceReportTotals = [
	        new ServiceReportTotals(yyyymm:200901, publishers:4, hours:47, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(yyyymm:200901, publishers:1, hours:50, category:Categories.AUXILIARY_PIONEERS),
	        new ServiceReportTotals(yyyymm:200901, publishers:1, hours:75, category:Categories.REGULAR_PIONEERS),
		
		new ServiceReportTotals(yyyymm:200902, publishers:5, hours:54, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(yyyymm:200902, publishers:1, hours:76, category:Categories.REGULAR_PIONEERS),
		
                new ServiceReportTotals(yyyymm:200903, publishers:4, hours:36, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(yyyymm:200903, publishers:1, hours:50, category:Categories.AUXILIARY_PIONEERS),
	        new ServiceReportTotals(yyyymm:200903, publishers:1, hours:75, category:Categories.REGULAR_PIONEERS),
		
                new ServiceReportTotals(yyyymm:200904, publishers:3, hours:42, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(yyyymm:200904, publishers:1, hours:74, category:Categories.REGULAR_PIONEERS),

                new ServiceReportTotals(yyyymm:200905, publishers:3, hours:39, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(yyyymm:200905, publishers:1, hours:73, category:Categories.REGULAR_PIONEERS),

                new ServiceReportTotals(yyyymm:200906, publishers:4, hours:37, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(yyyymm:200906, publishers:1, hours:72, category:Categories.REGULAR_PIONEERS),
	
                new ServiceReportTotals(yyyymm:200907, publishers:3, hours:33, category:Categories.PUBLISHERS),
	        new ServiceReportTotals(yyyymm:200907, publishers:1, hours:71, category:Categories.REGULAR_PIONEERS)
	    ]
	    serviceReportTotals.each save

	    log.debug 'bootstrapping test data complete'
        }
    }
    
    def destroy = {
    }
}
