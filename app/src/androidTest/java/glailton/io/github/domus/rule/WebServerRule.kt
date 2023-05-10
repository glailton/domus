package glailton.io.github.domus.rule

import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import org.koin.core.component.KoinComponent

class WebServerRule(
) : ExternalResource(), KoinComponent {

    private lateinit var webServer: MockWebServer
    private val apiEndpoint: ApiEndpoint = ApiEndpoint()

    override fun before() {
        webServer = MockWebServer()
        webServer.start(4007)
        apiEndpoint.baseUrl = MOCK_URL
    }

    override fun after() {
        webServer.shutdown()
    }

    private fun enqueueResponse(code: Int, request: String) = webServer.enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(request)
    )

    fun <T> enqueueResponse(data: T) = enqueueResponse(200, Gson().toJson(data))

    fun enqueueError(code: Int = 400, error: String = "Error") = enqueueResponse(code, error)

    companion object {
        const val MOCK_URL = "http://127.0.0.1:4007/"
    }
}
