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

class ExcelDownloadController {

    def exportService
    def publishersService

    def index = {
	redirect action:list
    }

    def list = {
/*
        def data = [
	    [publisher:'David Savage', books:0, brochures:0, hours:20, magazines:4, returnVisits:10, studies:2, comments:'testing'],
	    [publisher:'Gaile Savage', books:0, brochures:0, hours:10, magazines:3, returnVisits:5, studies:0, comments:'testing']
	]
*/
        def data = publishersService.loadActive()

	def fields = [
	    'groupUnit',
	    'fullname',
	    'books',
	    'brochures',
	    'hours',
	    'magazines',
	    'return_visits',
	    'studies',
	    'comments'
	] 
	exportService.export 'excel', response, 'cbs_report_proforma', 'xls', data, fields, [:], [:], [:]
    }
}

