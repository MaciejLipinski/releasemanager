package mt.lipinski.releasemanager.domain

import org.springframework.stereotype.Component

@Component
class ReleaseManager(private val systemVersionRepository: SystemVersionRepository) {
    fun register(serviceDeployment: ServiceDeployment): Int {
        val currentSystemVersion = systemVersionRepository.getCurrentVersion()

        if (currentSystemVersion.services[serviceDeployment.name] == serviceDeployment.version) {
            return currentSystemVersion.version
        }

        return currentSystemVersion.copy(
            version = currentSystemVersion.version + 1,
            services = currentSystemVersion.services.plus(serviceDeployment.name to serviceDeployment.version)
        ).let { systemVersionRepository.save(it) }
    }

    fun getSystemVersion(version: Int): SystemVersion =
        systemVersionRepository.getVersion(version)
}

data class ServiceDeployment(
    val name: String,
    val version: Int,
)
