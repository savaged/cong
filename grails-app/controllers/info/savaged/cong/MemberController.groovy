package info.savaged.cong

class MemberController {

    def scaffold = Member

    def search = {
        if (request.method == 'POST') {

            def member = Member.findByLastnameAndFirstname(params?.lastname, params?.firstname)
            
            redirect(uri:"/member/show/${member?.id}")
        }
    }
}
