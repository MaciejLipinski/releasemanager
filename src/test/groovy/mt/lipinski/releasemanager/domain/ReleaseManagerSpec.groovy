package mt.lipinski.releasemanager.domain

import mt.lipinski.releasemanager.support.InMemorySystemVersionRepository
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Subject

import static mt.lipinski.releasemanager.support.TestServiceDeployment.aServiceDeployment

@Stepwise
class ReleaseManagerSpec extends Specification {

    static SystemVersionRepository repository = new InMemorySystemVersionRepository()

    @Subject
    ReleaseManager releaseManager = new ReleaseManager(repository)

    def 'should register service deployment and return system version'() {
        given:
            def serviceDeployment = aServiceDeployment(name: 'Service A', version: 1)
        when:
            def systemVersion = releaseManager.register(serviceDeployment)
        then:
            systemVersion == 1
    }

    def 'should register subsequent service deployment and return incremented system version'() {
        given:
            def secondServiceDeployment = aServiceDeployment(name: 'Service B', version: 1)
        when:
            def secondSystemVersion = releaseManager.register(secondServiceDeployment)
        then:
            secondSystemVersion == 2
    }

    def 'should not increment system version when registering a deployment of already deployed service version'() {
        given:
            def secondServiceDeployment = aServiceDeployment(name: 'Service B', version: 1)
            def thirdServiceDeployment = aServiceDeployment(name: 'Service A', version: 2)
        when:
            def thirdSystemVersion = releaseManager.register(thirdServiceDeployment)
        then:
            thirdSystemVersion == 3
        when:
            def unchangedSystemVersion = releaseManager.register(secondServiceDeployment)
        then:
            unchangedSystemVersion == 3
    }

    def 'should return system version info'() {
        when:
            def systemVersion2 = releaseManager.getSystemVersion(2)
        then:
            systemVersion2.services == [
                    "Service A": 1,
                    "Service B": 1
            ]
        when:
            def systemVersion3 = releaseManager.getSystemVersion(3)
        then:
            systemVersion3.services == [
                    "Service A": 2,
                    "Service B": 1
            ]
    }
}
