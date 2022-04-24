package mt.lipinski.releasemanager.support

import mt.lipinski.releasemanager.domain.ServiceDeployment

class TestServiceDeployment {

    private static Map DEFAULT_PARAMS = [
            name   : 'Service X',
            version: 1
    ]

    static ServiceDeployment aServiceDeployment(Map params = [:]) {
        def mergedParams = DEFAULT_PARAMS + params

        new ServiceDeployment(
                mergedParams.name as String,
                mergedParams.version as int
        )
    }
}
