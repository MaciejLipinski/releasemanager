package mt.lipinski.releasemanager.domain

interface SystemVersionRepository {
    fun getCurrentVersion(): SystemVersion
    fun getVersion(version: Int): SystemVersion
    fun save(systemVersion: SystemVersion): Int
}

data class SystemVersion(
    val version: Int,
    val services: Map<String, Int>
)

class SystemVersionNotFoundException(version: Int) : RuntimeException("System version $version not found")
