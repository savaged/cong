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

enum Groups {
    AMBLER_ROAD,
    COURT_GARDENS,
    HERSLET_ROAD,
    MOLTON_HOUSE,
    NEW_WHARF_ROAD,
    PENFIELDS_HOUSE,
    VULCAN_WAY
}

class Member {

    String lastname
    String firstname
    Date birth
    Boolean isMale
    Groups groupUnit
    Member bibleStudyConductor
    Date immersion
    Boolean isPublisher
    Date pioneerSchoolDate
    String historisation

    static hasMany = [states:MemberState, serviceReports:ServiceReport]
    
    static transients = ['fullname']

    Member() {
        isMale = isPublisher = false
    }

    static constraints = {
        lastname(blank:false)
        firstname(blank:false)
        groupUnit(nullable:true)
        bibleStudyConductor(nullable:true)
        pioneerSchoolDate(nullable:true)
        historisation(nullable:true)
    }

    void setBibleStudyConductor(Member bibleStudyConductor) {
        this.bibleStudyConductor = bibleStudyConductor
    }

    String getFullname() {
        return """${lastname}, ${firstname}"""
    }

    String toString() {
        return fullname
    }
}
