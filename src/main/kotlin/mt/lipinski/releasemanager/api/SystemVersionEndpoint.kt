package mt.lipinski.releasemanager.api

import mt.lipinski.releasemanager.domain.ReleaseManager
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SystemVersionEndpoint(private val releaseManager: ReleaseManager) {
    @GetMapping(value = ["/services"], produces = [APPLICATION_JSON_VALUE])
    fun getSystemVersion(@RequestParam systemVersion: Int) =
        releaseManager.getSystemVersion(systemVersion)
            .services
            .map { it.toServiceVersionResponse() }
}

data class ServiceVersionResponse(
    val name: String,
    val version: Int,
)

private fun Map.Entry<String, Int>.toServiceVersionResponse() =
    ServiceVersionResponse(key, value)
