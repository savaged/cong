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

/**
 * Simulation of the paper "Congregation Publisher Record Card (S-21)"
 *
 * @author savaged
 */
class ServiceReport {

    Integer yyyymm
    Boolean isAuxPioneer
    Integer books
    Integer brochures
    Integer hours
    Integer magazines
    Integer returnVisits
    Integer studies
    String comments
    Member publisher

    static belongsTo = Member

    ServiceReport() {
        isAuxPioneer = false
    }

    static constraints = {
        yyyymm(min:1901)
        hours(min:1)
        books(nullable:true)
        brochures(nullable:true)
        magazines(nullable:true)
        returnVisits(nullable:true)
        studies(nullable:true)
        comments(nullable:true)
	publisher(unique:['yyyymm'])
    }

    String toString() {
        "yyyymm:${yyyymm}, books:${books}, brochures:${brochures}, hours:${hours}, magazines:${magazines}, returnVisits:${returnVisits}, studies:${studies}"
    }
}
