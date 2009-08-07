package info.savaged.cong

import grails.test.*

class ActivePublisherCountTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        mockDomain(ActivePublisherCount)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testConstraints() {
        def activePublisherCount = new ActivePublisherCount()
        assertFalse 'validation should have failed', activePublisherCount.validate()
        assertEquals 'publishers is null', 'nullable', activePublisherCount.errors.publishers
        assertEquals 'month is null', 'nullable', activePublisherCount.errors.month
        assertEquals 'year is null', 'nullable', activePublisherCount.errors.year

        activePublisherCount.publishers = 0
        assertFalse 'validation should have failed', activePublisherCount.validate()
        assertEquals 'publishers is below minimum', 'min', activePublisherCount.errors.publishers
    }
}
