package info.savaged.cong

enum Meeting {CBS, SM, PT, WT}

class MeetingAttendance {

    Integer month
    Integer year
    Integer meetings
    Integer total
    Meeting meeting
    
    static constraints = {}

    static transients = ['average']

    Integer getAverage() {
        if (total != null && total > 0 && meetings != null && meetings > 0) {
            Math.round(total / meetings)
        }
    }

    String toString() {
        return """${meeting}:${month}/${year}"""
    }
}
