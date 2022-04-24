package mt.lipinski.releasemanager

import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@SpringBootTest(webEnvironment = DEFINED_PORT)
class BaseIntSpec extends Specification {

    @Value('${server.port}')
    int port

    RESTClient restClient

    def setup() {
        restClient = new RESTClient("http://localhost:$port", APPLICATION_JSON_VALUE)
        restClient.headers.put(CONTENT_TYPE, APPLICATION_JSON_VALUE)
    }
}
