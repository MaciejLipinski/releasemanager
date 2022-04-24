package mt.lipinski.releasemanager.support

import mt.lipinski.releasemanager.domain.SystemVersion
import mt.lipinski.releasemanager.domain.SystemVersionNotFoundException
import mt.lipinski.releasemanager.domain.SystemVersionRepository
import org.springframework.dao.DuplicateKeyException

class InMemorySystemVersionRepository implements SystemVersionRepository {

    private Map<Integer, SystemVersion> repository = new HashMap<>()

    @Override
    SystemVersion getCurrentVersion() {
        if (repository.isEmpty()) {
            return new SystemVersion(0, [:])
        }
        repository.max { it.key }.value
    }

    @Override
    SystemVersion getVersion(int version) {
        def requestedVersion = repository[version]

        if (requestedVersion == null) {
            throw new SystemVersionNotFoundException(version)
        }

        return requestedVersion
    }

    @Override
    int save(SystemVersion systemVersion) {
        def existingSystemVersion = repository.get(systemVersion.version)
        if (existingSystemVersion == null) {
            repository.put(systemVersion.version, systemVersion)
        } else if (existingSystemVersion != systemVersion) {
            throw new DuplicateKeyException("System version conflict")
        }
        systemVersion.version
    }
}
