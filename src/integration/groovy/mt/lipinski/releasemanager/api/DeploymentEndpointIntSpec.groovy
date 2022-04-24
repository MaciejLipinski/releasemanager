package mt.lipinski.releasemanager.api

import groovyx.net.http.HttpResponseDecorator
import mt.lipinski.releasemanager.BaseIntSpec
import mt.lipinski.releasemanager.domain.SystemVersionRepository
import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.http.HttpStatus.CREATED

class DeploymentEndpointIntSpec extends BaseIntSpec {

    @Autowired
    SystemVersionRepository systemVersionRepository

    def 'should return CREATED and register service deployment'() {
        when:
            def response = restClient.post([
                    path: '/deploy',
                    body: [
                            serviceName         : 'Service A',
                            serviceVersionNumber: '1'
                    ]
            ]) as HttpResponseDecorator
        then:
            response.status == CREATED.value()
            response.data == 1
        and:
            with(systemVersionRepository.currentVersion) {
                it.version == 1
                it.services == ['Service A': 1]
            }
    }
}
