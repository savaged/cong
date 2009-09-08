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

import info.savaged.cong.utils.DateUtils

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
            def from = DateUtils.convert(
                Integer.parseInt(params.starting_year),
                Integer.parseInt(params.starting_month))
            
	    def publishers = Member.findAllByIsPublisher(true)

	    def publishersNotIncludingLongTermInactive = []
	    def fromLessOneYear = from - 1000
	    
	    publishers.each { publisher ->
		publisher?.states.each {
		    def isLongTermInactive = false
		    if (it.name == States.INACTIVE) {
			if (!it.ended) {
			    def cal = Calendar.instance
			    cal.setTime it.starting
			    def starting = DateUtils.convert(
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH))
			    if (starting < fromLessOneYear) {
				isLongTermInactive = true
			    }
			}
		    }
		    if (!isLongTermInactive) {
			publishersNotIncludingLongTermInactive << publisher
		    }
		}
	    }

	    def inactiveMembers = []
	    def previousMonthsRange = DateUtils.previousMonthsRange(6, from)
	    publishersNotIncludingLongTermInactive.each {
	        def hours = 0
	        it?.serviceReports.each {
		    if (previousMonthsRange.contains(it.yyyymm)) {
                        hours += it.hours
		    }
		}
		if (hours < 1) {
		    inactiveMembers << it
		}
	    }	    
	    render view:'list', model:[
	        memberInstanceList:inactiveMembers, 
		memberInstanceTotal:inactiveMembers.size()]
	}
    }

}
