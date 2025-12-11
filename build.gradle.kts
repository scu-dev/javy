import org.gradle.internal.os.OperatingSystem
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.JavaExec
import org.gradle.jvm.application.tasks.CreateStartScripts

plugins {
    java
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

var lwjglVersion = "2.9.3"

val os: OperatingSystem = OperatingSystem.current()
val nativesClassifier =
    when {
        os.isWindows -> "natives-windows"
        os.isLinux   -> "natives-linux"
        os.isMacOsX  -> "natives-osx"
        else         -> error("Unsupported operating system: $os")
    }

val lwjglNatives: Configuration by configurations.creating
val jinputNatives: Configuration by configurations.creating

dependencies {
    // Slick2D
    implementation("org.slick2d:slick2d-core:1.0.2")
    // LWJGL Java side
    implementation("org.lwjgl.lwjgl:lwjgl:$lwjglVersion")
    implementation("org.lwjgl.lwjgl:lwjgl_util:${lwjglVersion}")
    // LWJGL native libs
    lwjglNatives("org.lwjgl.lwjgl:lwjgl-platform:$lwjglVersion:$nativesClassifier")
    // JInput
    implementation("net.java.jinput:jinput:2.0.5")
    jinputNatives("net.java.jinput:jinput-platform:2.0.5:$nativesClassifier")
}

val copyNatives by tasks.registering(Copy::class) {
    from(lwjglNatives.resolve().map { zipTree(it) })
    from(jinputNatives.resolve().map { zipTree(it) })
    include("**/*.dll", "**/*.so", "**/*.dylib", "**/*.jnilib")
    eachFile {
        path = name
    }
    includeEmptyDirs = false
    into(layout.buildDirectory.dir("natives"))
}

application {
    mainClass.set("com.ljm12914.javy.Launcher")
}

tasks.named<CreateStartScripts>("startScripts") {
    dependsOn(copyNatives)
    defaultJvmOpts = listOf(
        "-Djava.library.path=MY_APP_HOME/natives",
        "-Dorg.lwjgl.librarypath=MY_APP_HOME/natives"
    )
    doLast {
        unixScript.writeText(
            unixScript.readText().replace("MY_APP_HOME", "\$APP_HOME")
        )
        windowsScript.writeText(
            windowsScript.readText().replace("MY_APP_HOME", "%~dp0..")
        )
    }
}

tasks.named<JavaExec>("run") {
    dependsOn(copyNatives)
    doFirst {
        val nativesDir = layout.buildDirectory.dir("natives").get().asFile.absolutePath
        jvmArgs("-Djava.library.path=$nativesDir")
        systemProperty("org.lwjgl.librarypath", nativesDir)
        println("Using java.library.path = $nativesDir")
    }
}

distributions {
    main {
        contents {
            from(copyNatives.map { it.destinationDir }) {
                into("natives")
            }
        }
    }
}