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
    Date immersion
    Boolean isPublisher
    Boolean isElder
    Boolean isServant
    Boolean isRegularPioneer
    Boolean isInactive
    Date inactiveStarting

    static hasMany = [serviceReports:ServiceReport]
    
    static transients = ['fullname', 'baptized']

    static constraints = {
        lastname(blank:false)
        firstname(blank:false)
        birth(unique:['lastname', 'firstname'])
        isMale(nullable:false)
        groupUnit(nullable:true)
        immersion(nullable:true)
        isPublisher(nullable:false)
        isElder(nullable:false)
        isServant(nullable:false)
        isRegularPioneer(nullable:false)
        isInactive(nullable:false)
        inactiveStarting(nullable:true)
        
	isInactive(validator: { val, obj ->
	    def inactiveFlagSet = (val == true)
	    def inactiveStartingNotNull = (obj?.inactiveStarting != null)
	    def validCombination = (inactiveFlagSet == false || (inactiveFlagSet && inactiveStartingNotNull)) 
	    validCombination
	})
    }

    Member() {
	isMale = isPublisher = isElder = isServant = isInactive = isRegularPioneer = false
    }

    String getFullname() {
        return "${lastname}, ${firstname}"
    }

    Boolean getBaptized() {
        Boolean baptized = false
        if (immersion) {
            Calendar cal = Calendar.instance
            cal.set 1914,1,1
            if (immersion > cal.time) {
                baptized = true
            }
        }
        baptized
    }
 
    String toString() {
        return fullname
    }
}

