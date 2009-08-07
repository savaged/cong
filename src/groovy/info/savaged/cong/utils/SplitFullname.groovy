package info.savaged.cong.utils

class SplitFullname {

    static String extractLastname(String fullname) {
        fullname.substring(0, fullname.indexOf(','))
    }

    static String extractFirstname(String fullname) {
        fullname.substring(fullname.indexOf(',') + 1, fullname.length()).trim()
    }
}

