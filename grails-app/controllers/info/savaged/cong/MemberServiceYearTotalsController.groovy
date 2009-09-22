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

class MemberServiceYearTotalsController {

    def index = {
	if (request.method == 'POST') {
            def member = Member.findByLastnameLikeAndFirstnameLike(params?.lastname, params?.firstname)
	    
	    def serviceYearStart = DateUtils.convert(Integer.parseInt(params?.serviceYear_year), 9)
	    
	    def serviceReports = member?.serviceReports.findAll( {
		it.yyyymm >= serviceYearStart
	    } )
	    
	    def serviceYearTotals = [
		publisher:member,
		books:0,
		brochures:0,
		hours:0,
		magazines:0,
		returnVisits:0,
		studies:0
	    ]
	    serviceReports.each {
		serviceYearTotals.books += (it.books ? it.books : 0)
		serviceYearTotals.brochures += (it.brochures ? it.brochures : 0)
		serviceYearTotals.hours += it.hours 
		serviceYearTotals.magazines += (it.magazines ? it.magazines : 0)
		serviceYearTotals.returnVisits += (it.returnVisits ? it.returnVisits : 0)
		serviceYearTotals.studies += (it.studies ? it.studies : 0)
	    }
	    render view:'show', model:[serviceYearTotals:serviceYearTotals]
        }
    }
}
