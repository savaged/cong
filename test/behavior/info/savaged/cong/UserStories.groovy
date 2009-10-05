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

// set-up and tear-down
before 'start selenium', {
    given 'selenium is up and running', {
        selenium = new DefaultSelenium(
            'localhost',
            4444,
            '*chrome',
            'http://localhost:8080/cong')
        selenium.start()
    }
}
after 'stop selenium', {
    then 'selenium should be shutdown', {
        selenium.stop()
    }
}


description 'user-stories and acceptance criteria scenarios'

narrative 'member search user story', {
    as_a 'congregation secretary'
    i_want 'to search for a congregation member by first and last name'
    so_that 'i can view their record'
}

scenario 'member search acceptance criteria', {
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
	selenium.isTextPresent 'David'
    }
}

narrative 'member CRUD story', {
    as_a 'congregation secretary'
    i_want 'to create, read, update and delete a congregation member record'
    so_that 'i can manage congregation member data'
}
// TODO add scenario and include unique member constraint


narrative 'monthly service report upload story', {
    as_a 'congregation secretary'
    i_want 'to upload the monthly publisher service reports'
    so_that 'i can use that data for reporting'
}
// TODO add scenario and include unique service report constraint and include auxiliary pioneer flagging


narrative 'service report download story', {
    as_a 'congregation secretary'
    i_want 'to download a spreadsheet with the publishers and space for their reports'
    so_that 'i can distribute it to group overseers for them to fill and return for upload'
}
// TODO add scenario


narrative 'member CRUD story', {
    as_a 'congregation secretary'
    i_want 'to create, read, update and delete a congregation member record'
    so_that 'i can manage congregation member data'
}
// TODO add scenario and include unique member constraint


narrative 'monthly service report submission story', {
    as_a 'congregation secretary'
    i_want 'i want a report with the fields on jw.org'
    so_that 'i can submit the monthly service report there'
}
// TODO add scenario


narrative 'meeting attendance CRUD story', {
    as_a 'congregation secretary'
    i_want 'to store monthly meeting attendances'
    so_that 'i can produce reports covering many months'
}
// TODO add scenario and include unique constraint


narrative 'list inactive publishers story', {
    as_a 'congregation secretary'
    i_want 'want inactive publishers to be viewable as a set'
    so_that 'i can manage inactive states and report on it'
}
// TODO add scenario