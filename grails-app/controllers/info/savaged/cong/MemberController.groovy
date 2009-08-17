package info.savaged.cong

class MemberController {
    
    def scaffold = true

    def search = {
        if (request.method == 'POST') {
            log.debug "Searching using lastname:${params?.lastname} & firstname:${params?.firstname}"
            def member = Member.findByLastnameLikeAndFirstnameLike(params?.lastname, params?.firstname)
            redirect(uri:"/member/show/${member?.id}")
        }
    }

    def inactive = {
        if (request.method == 'POST') {
            def cal = Calendar.instance
            cal.set(
                Integer.parseInt(params.starting_year),
                Integer.parseInt(params.starting_month), 1)

            def range = []
            (0..5).each {
                def c = { offset ->
                    def yyyymm
                    cal.with {
                        add Calendar.MONTH, - offset
                        yyyymm = (get(Calendar.YEAR) * 100) + get(Calendar.MONTH)
                        add Calendar.MONTH, offset
                    }
                    yyyymm
                }
                range << c(it)
            }
            def publishers = Member.findAllByIsPublisher(true)
	    def inactiveMembers = []
	    publishers.each {
	        def hours = 0
	        it?.serviceReports.each {
                    def reportDate = (it.year * 100) + it.month
		    if (range.contains(reportDate)) {
                        hours += it.hours
		    }
		}
		if (hours < 1) {
		    inactiveMembers << it
		}
	    }	    
	    render view:'list', model:[memberInstanceList:inactiveMembers, memberInstanceTotal:inactiveMembers.size()]
	}
    }

}
