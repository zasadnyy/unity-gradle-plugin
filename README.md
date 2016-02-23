# Unity Gradle Plugin
Build your Unity 3D projects with Gradle.

## Features
* Supports Android and iOS builds
* The most important Player Settings exposed via Gradle DSL

#### Upcoming features
* Tasks to run Unit and Integration tests
* Distribute helper classes from `Assets/GradleBuild` folder as a .unitypackage
* More supported platforms (next one Mac/Linux)


## Requirnments
* Gradle added to $PATH
* Mac machine (not tested on Windows)


## Usage
1. `$cd [unity project root]`
1. `$gradle init` - initialize Gradle wrapper, create settings.gradle, build.gradle
1. Configure `unity.path` in `local.properties`, e.g. `unity.path=/Applications/Unity/Unity.app/Contents/MacOS`
1. Apply unity plugin in `build.gradle`

    ```groovy
    plugins {
        id "com.zasadnyy.unity" version "0.1.1"
    }
    ```

1. Add Unity plugin configuration

    ```groovy
    unity {
        common {
            bundleId 'com.zasadnyy.testapp'
            bundleVersion 'v0.1'
            scenes 'Assets/Scenes/MainScene.unity', 'Assets/Scenes/MainScene1.unity'
        }

        android {
            splitApplicationBinary false
            bundleVersionCode 42
            signingConfigs {
                debug {
                    storePath 'keystores/get-social-debug.keystore'
                    storePassword 'android'
                    keyAlias 'androiddebugkey'
                    keyPassword 'android'
                }
            }
            signingConfig signingConfigs.debug
        }

        ios {
            shortBundleVersion 'v0.1'
        }
    }
    ```
1. Copy folder `Assets/GradleBuild` from [example-unity-project](https://github.com/zasadnyy/unity-gradle-plugin/tree/master/example-unity-project) into your Unity project
1. Check available tasks with `$./gradlew tasks`
1. Build selected platform, e.g. `$./gradlew buildAndroidPlayer`
1. Check the output in `dist/android` folder


## Docs
*Full DSL documentation coming soon... Meantime, check example to find available options.*


## Example project
Check the [example-unity-project](https://github.com/zasadnyy/unity-gradle-plugin/tree/master/example-unity-project) folder for an example project.


## Changelog

#### v0.1.1 - February 22, 2016
  * Initial release


## License
The project is published under the [Apache 2](https://github.com/zasadnyy/unity-gradle-plugin/blob/master/LICENSE) license. Feel free to clone and modify repo as you want, but don't forget to add a reference to the author.

