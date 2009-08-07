package info.savaged.cong

class ExcelUploadController {

    def excelUploadService

    def month
    def year

    def index = {}

    def upload = {
        month = year = null
        def sourceFile = request.getFile('myFile');
        if (!sourceFile.empty) {
            def localFile = new File('cbs_report_proforma.xls')
            sourceFile.transferTo(localFile)

            Calendar cal = Calendar.getInstance()
            if (params != null){
                if (params.starting_year != null) {
                    year = Integer.parseInt(params.starting_year)
                    log.debug """Using params.starting_year=${params.starting_year} for upload"""
                } else {
                    year = cal.get(Calendar.YEAR)
                    log.debug """Using calendar year=${year} for upload, because the params object is null"""
                }
                if (params.starting_month != null) {
                    month = Integer.parseInt(params.starting_month)
                    log.debug """Using params.starting_month=${params.starting_month} for upload"""
                } else {
                    month = cal.get(Calendar.MONTH)
                    log.debug """Using calendar month=${month} for upload, because the params object is null"""
                }
            }

            excelUploadService.bindData localFile, month, year

            localFile.delete()
            log.debug "File uploaded"
        } else {
            log.error "File is empty"
        }
        redirect(controller:'monthServiceReportTotals')
    }

}
