/*
 *    Copyright (c) 2016 Vitaliy Zasadnyy
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.zasadnyy.gradle.unity

import com.zasadnyy.gradle.unity.config.Platform
import groovy.json.JsonOutput
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.process.ExecResult

class UnityPlugin implements Plugin<Project> {

    private static final String GRADLE_BUILD_HELPER_CSHARP_ENTRY_METHOD = 'Com.Zasadnyy.GradleBuildHelper.BuildInBatchMode'

    void apply(Project project) {
        addExtensions(project)
        addTasks(project)
    }

    private void addExtensions(Project project) {
        project.extensions.create('unity', UnityPluginExtension, project)
    }

    private void addTasks(Project project) {
        Platform.values().each {
            def targetPlatform = it

            project.task("build${targetPlatform}Player") {
                group 'Unity Build'
                description "Build ${targetPlatform} Unity Player with the specified parameters"
                doLast {
                    def result = UnityPlugin.buildPlayer(project, targetPlatform)
                    result.assertNormalExitValue()
                }
            }
        }
    }

    private static ExecResult buildPlayer(Project project, Platform targetPlatform) {
        return project.exec {
            def plugin = project.unity
            def buildConfig = plugin.getMergedBuildConfig(targetPlatform)

            def configMap = buildConfig.asMap()

            // adding new property TargetPlatform
            configMap.TargetPlatform = targetPlatform.unityBuildTarget

            def serializedBuildConfig = JsonOutput.toJson(configMap)

            commandLine "${getUnityPath(project)}/Unity",
                    '-batchmode',
                    '-projectPath', "${project.rootDir.absolutePath}/${plugin.projectPath}",
                    '-logFile', buildConfig.logFile,
                    '-executeMethod', GRADLE_BUILD_HELPER_CSHARP_ENTRY_METHOD,
                    "-BuildConfig=$serializedBuildConfig",
                    '-quit'

            println "   Building Unity ${targetPlatform} player with configuration:\n${buildConfig}"
        }
    }

    private static String getUnityPath(Project project) {
        def localEnvProps = new File("${project.rootDir}/local.properties")
        
        def props = new Properties()
        props.load(new FileInputStream(localEnvProps))

        def unityPath = props.getProperty('unity.path')
        
        if(!unityPath) {
            throw new UnityPluginException(
                    'unity.path property is miss configured. ' +
                    'Ensure local.properties file is created ' +
                    'and unity.path property is set to Unity executable location.'
            )
        }
        
        return unityPath
    }
}

/*
 * ================= Testing tasks =================
 */
// task runUnityUnitTests(type:Exec, dependsOn: 'createOutputDir') {
//     commandLine "${gradle.unityDir}/Unity",
//             '-batchmode',
//             '-projectPath', unityProjPath,
//             '-executeMethod', 'UnityTest.Batch.RunUnitTests',
//             '-logFile', "${outputDirPath}/unit-tests-run-log.txt",
//             "-resultFilePath=${outputDirPath}/unit-tests-result.xml",
//             '-quit'
// }

// task runUnityIntegrationTests(type:Exec, dependsOn: 'createOutputDir') {
//     commandLine "${gradle.unityDir}/Unity",
//             '-batchmode',
//             '-projectPath', unityProjPath,
//             '-executeMethod', 'UnityTest.Batch.RunIntegrationTests',
//             '-testscenes=IntegrationTestsScene',
//             "-targetPlatform=${getIntegrationTestTarget()}",
//             '-logFile', "${outputDirPath}/integration-tests-run-log.txt",
//             "-resultsFileDirectory=${outputDirPath}/integration-tests-results/"
// }