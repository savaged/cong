package info.savaged.cong

import static info.savaged.cong.utils.SplitFullname.extractLastname
import static info.savaged.cong.utils.SplitFullname.extractFirstname

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
    def reportMonth
    def reportYear

    def bindData(File file, Integer month, Integer year) {
        serviceReports = []
        serviceReportTotals = null
        activePublisherCount = null
        reportMonth = month
        reportYear = year

        log.debug("opening xl report file at: " + file.getName())
        InputStream inputStream = new FileInputStream(file)
        POIFSFileSystem poiFsFileSystem = new POIFSFileSystem(inputStream)
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poiFsFileSystem)
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0)
        inputStream.close()

        retrieveReports(hssfSheet)

        validateReports()
        calcTotals()
        persistReports()
        persistTotals()
    }

    private void validateReports() {

        log.debug 'Validating reports...'

        // check that this month hasn't been submitted previously
        def previousReportsForMonthAndYear = ServiceReport.findAllByMonthAndYear(
            serviceReports[0].month, serviceReports[0].year
        )
        if (previousReportsForMonthAndYear && previousReportsForMonthAndYear.size() > 0) {
            throw new Exception(
"Service report for ${serviceReports[0]?.month}/${serviceReports[0].year} already exists."
            )
        }

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
                    serviceReport?.publisher?.firstname + ' with month ' +
                    serviceReport?.month + ' and year ' + serviceReport?.year)
            } else {
                serviceReport.errors.each {
                    log.debug "failed to save ${serviceReport} due to: ${it}"
                }
            }
        }
    }

    private void persistTotals() {

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
"Persisting active publisher total of ${activePublisherCount.publishers} for ${activePublisherCount.month}/${activePublisherCount.year}"
            )
        } else {
            activePublisherCount.errors.each {
                log.debug "failed to save ${activePublisherCount} due to: ${it}"
            }
        }
    }

    private void retrieveReports(HSSFSheet hssfSheet) {

        log.debug 'Retrieving reports...'

        log.debug 'using start month: ' + reportMonth + ' and year: ' + reportYear
        Iterator rows = hssfSheet.rowIterator()
        int rowCount = 0
        while (rows.hasNext()) {
            
            HSSFRow row = (HSSFRow) rows.next()
            if (!row.getCell(1) || row.getCell(1).getRichStringCellValue().getString()?.length() < 1) {
                log.debug 'row.hasNext() is true but the first cell is empty, so breaking while loop'
                break
            }
            def report = new ServiceReport()
            report.month = reportMonth
            report.year = reportYear

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

	    setInactiveState(report, reportMonth, reportYear)

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

    private Boolean setInactiveState(report, reportMonth, reportYear) {
	    isPreviouslyInactive(report, reportMonth, reportYear)
	    // TODO set INACTIVE open or closed if reported in past six months
    }

    private Boolean isPreviouslyInactive(report, reportMonth, reportYear) {
	    def monthSixMonthsPrior
	    def yearSixMonthsPrior
	    def cal = Calendar.instance
	    cal.with {
	    	set reportYear, reportMonth-5, 1
	    	monthSixMonthsPrior = get(Calendar.MONTH)
		yearSixMonthsPrior = get(Calendar.YEAR)
	    }
	    def yearAndMonthSixMonthsPrior = yearSixMonthsPrior * 100  
	    yearAndMonthSixMonthsPrior += monthSixMonthsPrior
	    def reportYearAndMonth = reportYear * 100
	    reportYearAndMonth += reportMonth
	    def range = [yearAndMonthSixMonthsPrior..reportYearAndMonth]
	    def hours = 0
	    report?.publisher?.serviceReports.each {
		    def currentReportYearAndMonth = it.year * 100 
		    currentReportYearAndMonth += it.month
		    if (range.contains(currentReportYearAndMonth)) {
			    hours += it.hours 
		    }
	    }
	    hours < 1
    }

    private void calcTotals() {

        log.debug 'Calculating totals...'

        def publishers = Member.findAllByIsPublisher(true)

        def activePublishers = [:]

        for (publisher in publishers) {
            def inactive = MemberState.findByNameAndMember(States.INACTIVE.toString(), publisher)
            if (inactive) {
                if (!inactive.ending) {
                    log.debug(
			"${publisher} not counted as active publisher due to being in an inactive state"
                    )
                    continue
                }
            }
            def disfellowshipped = MemberState.findByNameAndMember(States.DISFELLOWSHIPPED.toString(), publisher)
            if (disfellowshipped) {
                if (!disfellowshipped.ending) {
                    log.debug(
			"${publisher} not counted as active publisher due to being in a disfellowshipped state"
                    )
                    continue
                }
            }
            if (publisher?.historisation?.length() > 1) {
                log.debug(
"""${publisher} not counted as active publisher due to being historised with the following note
\'$publisher.historisation}\'"""
                )
                continue
            }
            activePublishers.put(publisher.fullname, publisher)
            log.debug "${publisher} counted as active publisher"
        }
        activePublisherCount = new ActivePublisherCount(month:reportMonth, year:reportYear, publishers:activePublishers.size())
        log.debug "${activePublisherCount} of ${activePublisherCount.publishers}"

        serviceReportTotals = [
            new ServiceReportTotals(category:Categories.PUBLISHERS, month:reportMonth, year:reportYear),
            new ServiceReportTotals(category:Categories.AUXILIARY_PIONEERS, month:reportMonth, year:reportYear),
            new ServiceReportTotals(category:Categories.REGULAR_PIONEERS, month:reportMonth, year:reportYear)
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

