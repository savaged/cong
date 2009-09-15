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

description 'Member searching user-story and acceptance criteria scenario'

narrative 'User Story', {
    as a 'congregation secretary'
    i want 'to search for a congregation member by first and last name'
    so that 'i can view their record'
}

scenario 'Acceptance Criteria', {
    given 'the member is in the database', {
	// TODO ensure the test data BootStrap is run
	false
    }
    and 'the member search page is selected', {
	// TODO select the member search GUI
	false
    }
    when 'searched for by firstname and lastname', {
	// TODO use the GUI search
	false
    }
    then 'the member matching should be returned', {
	// TODO test for member on GUI
	false
    }
}

