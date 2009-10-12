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

import com.thoughtworks.selenium.DefaultSelenium

// TODO split into separate files

description 'Tests For Each User-Story'

before 'start selenium', {
    given 'selenium is up and running', {
        selenium = new DefaultSelenium(
            'localhost',
            4444,
            '*firefox',
            'http://localhost:8080/cong/'
        )
        selenium.start()
    }
}

narrative 'member search user story', {
    as_a 'congregation secretary'
    i_want 'to search for a congregation member by first and last name'
    so_that 'i can view their record'
}
scenario 'member search acceptance criteria (positive)', {
    given 'the member is in the database', {
        selenium.open '/cong/member/list/'
        selenium.isTextPresent 'David'
    }
    and 'the member search page is selected', {
        selenium.open '/cong/member/search/'
        selenium.isTextPresent 'Member Search'
    }
    when 'searched for by firstname and lastname', {
        selenium.type 'firstname', 'David'
        selenium.type 'lastname', 'Savage'
        selenium.click "//input[@value='Search']"
    }
    then 'the member matching should be returned', {
        selenium.isTextPresent 'Savage, David'
    }
}
scenario 'member search acceptance criteria (negative)', {
    given 'the member is not in the database', {
        selenium.open '/cong/member/list/'
        !selenium.isTextPresent('Neo')
    }
    and 'the member search page is selected', {
        selenium.open '/cong/member/search/'
        selenium.isTextPresent 'Member Search'
    }
    when 'searched for by firstname and lastname', {
        selenium.type 'firstname', 'Neo'
        selenium.type 'lastname', 'Savage'
        selenium.click "//input[@value='Search']"
    }
    then 'the failed member match message should be returned', {
        selenium.isTextPresent "No match found for lastname: Savage and firstname: Neo. You could try something like 'Sav%' to get all matches starting with 'Sav'."
    }
}

narrative 'member CRUD story', {
    as_a 'congregation secretary'
    i_want 'to create, read, update and delete a congregation member record'
    so_that 'i can manage congregation member data'
}
scenario 'member CRUD acceptance criteria (positive)', {
    given 'a member is not already in the database', {
        selenium.open '/cong/member/list/'
        !selenium.isTextPresent('Neo')
    }       
    when 'the member is created', {
        selenium.open '/cong/member/create'
        selenium.type 'firstname', 'Neo'
        selenium.type 'lastname', 'Savage'
        selenium.select 'groupUnit', 'label=PENFIELDS_HOUSE'
        selenium.select 'birth_day', 'label=30'
        selenium.select 'birth_month', 'label=September'
        selenium.select 'birth_year', 'label=2009'
        selenium.select 'birth_hour', 'label=00'
        selenium.select 'birth_minute', 'label=12'
        selenium.click 'isMale'
        selenium.click "//input[@value='Create']"
    }
    then 'the member should be returned on the Show Member page', {
        selenium.isTextPresent 'Show Member'
        selenium.isTextPresent 'created'
        selenium.isTextPresent 'Savage, Neo'
    }
}
scenario 'member CRUD acceptance criteria (negative)', {
    given 'a member is already in the database', {
        selenium.open '/cong/member/list/'
        selenium.isTextPresent('Neo')
    }       
    when 'the member is created', {
        selenium.open '/cong/member/create'
        selenium.type 'firstname', 'Neo'
        selenium.type 'lastname', 'Savage'
        selenium.select 'groupUnit', 'label=PENFIELDS_HOUSE'
        selenium.select 'birth_day', 'label=30'
        selenium.select 'birth_month', 'label=September'
        selenium.select 'birth_year', 'label=2009'
        selenium.select 'birth_hour', 'label=00'
        selenium.select 'birth_minute', 'label=12'
        selenium.click 'isMale'
        selenium.click "//input[@value='Create']"
    }       
    then 'an error message should be returned on the Create Member page', {
        selenium.isTextPresent 'Create Member'
        selenium.isTextPresent 'Property [birth] of class [class info.savaged.cong.Member] with value [01/10/09 12:00] must be unique'
        selenium.isTextPresent 'Savage, Neo'
    }
}


