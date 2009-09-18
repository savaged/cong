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

enum Meeting {CBS, SM, PT, WT}

/**
 * Simulation of the paper "Congregation Meeting Attendance Record (S-88)"
 *
 * @author savaged
 */
class MeetingAttendance {

    Integer yyyymm
    Integer meetings
    Integer total
    Meeting meeting
    
    static constraints = {
        yyyymm(min:190101)
        meetings(min:1)
        total(min:1)
	yyyymm(unique:['meeting'])
    }

    static transients = ['average']

    Integer getAverage() {
        if (total != null && total > 0 && meetings != null && meetings > 0) {
            Math.round(total / meetings)
        }
    }

    String toString() {
        return """${meeting}:${yyyymm}"""
    }
}
