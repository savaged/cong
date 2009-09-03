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

import grails.test.*

class DateUtilsTests extends GrailsUnitTestCase {

   protected void setUp() {
       super.setUp()
   }

   protected void tearDown() {
       super.tearDown()
   }
   
   void testPreviousMonthsRange() {
       def cal = Calendar.instance
       cal.set(2009, 02, 01)
       def range = DateUtils.previousMonthsRange(6, cal)
       assertEquals 6, range.size()
       assertEquals 200809, range[5]
   }

   void testInPreviousMonthsRange() {
       def cal = Calendar.instance
       cal.set(2009, 02, 01)
       assertTrue DateUtils.inPreviousMonthsRange(cal, 6, cal)
   }
}
