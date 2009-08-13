package info.savaged.cong

import grails.test.*
import java.io.FileInputStream
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest

class ExcelUploadControllerTests extends ControllerUnitTestCase {

    def excelUploadService

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
