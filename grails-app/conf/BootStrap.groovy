import info.savaged.cong.*
import java.text.SimpleDateFormat
import grails.util.*

class BootStrap {

    def init = { servletContext ->
        if (Environment.current != Environment.PRODUCTION) {
            def df = new SimpleDateFormat('yyyy-MM-dd')

            def members = [
                new Member(lastname:'Savage', firstname:'David', birth:df.parse('1959-12-02'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1986-07-12')),
                new Member(lastname:'Savage', firstname:'Gaile', birth:df.parse('1963-01-09'), isMale:false, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1990-09-23')),
                new Member(lastname:'Savage', firstname:'Jude', birth:df.parse('1980-01-16'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30')),
                new Member(lastname:'Savage', firstname:'Saskia', birth:df.parse('1975-04-23'), isMale:false, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30')),
                new Member(lastname:'Savage', firstname:'Kieran', birth:df.parse('1970-05-11'), isMale:true, isPublisher:true, groupUnit:Groups.PENFIELDS_HOUSE, immersion:df.parse('1999-12-30'))
            ]
            members.each {
                it.save()
            }

            def cal = Calendar.instance
            cal.set(2008,8,1)
            def regPioneers = [
                new MemberState(name:States.REGULAR_PIONEER, starting:cal.getTime(), member:members[1])
            ]
            regPioneers.each {
                it.save(true)
            }
        }
    }
    
    def destroy = {
    }
} 