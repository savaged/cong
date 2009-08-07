package info.savaged.cong

enum Categories {PUBLISHERS, AUXILIARY_PIONEERS, REGULAR_PIONEERS}

class ServiceReportTotals {

    Categories category
    Integer month
    Integer year
    Integer publishers = 0
    Integer books = 0
    Integer brochures = 0
    Integer hours = 0
    Integer magazines = 0
    Integer returnVisits = 0
    Integer studies = 0

    static constraints = {
        books(nullable:true)
        brochures(nullable:true)
        magazines(nullable:true)
        returnVisits(nullable:true)
        studies(nullable:true)
    }

    String toString() {
        return """${category} for ${month}/${year}"""
    }
}
