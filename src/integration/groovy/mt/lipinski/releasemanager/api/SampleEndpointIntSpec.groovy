package mt.lipinski.releasemanager.api

import groovyx.net.http.HttpResponseDecorator
import mt.lipinski.releasemanager.BaseIntSpec

import static org.springframework.http.HttpStatus.OK

class SampleEndpointIntSpec extends BaseIntSpec {

    def 'should return OK'() {
        when:
            def response = restClient.get([path: '/value']) as HttpResponseDecorator
        then:
            response.status == OK.value()
            response.data == 1
    }
}
