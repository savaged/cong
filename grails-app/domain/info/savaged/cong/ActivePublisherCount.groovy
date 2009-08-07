package info.savaged.cong

class ActivePublisherCount {

    Integer month
    Integer year
    Integer publishers

    static constraints = {
        publishers(min:1)
    }

    String toString() {
        return """Active publishers count for ${month}/${year}"""
    }
}
