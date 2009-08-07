package info.savaged.cong.utils

import grails.test.*

class SplitFullnameTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExtractLastname() {
        assertEquals 'Savage', SplitFullname.extractLastname('Savage, David')
        assertEquals 'De Sousa', SplitFullname.extractLastname('De Sousa, Elsa')
    }

    void testExtractFirstname() {
        assertEquals 'David', SplitFullname.extractFirstname('Savage, David')
        assertEquals 'Elsa', SplitFullname.extractFirstname('De Sousa, Elsa')
    }
}

