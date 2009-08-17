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
