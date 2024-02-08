import org.gradle.api.JavaVersion

// Keep: used in the CI/CD to retrieve the app version
private const val appVersionName = "0.0.1"

object Versions {
    val app = App()
    val java = JavaVersion.VERSION_17
    val dependencies = Dependencies()

    class App {
        val name = appVersionName
        val code = 1

        operator fun invoke(action: App.() -> Unit) = action()
    }

    class Dependencies {
        val mavericks = "3.0.7"
        val hilt = "2.44"
        val android = Android()
        val test = Test()

        class Android {
            val core = "1.9.0"
            val splashscreen = "1.0.1"
            val kotlin = Kotlin()
            val compose = Compose()

            class Kotlin {
                val lifecycleRuntime = "2.6.2"
            }

            class Compose {
                val bom = "2023.03.00"
                val activity = "1.8.0"
                val icons = "1.5.3"
                val animation = "1.6.0"
            }
        }

        class Test {
            val assertj = "3.11.1"
            val mockk = "1.13.8"
            val espresso = "3.5.1"
            val jacoco = "0.8.11"
            val jUnit = "5.10.0"
            val android = Android()
            val kotlin = Kotlin()

            class Android {
                val runner = "1.5.2"
            }

            class Kotlin {
                val coroutine = "1.6.4"
            }
        }

        operator fun invoke(action: Dependencies.() -> Unit) = action()
    }
}
