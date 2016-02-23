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

import com.zasadnyy.gradle.unity.config.AndroidBuildConfig
import com.zasadnyy.gradle.unity.config.BuildConfig
import com.zasadnyy.gradle.unity.config.IosBuildConfig
import com.zasadnyy.gradle.unity.config.IosScriptingBackend
import com.zasadnyy.gradle.unity.config.IosSdkVersion
import com.zasadnyy.gradle.unity.config.IosVersion
import com.zasadnyy.gradle.unity.config.Platform
import org.gradle.api.Project

class UnityPluginExtension {
    
    String projectPath
    BuildConfig common
    AndroidBuildConfig android
    IosBuildConfig ios

    private Map buildConfigPlatformMapping
    private Project project

    
    UnityPluginExtension(Project project) {
        this.project = project
        setDefaults(project)
        this.buildConfigPlatformMapping = [Android:android, Ios:ios]
    }

    void projectPath(projectPath) {
        this.projectPath = projectPath
    }
    
    void common(Closure closure) {
        applyClosure(common, closure)
    }

    void android(Closure closure) {
        applyClosure(android, closure)
    }

    void ios(Closure closure) {
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
            splitApplicationBinary false
            bundleVersionCode 1
        }
        ios = project.configure(new IosBuildConfig(project)) {
            outputPath 'dist/ios/'
            outputName 'ios'
            shortBundleVersion 'v1.0.0'
            scriptingBackend IosScriptingBackend.IL2CPP
            sdkVersion IosSdkVersion.DeviceSDK
            targetIosVersion IosVersion.iOS_8_1
        }
    }

    private void applyClosure(Object target, Closure closure) {
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = target
        closure()
    }
}
