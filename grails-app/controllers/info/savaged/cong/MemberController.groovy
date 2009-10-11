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

import java.text.SimpleDateFormat

class MemberController {

    def publishersService

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
        def year = Integer.parseInt(params.starting_year)
        def month = Integer.parseInt(params.starting_month)
 
        def inactiveMembers = publishersService.loadInactive(month, year)

        render view:'list', model:[
            memberInstanceList:inactiveMembers, 
            memberInstanceTotal:inactiveMembers.size()]
        }
    }

}

