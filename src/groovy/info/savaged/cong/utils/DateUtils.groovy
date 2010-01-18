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

   static Map convert(Integer yyyymm) {
       Integer y = 1901
       Integer m = 1
       if (yyyymm != null && yyyymm > 190101) {
           y = Math.round(yyyymm / 100)
           m = yyyymm - (y * 100)
       }
       ['year':y, 'month':m]
   }

   static Integer convert(Integer year, Integer month) {
       Integer yyyymm = 190101
       if (year != null && month != null) {
           if (month < 1) {
               month = 12
               year = year - 1
           }
           yyyymm = (year * 100) + month
       }
       yyyymm
   }

   private static Calendar convertToCalendar(Integer yyyymm) {
       def map = convert(yyyymm)
       def cal = Calendar.instance
       cal.set map['year'], map['month'] - 1, 1
       cal
   }

   private static Integer convertToYyyymm(Calendar cal) {
       convert cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1
   }

   static List previousMonthsRange(Integer size, Integer yyyymm) {
       def range = []
       def cal = convertToCalendar(yyyymm)
       (0..size-1).each {
           def c = { offset ->
               cal.add Calendar.MONTH, - offset
               yyyymm = convertToYyyymm(cal)
               cal.add Calendar.MONTH, offset
               yyyymm
           }
           range << c(it)
       }
       range
   }

}