narrative 'service report proforma download story', {
    as_a 'congregation secretary'
    i_want 'to download a spreadsheet with the publishers and space for their reports'
    so_that 'i can distribute it to group overseers for them to fill and return for upload'
}
/*
scenario 'service report proforma download acceptane criteria', {
    when 'the download is requested', {
        selenium.open '/cong/excelDownload/'
    }
    then 'the browser file open/save process should work and the cong home page be presented', {
        selenium.isTextPresent 'Cong (AKA Groovy-Cong), is designed as lean tooling for a JW congregation secretary'
    }
}
*/

narrative 'monthly service report completed proforma upload story', {
    as_a 'congregation secretary'
    i_want 'to upload the monthly publisher service reports from the complete proforma'
    so_that 'i can use that data for reporting'
}
/* 
scenario 'service report proforma download acceptane criteria', {
    given 'a completed proforma', {
        file = new File('/Users/davidsavage/savaged.info/cong/reports/cong/docs/test_cbs_report_proforma.xls')
    }
    when 'the upload is requested', {
        selenium.type 'myFile', file.name
        selenium.select 'starting_month', 'label=September'
        selenium.click "//input[@value='Upload']"
    }
    then 'the file should upload and the service report totals selection page should be presented', {
        selenium.isTextPresent 'Service Report Totals'
    }
}
*/


narrative 'monthly service report submission story', {
    as_a 'congregation secretary'
    i_want 'i want a report with the fields on jw.org'
    so_that 'i can submit the monthly service report there'
}
scenario 'monthly service report submission acceptance criteria', {
    given 'the month service report totals page is selected', {
        selenium.open '/cong/monthServiceReportTotals'
    }
    when 'a report month is selected', {
        selenium.select 'starting_month', 'label=September'
        selenium.select 'starting_year', 'label=2009'
        selenium.click "//input[@value='Submit']"
    }
    then 'the month service report totals page should be presented', {
        selenium.isTextPresent 'Service report totals for 2009-9'
    }
    and 'the month totals should be correct', {
        def totalCount = selenium.getText("//table/tbody/tr[5]/td[2]")
        totalCount == '4'
        def totalBooks = selenium.getText("//table/tbody/tr[5]/td[3]")
        totalBooks == '7' 
        def totalBrochures = selenium.getText("//table/tbody/tr[5]/td[4]")
        totalBrochures == '0'
        def totalHours = selenium.getText("//table/tbody/tr[5]/td[5]")
        totalHours == '139'
        def totalMagazines = selenium.getText("//table/tbody/tr[5]/td[6]")
        totalMagazines == '42'
        def totalReturnVisits = selenium.getText("//table/tbody/tr[5]/td[7]")
        totalReturnVisits == '47'
        def totalStudies = selenium.getText("//table/tbody/tr[5]/td[8]")
        totalStudies == '7'
    }
}


narrative 'list inactive publishers story', {
    as_a 'congregation secretary'
    i_want 'want inactive publishers to be viewable as a set'
    so_that 'i can manage inactive states and report on it'
}
scenario 'list inactive publishers acceptance criteria', {
    given 'the inactive member analysis page is selected', {
        selenium.open '/cong/member/inactive'
    }
    when 'the month and year is selected', {
        selenium.select 'starting_month', 'label=September'
        selenium.select 'starting_year', 'label=2009'
        selenium.click "//input[@value='Submit']"
    }
    then 'the inactive should appear', {
        selenium.isTextPresent 'Allison'
        selenium.isTextPresent 'Dave'
    }
}


after 'stop selenium', {
    then 'tests are complete and selenium should be shutdown', {
        selenium.stop()
    }
}
