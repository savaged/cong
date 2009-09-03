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

enum States {
    INACTIVE,
    RESTRICTED,
    DISFELLOWSHIPPED,
    REGULAR_AUXILIARY_PIONEER,
    REGULAR_PIONEER,
    SERVANT,
    ELDER
}

class MemberState {

    States name
    Date starting
    Date ending
    Member member

    static belongsTo = Member

    static constraints = {
        ending(nullable:true)
    }

    static transients = ['ended']
 
    // Hack to get around gui not allowing empty date
    Boolean getEnded() {
        Boolean ended = false
        if (ending) {
            Calendar cal = Calendar.instance
            cal.clear()
            if (ending > cal.time) {
                ended = true
            }
        }
        ended
    }
    

    String toString() {
        return "${member} is ${name.toString()} from ${starting} to ${ending}"
    }
}
