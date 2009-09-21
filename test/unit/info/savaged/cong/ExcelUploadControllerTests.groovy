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
import java.io.FileInputStream
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest

class ExcelUploadControllerTests extends ControllerUnitTestCase {

    protected void setUp() {
        super.setUp()
        mockLogging(ExcelUploadService, true)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUpload() {
        GrailsMockHttpServletRequest.metaClass.getFile = { filename ->
            // TODO sort out where this test resource should live, maybe with the grails maven plugin support
            def file = new File('/Users/davidsavage/savaged.info/cong/reports/cong/docs/cbs_report_proforma.xls')
            def stream = new FileInputStream(file)
            def multipartFile = new MockMultipartFile('local.xls', 'cbs_report_proforma.xls', 'text/xls', stream)
            return multipartFile
        }
        GrailsMock.metaClass.bindData = { file, month, year -> }
        controller.excelUploadService = mockFor(ExcelUploadService)
        controller.upload()
        Calendar cal = Calendar.getInstance()
        assertEquals cal.get(Calendar.MONTH), controller.month
        assertEquals cal.get(Calendar.YEAR), controller.year
    }
}
