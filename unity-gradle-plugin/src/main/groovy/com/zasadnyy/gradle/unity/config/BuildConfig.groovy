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

package com.zasadnyy.gradle.unity.config

import org.gradle.api.Project

class BuildConfig {

    String outputPath
    String outputName
    String logFile
    String bundleId
    String bundleVersion
    boolean isDevelopmentBuild
    boolean enableScriptDebugging
    String[] scenes

    protected Project project

    BuildConfig(Project project) {
        this.project = project
    }

    void outputPath(String outputPath) {
        this.outputPath = outputPath
    }

    void outputName(String outputName) {
        this.outputName = outputName
    }

    void logFile(String logFile) {
        this.logFile = logFile
    }

    void bundleId(String bundleId) {
        this.bundleId = bundleId
    }

    void bundleVersion(String bundleVersion) {
        this.bundleVersion = bundleVersion;
    }

    void isDevelopmentBuild(boolean isDevelopmentBuild) {
        this.isDevelopmentBuild = isDevelopmentBuild
    }

    void enableScriptDebugging(boolean enableScriptDebugging) {
        this.enableScriptDebugging = enableScriptDebugging
    }

    void scenes(String... scenes) {
        this.scenes = scenes
    }

    Map asMap() {
        return  [
            OutputPath: outputPath,
            OutputName: outputName,
            LogFile: logFile,
            BundleId: bundleId,
            BundleVersion: bundleVersion,
            IsDevelopmentBuild: isDevelopmentBuild,
            EnableScriptDebugging: enableScriptDebugging,
            Scenes: scenes
        ]
    }

    @Override
    String toString() {
        def sb = new StringBuilder()
        this.properties
                .findAll { it.key != 'class' }
                .sort()
                .each { sb.append("      ${it.key} = ${it.value}\n") }
        return sb.toString()
    }
}