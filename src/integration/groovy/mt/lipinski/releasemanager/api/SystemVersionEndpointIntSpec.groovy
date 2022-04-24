package mt.lipinski.releasemanager.api

import groovyx.net.http.HttpResponseDecorator
import mt.lipinski.releasemanager.BaseIntSpec

import static org.springframework.http.HttpStatus.OK

class SystemVersionEndpointIntSpec extends BaseIntSpec {

    def 'should return system version'() {
        given:
            registerServiceDeployment('Service A', '1')
        when:
            def response = restClient.get([
                    path       : '/services',
                    queryString: 'systemVersion=1'
            ]) as HttpResponseDecorator
        then:
            response.status == OK.value()
            response.data == [
                    [
                            name   : 'Service A',
                            version: 1
                    ]
            ]
    }

    private def registerServiceDeployment(String name, String version) {
        restClient.post([
                path: '/deploy',
                body: [
                        serviceName         : name,
                        serviceVersionNumber: version
                ]
        ])
    }
}
