package info.savaged.cong

class ServiceReport {

    Integer month
    Integer year
    Boolean isAuxPioneer
    Integer books
    Integer brochures
    Integer hours
    Integer magazines
    Integer returnVisits
    Integer studies
    String comments
    Member publisher

    static belongsTo = Member

    ServiceReport() {
        isAuxPioneer = false
    }

    static constraints = {
        hours(min:1)
        books(nullable:true)
        brochures(nullable:true)
        magazines(nullable:true)
        returnVisits(nullable:true)
        studies(nullable:true)
        comments(nullable:true)
    }

    String toString() {
        return """${publisher} for ${month}/${year}"""
    }
}
