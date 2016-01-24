package com.zasadnyy.unitygradleplugin

import org.gradle.api.Project

class UnityPluginExtension {
    
    String unityPath
    String projectPath
    BuildConfig common
    AndroidBuildConfig android
    BuildConfig ios

    private def buildConfigPlatformMapping
    private Project project

    
    UnityPluginExtension(Project project) {
        this.project = project
        setDefaults(project)
        this.buildConfigPlatformMapping = [Android:android, IOS:ios]
    }

    def unityPath(unityPath) {
        this.unityPath = unityPath
    }
    
    def projectPath(projectPath) {
        this.projectPath = projectPath
    }
    
    def common(Closure closure) {
        applyClosure(common, closure)
    }

    def android(Closure closure) {
        applyClosure(android, closure)
    }

    def ios(Closure closure) {
        applyClosure(ios, closure)
    }

    BuildConfig getMergedBuildConfig(Platform platform) {
        def target = buildConfigPlatformMapping[platform.toString()]
        return mergeBuildConfigs(common, target)
    }

    private BuildConfig mergeBuildConfigs(BuildConfig common, BuildConfig target) {
        def result = target.class.newInstance(project)
        result.properties
            .findAll {it.key != 'class'}
            .each {
                def property = it.key
                def value = target.properties[property] != null ? target.properties[property] : common.properties[property]
                result.setProperty(property, value)
            }
        return result
    }

    private void setDefaults(Project project) {
        unityPath = '/Applications/Unity/Unity.app/Contents/MacOS'
        projectPath = ''
        common = project.configure(new BuildConfig(project)) {
            outputPath 'dist/'
            outputName 'unity-app'
            logFile "$outputPath/build-log.txt"
            bundleId 'com.company'
            bundleVersion '0.0.0'
            isDevelopmentBuild false
            enableScriptDebugging false
        }
        android = project.configure(new AndroidBuildConfig(project)) {
            outputPath 'dist/android/'
            outputName 'android'
        }
        ios = new BuildConfig(project)
    }

    private def applyClosure(Object target, Closure closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = target
        closure()
    }
}
