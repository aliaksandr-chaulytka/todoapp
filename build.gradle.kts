buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Deps.Android.Tools.Build.Gradle)
        classpath(Deps.Jetbrains.Kotlin.Plugin.Gradle)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete({ rootProject.buildDir })
}