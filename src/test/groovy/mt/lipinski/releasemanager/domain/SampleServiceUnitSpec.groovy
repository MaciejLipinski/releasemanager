package mt.lipinski.releasemanager.domain

import spock.lang.Specification

class SampleServiceUnitSpec extends Specification {

    SampleService sampleService = new SampleService()

    def 'should return value'() {
        expect:
            sampleService.getValue() == 1
    }
}
