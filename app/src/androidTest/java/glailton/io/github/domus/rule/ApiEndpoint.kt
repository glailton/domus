package glailton.io.github.domus.rule

class ApiEndpoint {
    var baseUrl: String = "https://0.0.0.0:8001"
        set(url) {
            field = url
            if (!url.endsWith("/")) {
                field += "/"
            }
        }
}