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
package info.savaged.cong.utils

class DateUtils {

   static Calendar convert(Integer year, Integer month) {
       def cal = Calendar.instance
       cal.set year, month, 1
       cal
   }
   
   static Integer convert(Integer year, Integer month) {
       assert year > 0
       if (month < 1) {
	   month = 12
	   year = year - 1
       }
       (year * 100) + month
   }

   static Integer convert(Calendar cal) {
       def year = cal.get(Calendar.YEAR)
       def month = cal.get(Calendar.MONTH)
       convert year, month
   }

   static List previousMonthsRange(Integer size, Calendar from) {
       def range = []
       (0..size-1).each {
	   def c = { offset ->
	       def yyyymm
	       from.add Calendar.MONTH, - offset
	       yyyymm = convert(from)
	       from.add Calendar.MONTH, offset
	       yyyymm
	   }
	   range << c(it)
       }
       println range.dump()
       range
   }

   static Boolean inPreviousMonthsRange(Calendar cal, Integer size, Calendar from) {
       def range = previousMonthsRange(size, from)
       range.contains(convert(cal))
   }
}
