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
