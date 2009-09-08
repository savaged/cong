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

import grails.test.*
import java.text.SimpleDateFormat

class ExcelUploadServiceTests extends GrailsUnitTestCase {

    def excelUploadService
    def file

    protected void setUp() {
        super.setUp()

        mockLogging(ExcelUploadService, true)

        def df = new SimpleDateFormat('yyyy-MM-dd')

        def members = [
            new Member(lastname:'Savage', firstname:'David', birth:df.parse('1959-12-02'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1986-07-12')),
            new Member(lastname:'Savage', firstname:'Gaile', birth:df.parse('1963-01-09'), isMale:false, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1990-09-23')),
            new Member(lastname:'Savage', firstname:'Jude', birth:df.parse('1980-01-16'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30')),
            new Member(lastname:'Savage', firstname:'Saskia', birth:df.parse('1975-04-23'), isMale:false, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30')),
            new Member(lastname:'Savage', firstname:'Kieran', birth:df.parse('1970-05-11'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30'))
        ]
        mockDomain(Member, members)

        def cal = Calendar.getInstance()
        cal.set(2008,8,1)
        def regPioneers = [
            new MemberState(name:States.REGULAR_PIONEER, starting:cal.getTime(), member:members[1])
        ]
        mockDomain(MemberState, regPioneers)

        mockDomain(ServiceReport)
        mockDomain(ServiceReportTotals)
        mockDomain(ActivePublisherCount)

        // TODO sort out where this test resource should live, maybe with the grails maven plugin support
        file = new File('/Users/davidsavage/savaged.info/cong/reports/cong/docs/test_cbs_report_proforma.xls')
        excelUploadService = new ExcelUploadService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testBindData() {
        excelUploadService.bindData file, 5, 2009
	
        assertEquals(4, excelUploadService.serviceReports.size())
        assertNotNull excelUploadService.serviceReportTotals
        
        assertEquals(5, excelUploadService.serviceReports[0].month)
        assertEquals(2009, excelUploadService.serviceReports[0].year)
    }
}
