package mz.org.fgh.sifmoz.backend.provincialServer



class ProvincialServer {

    Long id
    String code
    String urlPath
    String port
    String destination
    String username
    String password


    static constraints = {
        urlPath(nullable: false, blank: false)
        code(nullable: false, maxSize: 50, blank: false,unique: ['code', 'destination'])
        username(nullable: false,blank: false)
        password(nullable: false,blank: false)
    }
}
