package mt.lipinski.releasemanager.api

import mt.lipinski.releasemanager.domain.SampleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleEndpoint(private val sampleService: SampleService) {

    @GetMapping("/value")
    fun getValue(): Int =
        sampleService.getValue()
}
