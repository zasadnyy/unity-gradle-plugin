package com.zasadnyy.unitygradleplugin

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

    @Override
    public String toString() {
        def sb = new StringBuilder()
        this.properties
                .findAll { it.key != 'class' }
                .sort()
                .each { sb.append("      ${it.key} = ${it.value}\n") }
        return sb.toString()
    }
}