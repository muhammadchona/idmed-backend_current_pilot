package sifmoz.backend

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        post "/patient/search(.$format)?"(controller:'patient', action:'search')
        get "/patient/clinic/$clinicId(.$format)?"(controller:'patient', action:'getByClinicId')
        get "/patientServiceIdentifier/clinic/$clinicId(.$format)?"(controller:'patientServiceIdentifier', action:'getByClinicId')
        get "/episode/clinic/$clinicId(.$format)?"(controller:'episode', action:'getByClinicId')
        get "/doctor/clinic/$clinicId(.$format)?"(controller:'doctor', action:'getByClinicId')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
