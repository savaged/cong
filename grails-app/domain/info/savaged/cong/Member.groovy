package info.savaged.cong

enum Groups {
    AMBLER_ROAD,
    COURT_GARDENS,
    HERSLET_ROAD,
    MOLTON_HOUSE,
    NEW_WHARF_ROAD,
    PENFIELDS_HOUSE,
    VULCAN_WAY
}

class Member {

    String lastname
    String firstname
    Date birth
    Boolean isMale
    Groups groupUnit
    Member bibleStudyConductor
    Date immersion
    Boolean isPublisher
    Date pioneerSchoolDate
    String historisation

    static hasMany = [states:MemberState, serviceReports:ServiceReport]
    
    static transients = ['fullname']

    Member() {
        isMale = isPublisher = false
    }

    static constraints = {
        lastname(blank:false)
        firstname(blank:false)
        groupUnit(nullable:true)
        bibleStudyConductor(nullable:true)
        pioneerSchoolDate(nullable:true)
        historisation(nullable:true)
    }

    void setBibleStudyConductor(Member bibleStudyConductor) {
        this.bibleStudyConductor = bibleStudyConductor
    }

    String getFullname() {
        return """${lastname}, ${firstname}"""
    }

    String toString() {
        return fullname
    }
}
