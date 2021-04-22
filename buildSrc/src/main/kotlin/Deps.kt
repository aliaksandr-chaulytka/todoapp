object Deps {
    object Jetbrains {
        object Kotlin : Group(name = "org.jetbrains.kotlin") {
            private const val version = "1.4.30"

            object Plugin {
                object Gradle :
                    Dependency(group = Kotlin, name = "kotlin-gradle-plugin", version = version)
            }

            object StdLib {
                object StdLib :
                    Dependency(group = Kotlin, name = "kotlin-stdlib", version = version)
            }
        }

        object KotlinX : Group(name = "org.jetbrains.kotlinx") {
            object Coroutines {
                private const val version = "1.4.2"

                object Core :
                    Dependency(group = KotlinX, name = "kotlinx-coroutines-core", version = version)

                object Android : Dependency(
                    group = KotlinX,
                    name = "kotlinx-coroutines-android",
                    version = version
                )
            }
        }
    }

    object Android {
        object Tools {
            object Build : Group(name = "com.android.tools.build") {
                object Gradle : Dependency(group = Build, name = "gradle", version = "4.1.3")
            }
        }
    }

    object AndroidX {
        object Core : Group(name = "androidx.core") {
            object Ktx : Dependency(group = Core, name = "core-ktx", version = "1.3.2")
        }

        object AppCompat : Group(name = "androidx.appcompat") {
            object AppCompat :
                Dependency(group = AndroidX.AppCompat, name = "appcompat", version = "1.1.0")
        }

        object RecyclerView : Group(name = "androidx.recyclerview") {
            object RecyclerView :
                Dependency(group = AndroidX.RecyclerView, name = "recyclerview", version = "1.1.0")
        }

        object ConstraintLayout : Group(name = "androidx.constraintlayout") {
            object ConstraintLayout : Dependency(
                group = AndroidX.ConstraintLayout,
                name = "constraintlayout",
                version = "1.1.3"
            )
        }

        object Lifecycle : Group(name = "androidx.lifecycle") {
            private const val version = "2.3.1"

            object ViewModelKtx :
                Dependency(group = Lifecycle, name = "lifecycle-viewmodel-ktx", version = version)

            object ViewModel :
                Dependency(group = Lifecycle, name = "lifecycle-viewmodel", version = version)

            object ViewModelSavedState : Dependency(
                group = Lifecycle,
                name = "lifecycle-viewmodel-savedstate",
                version = version
            )

            object Runtime :
                Dependency(group = Lifecycle, name = "lifecycle-runtime", version = version)

        }
    }

    object Google {

        object AndroidMaterial : Group(name = "com.google.android.material") {

            object Material :
                Dependency(group = AndroidMaterial, name = "material", version = "1.3.0")
        }
    }

    object Squareup {

        object Retrofit2 : Group(name = "com.squareup.retrofit2") {
            val version = "2.9.0"

            object Retrofit :
                Dependency(group = Retrofit2, name = "retrofit", version = version)

            object ConvertorGson :
                Dependency(group = Retrofit2, name = "converter-gson", version = version)
        }
    }

    open class Group(val name: String)

    open class Dependency private constructor(
        private val notation: String
    ) : CharSequence by notation {
        constructor(
            group: Group,
            name: String,
            version: String
        ) : this("${group.name}:$name:$version")

        override fun toString(): String = notation
    }
}