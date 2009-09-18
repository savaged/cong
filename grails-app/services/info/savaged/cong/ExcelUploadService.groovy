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

import static info.savaged.cong.utils.SplitFullname.extractLastname
import static info.savaged.cong.utils.SplitFullname.extractFirstname
import info.savaged.cong.utils.DateUtils

import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.text.ParseException
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Iterator
import java.util.List
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.springframework.util.Assert

class ExcelUploadService {

    boolean transactional = true

    def serviceReports = []
    def serviceReportTotals
    def activePublisherCount
    def reportYyyymm

    def bindData(File file, Integer month, Integer year) {
        serviceReports = []
        serviceReportTotals = null
        activePublisherCount = null

	reportYyyymm = DateUtils.convert(year, month)

        log.debug "opening xl report file at: [${file.getName()}], using the year and month: [${reportYyyymm}]"
        InputStream inputStream = new FileInputStream(file)
        POIFSFileSystem poiFsFileSystem = new POIFSFileSystem(inputStream)
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poiFsFileSystem)
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0)
        inputStream.close()

        retrieveReports(hssfSheet)

        validateReports()
        buildMonthTotals()
        persistReports()

    }

    private void validateReports() {

        log.debug 'Validating reports...'

        // check that no publisher appears twice
        def publishers = []
        for (serviceReport in serviceReports) {
            def fullname = serviceReport.publisher.lastname + ', ' +
            serviceReport.publisher.firstname
            publishers << fullname
        }
        def countBefore = publishers.size()
        publishers.unique()
        def countAfter = publishers.size()
        def diff = countBefore - countAfter
        if (diff > 0) {
            throw new Exception("There were ${diff} publishers appearing more than once.")
        }
    }

    private void persistReports() {

        log.debug 'Persisting ' + serviceReports.size() + ' service reports...'

        for (serviceReport in serviceReports) {
            if (serviceReport.save(flush:true)) {
                log.debug('persisted service report for ' +
                    serviceReport?.publisher?.lastname + ', ' +
                    serviceReport?.publisher?.firstname + ' with year and  month ' +
                    serviceReport?.yyyymm)
            } else {
                serviceReport.errors.each {
                    log.debug "failed to save ${serviceReport} due to: ${it}"
                }
            }
        }
    }

    private void persistMonthTotals() {

        log.debug 'Persisting ' + serviceReportTotals.size() + ' service report totals...'

        for (totals in serviceReportTotals) {
            if (totals.save(flush:true)) {
                log.debug(
"Persisting service report totals: ${totals} with ${totals?.hours} hours"
                )
            } else {
                totals.errors.each {
                    log.debug "failed to save ${totals} due to: ${it}"
                }
            }
        }

        log.debug 'Persisting active publisher count...'
        
        if (activePublisherCount.save(flush:true)) {
            log.debug(
		"Persisting active publisher total of ${activePublisherCount.publishers} for ${activePublisherCount.yyyymm}"
            )
        } else {
            activePublisherCount.errors.each {
                log.debug "failed to save ${activePublisherCount} due to: ${it}"
            }
        }
    }

    private void retrieveReports(HSSFSheet hssfSheet) {

        log.debug 'Retrieving reports...'

        log.debug "using start date with the year and month ${reportYyyymm}" 
        Iterator rows = hssfSheet.rowIterator()
        int rowCount = 0
        while (rows.hasNext()) {
            
            HSSFRow row = (HSSFRow) rows.next()
            if (!row.getCell(1) || row.getCell(1).getRichStringCellValue().getString()?.length() < 1) {
                log.debug 'row.hasNext() is true but the first cell is empty, so breaking while loop'
                break
            }
            def report = new ServiceReport()
	    report.yyyymm = reportYyyymm

            rowCount++
            log.debug("processing row: " + rowCount)
            if (rowCount == 1) {
                continue
            }
            Assert.notNull(row.getCell(1), "Cell 1 should always have an entry.")
            String fullname = row.getCell(1).getRichStringCellValue().getString()
            Assert.notNull(fullname, "The publisher's full name should not be null.")
            Assert.hasLength(fullname, "The publisher's full name should always be entered.")
            
            log.debug "Looking up \'${fullname}\' to add as service report publisher"
            report.publisher = Member.findByLastnameAndFirstname(
                extractLastname(fullname), extractFirstname(fullname))
            Assert.notNull(report.publisher, "The publisher should always be in the database.")
            log.debug "Found ${report.publisher}"

            if (row.getCell(2) != null) {
                report.books = (int) row.getCell(2).getNumericCellValue()
            }
            if (row.getCell(3) != null) {
                report.brochures = (int) row.getCell(3).getNumericCellValue()
            }
            Assert.notNull(row.getCell(4), "Hours should always be entered.")
            Assert.isTrue(row.getCell(4).getNumericCellValue() > 0, "Hours should always be > 0.")
            report.hours = (float) row.getCell(4).getNumericCellValue()
            if (row.getCell(5) != null) {
                report.magazines = (int) row.getCell(5).getNumericCellValue()
            }
            if (row.getCell(6) != null) {
                report.returnVisits = (int) row.getCell(6).getNumericCellValue()
            }
            if (row.getCell(7) != null) {
                report.studies = (int) row.getCell(7).getNumericCellValue()
            }
            if (row.getCell(8) != null) {
                report.comments = row.getCell(8).getRichStringCellValue().getString()
            }
            report.isAuxPioneer = isAuxPioneer(report?.comments)

            serviceReports.add(report)
            log.debug("processed row: " + rowCount)
        }
    }

    private Boolean isAuxPioneer(String comments) {
        Boolean match = false
        if (comments) {
            Boolean match1 = comments =~ ~"(A|a)ux"
            Boolean match2 = comments =~ ~"(P|p)io"
            match = match1 & match2
        }
        if (match && comments.startsWith('Not ')) {
            match = false
        }
        return match
    }

    private Map retrieveActivePublishers() {
	
	def activePublishers = [:]
	def activeBaptizedPublisherCount = 0
        
	def publishers = Member.findAllByIsPublisher(true)

        for (publisher in publishers) {
            def inactive = MemberState.findByNameAndMember(States.INACTIVE.toString(), publisher)
            if (inactive) {
                if (!inactive.ended) {
                    log.debug(
			"${publisher} not counted as active publisher due to being in an inactive state"
                    )
                    continue
                }
            }
            def disfellowshipped = MemberState.findByNameAndMember(States.DISFELLOWSHIPPED.toString(), publisher)
            if (disfellowshipped) {
                if (!disfellowshipped.ended) {
                    log.debug(
			"${publisher} not counted as active publisher due to being in a disfellowshipped state"
                    )
                    continue
                }
            }
            activePublishers.put(publisher.fullname, publisher)
            log.debug "${publisher} counted as active publisher"

	    if (publisher.baptized) { 
		activeBaptizedPublisherCount++
		log.debug "${publisher} counted as baptized active publisher"
	    }
        }
        activePublisherCount = new ActivePublisherCount(
	    yyyymm:reportYyyymm, 
	    publishers:activePublishers.size(),
	    baptizedPublishers:activeBaptizedPublisherCount)
        log.debug "${activePublisherCount} of ${activePublisherCount.publishers}"

        activePublishers
    }
    
    private void buildMonthTotals() {

        log.debug 'Calculating month totals...'
        
	def activePublishers = retrieveActivePublishers()

        serviceReportTotals = [
            new ServiceReportTotals(category:Categories.PUBLISHERS, yyyymm:reportYyyymm),
            new ServiceReportTotals(category:Categories.AUXILIARY_PIONEERS, yyyymm:reportYyyymm),
            new ServiceReportTotals(category:Categories.REGULAR_PIONEERS, yyyymm:reportYyyymm)
        ]

        for (serviceReport in serviceReports) {

            if (activePublishers.get(serviceReport.publisher.fullname)) {

                def regPioneer = MemberState.findByNameAndMember(States.REGULAR_PIONEER.toString(), serviceReport.publisher)
                if (regPioneer) {
                    if (!regPioneer.ending) {
                        updateRow(serviceReportTotals[2], serviceReport)
                        log.debug(
				"Added service report for ${serviceReport?.publisher} as regular pioneer"
                        )
                    }
                } else {
                    if (serviceReport.isAuxPioneer) {
                        updateRow(serviceReportTotals[1], serviceReport)
                        log.debug(
				"Added service report for  ${serviceReport?.publisher} as auxiliary pioneer"
                        )
                    } else {
                        updateRow(serviceReportTotals[0], serviceReport)
                        log.debug(
    				"Added service report for ${serviceReport?.publisher} as publisher"
                        )
                    }
                }
            }
        }

        persistMonthTotals()
    }

    private void updateRow(row, serviceReport) {
        row.publishers++
        row.books += (serviceReport.books ? serviceReport.books : 0)
        row.brochures += (serviceReport.brochures ? serviceReport.brochures : 0)
        row.hours += serviceReport.hours
        row.magazines += (serviceReport.magazines ? serviceReport.magazines : 0)
        row.returnVisits += (serviceReport.returnVisits ? serviceReport.returnVisits : 0)
        row.studies += (serviceReport.studies ? serviceReport.studies : 0)
    }

}

