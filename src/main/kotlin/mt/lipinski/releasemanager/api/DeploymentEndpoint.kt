package mt.lipinski.releasemanager.api

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import mt.lipinski.releasemanager.domain.ReleaseManager
import mt.lipinski.releasemanager.domain.ServiceDeployment
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DeploymentEndpoint(
    private val releaseManager: ReleaseManager
) {
    @PostMapping(value = ["/deploy"], consumes = [APPLICATION_JSON_VALUE])
    fun registerServiceDeployment(@RequestBody request: DeploymentRequest) =
        request.toServiceDeployment()
            .let { releaseManager.register(it) }
            .let { ResponseEntity(it, CREATED) }
}

data class DeploymentRequest(
    val serviceName: String,
    val serviceVersionNumber: Int,
) {
    @JsonCreator
    constructor(
        @JsonProperty("serviceName") serviceName: String,
        @JsonProperty("serviceVersionNumber") serviceVersionNumber: String
    ) : this(serviceName, serviceVersionNumber.toInt())

    fun toServiceDeployment() = ServiceDeployment(
        name = serviceName,
        version = serviceVersionNumber,
    )
}
