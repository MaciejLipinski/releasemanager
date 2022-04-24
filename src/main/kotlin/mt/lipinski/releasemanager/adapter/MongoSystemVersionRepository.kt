package mt.lipinski.releasemanager.adapter

import mt.lipinski.releasemanager.domain.SystemVersion
import mt.lipinski.releasemanager.domain.SystemVersionNotFoundException
import mt.lipinski.releasemanager.domain.SystemVersionRepository
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class MongoSystemVersionRepository(private val mongoOperations: MongoOperations) : SystemVersionRepository {
    companion object {
        private const val VERSION = "version"
    }

    override fun getCurrentVersion(): SystemVersion =
        mongoOperations.findAll(MongoSystemVersion::class.java)
            .map { it.toSystemVersion() }
            .maxByOrNull { it.version }
            ?: initialSystemVersion()

    override fun getVersion(version: Int): SystemVersion =
        findVersion(version)
            ?: throw SystemVersionNotFoundException(version)

    override fun save(systemVersion: SystemVersion): Int {
        if (systemVersion == findVersion(systemVersion.version)) {
            return systemVersion.version
        }

        return systemVersion.toMongo()
            .also { mongoOperations.insert(it) }
            .version
    }

    private fun findVersion(version: Int): SystemVersion? =
        mongoOperations.findOne(query(where(VERSION).isEqualTo(version)), MongoSystemVersion::class.java)
            ?.toSystemVersion()

    private fun initialSystemVersion() = SystemVersion(0, emptyMap())
}

@Document("system_version")
data class MongoSystemVersion(
    @field:Id val version: Int,
    val services: Map<String, Int>,
) {
    fun toSystemVersion() = SystemVersion(
        version = version,
        services = services,
    )
}

fun SystemVersion.toMongo() = MongoSystemVersion(
    version = version,
    services = services,
)
