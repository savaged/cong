

package info.savaged.cong

class MemberController {
    
    def scaffold = true

    def search = {
        if (request.method == 'POST') {
            def member = Member.findByLastnameLikeAndFirstnameLike(params?.lastname, params?.firstname)
            redirect(uri:"/member/show/${member?.id}")
        }
    }

    def inactive = {
        if (request.method == 'POST') {
	    def reportYear = Integer.parseInt(params.starting_year)
            def reportMonth = Integer.parseInt(params.starting_month)
    
            def monthSixMonthsPrior
	    def yearSixMonthsPrior
	    def cal = Calendar.instance
	    cal.with {
	    	set reportYear, reportMonth-5, 1
	    	monthSixMonthsPrior = get(Calendar.MONTH)
		yearSixMonthsPrior = get(Calendar.YEAR)
	    }
	    def yearAndMonthSixMonthsPrior = yearSixMonthsPrior * 100  
	    yearAndMonthSixMonthsPrior += monthSixMonthsPrior
	    def reportYearAndMonth = reportYear * 100
	    reportYearAndMonth += reportMonth
	    def range = [yearAndMonthSixMonthsPrior..reportYearAndMonth]

	    def publishers = Member.findAllByIsPublisher(true)
	    def inactiveMembers = []
	    publishers.each {
	        def hours = 0
	        it?.serviceReports.each {
		    def currentReportYearAndMonth = it.year * 100 
		    currentReportYearAndMonth += it.month
		    if (range.contains(currentReportYearAndMonth)) {
			    hours += it.hours 
		    }
		}
		if (hours < 1) {
		    inactiveMembers.add it
		}
	    }
	    
	    render view:'list', model:[memberInstanceList:inactiveMembers, memberInstanceTotal:inactiveMembers.size()]
	}
    }

}
