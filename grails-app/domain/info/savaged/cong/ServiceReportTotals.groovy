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

enum Categories {PUBLISHERS, AUXILIARY_PIONEERS, REGULAR_PIONEERS}

class ServiceReportTotals {

    Categories category
    Integer yyyymm
    Integer publishers = 0
    Integer books = 0
    Integer brochures = 0
    Integer hours = 0
    Integer magazines = 0
    Integer returnVisits = 0
    Integer studies = 0

    static constraints = {
	yyyymm(min:190101)
	hours(min:1)
	publishers(min:1)
        books(nullable:true)
        brochures(nullable:true)
        magazines(nullable:true)
        returnVisits(nullable:true)
        studies(nullable:true)
    }

    String toString() {
        return "${category} for ${yyyymm}"
    }
}
