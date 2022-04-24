package mt.lipinski.releasemanager.adapter

import mt.lipinski.releasemanager.BaseIntSpec
import mt.lipinski.releasemanager.domain.SystemVersion
import mt.lipinski.releasemanager.domain.SystemVersionNotFoundException
import mt.lipinski.releasemanager.domain.SystemVersionRepository
import mt.lipinski.releasemanager.support.InMemorySystemVersionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import spock.lang.Subject

class MongoSystemVersionRepositorySpec extends SystemVersionRepositorySpec {

    @Autowired
    MongoSystemVersionRepository mongoRepository

    @Override
    SystemVersionRepository testRepository() {
        mongoRepository
    }
}

class InMemorySystemVersionRepositorySpec extends SystemVersionRepositorySpec {

    @Override
    SystemVersionRepository testRepository() {
        new InMemorySystemVersionRepository()
    }
}

abstract class SystemVersionRepositorySpec extends BaseIntSpec {

    abstract SystemVersionRepository testRepository()

    @Subject
    SystemVersionRepository repository

    def setup() {
        repository = testRepository()
    }

    def 'should return initial system version'() {
        when:
            def initialSystemVersion = repository.getCurrentVersion()
        then:
            initialSystemVersion.version == 0
            initialSystemVersion.services.isEmpty()
    }

    def 'should save system version'() {
        given:
            def systemVersion = new SystemVersion(1, [:])
        and:
            repository.save(systemVersion)
        expect:
            repository.currentVersion == systemVersion
    }

    def 'should return highest system version'() {
        given:
            def firstSystemVersion = new SystemVersion(1, [:])
            def secondSystemVersion = new SystemVersion(2, [:])
        and:
            repository.save(firstSystemVersion)
            repository.save(secondSystemVersion)
        expect:
            repository.currentVersion == secondSystemVersion
    }

    def 'should do nothing when trying to save the same system version twice'() {
        given:
            def systemVersion = new SystemVersion(1, [:])
        and:
            repository.save(systemVersion)
            repository.save(systemVersion)
        expect:
            repository.currentVersion == systemVersion
    }

    def 'should throw DuplicateKeyException when trying to overwrite a system version'() {
        given:
            def systemVersion = new SystemVersion(1, [:])
            def differentSystemVersion = new SystemVersion(1, ['Service A': 1])
        and:
            repository.save(systemVersion)
        when:
            repository.save(differentSystemVersion)
        then:
            thrown(DuplicateKeyException)
    }

    def 'should throw SystemVersionNotFoundException when requested system version does not exist'() {
        when:
            repository.getVersion(1)
        then:
            def ex = thrown(SystemVersionNotFoundException)
            ex.message == "System version 1 not found"
    }

    def 'should return requested system version'() {
        given:
            def firstSystemVersion = new SystemVersion(1, [:])
            def secondSystemVersion = new SystemVersion(2, [:])
        and:
            repository.save(firstSystemVersion)
            repository.save(secondSystemVersion)
        expect:
            repository.getVersion(1) == firstSystemVersion
            repository.getVersion(2) == secondSystemVersion
    }
}
