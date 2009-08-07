package info.savaged.cong

enum States {
    INACTIVE,
    RESTRICTED,
    DISFELLOWSHIPPED,
    REGULAR_AUXILIARY_PIONEER,
    REGULAR_PIONEER,
    SERVANT,
    ELDER
}

class MemberState {

    States name
    Date starting
    Date ending
    Member member

    static belongsTo = Member

    static constraints = {
        ending(nullable:true)
    }

    String toString() {
        return """${member} is ${name.toString()} from ${starting} to ${ending} """
    }
}
