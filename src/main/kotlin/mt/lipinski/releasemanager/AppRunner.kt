package mt.lipinski.releasemanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppRunner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<AppRunner>(*args)
        }
    }
}
