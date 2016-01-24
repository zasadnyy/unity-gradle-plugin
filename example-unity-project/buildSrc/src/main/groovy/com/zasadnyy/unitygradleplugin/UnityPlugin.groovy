package com.zasadnyy.unitygradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.process.ExecResult
import groovy.json.JsonOutput

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

            def serializedBuildConfig = JsonOutput.toJson(buildConfig)

            commandLine "${plugin.unityPath}/Unity",
                    '-batchmode',
                    '-projectPath', "${project.rootDir.absolutePath}/${plugin.projectPath}",
                    '-logFile', buildConfig.logFile,
                    '-executeMethod', GRADLE_BUILD_HELPER_CSHARP_ENTRY_METHOD,
                    "-TargetPlatform=${targetPlatform.unityBuildTarget}",
                    "-BuildConfig=$serializedBuildConfig",
                    '-quit'

            println "   Building Unity ${targetPlatform} player with configuration:\n${buildConfig}"
        }
    }
}