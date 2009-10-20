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

   void testConvert() {
       def y = 2009
       def m = 12
       def yyyymm = 200912

       assertEquals yyyymm, DateUtils.convert(y, m)
       assertEquals y, DateUtils.convert(yyyymm)['year']
       assertEquals m, DateUtils.convert(yyyymm)['month']

        m = 9
        yyyymm = 200909
        assertEquals yyyymm, DateUtils.convert(y, m)
        assertEquals y, DateUtils.convert(yyyymm)['year']
        assertEquals m, DateUtils.convert(yyyymm)['month']
   }
   
   void testPreviousMonthsRange() {
       def from = 200902
       def range = DateUtils.previousMonthsRange(6, from)
       assertEquals 6, range.size()
       assertEquals 200809, range[5]
   }

    void testIsCurrentServiceReportMonth() {
        def cal = Calendar.instance
        def yyyymm = DateUtils.convert(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH))
        assertTrue DateUtils.isCurrentServiceReportMonth(yyyymm)
        cal.add Calendar.MONTH, -1
        yyyymm = DateUtils.convert(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH))
        assertFalse DateUtils.isCurrentServiceReportMonth(yyyymm)
    }
}
