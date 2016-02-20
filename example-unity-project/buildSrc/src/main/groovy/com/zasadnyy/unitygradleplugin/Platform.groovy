package com.zasadnyy.unitygradleplugin

/**
 * List of platforms supported
 * by Unity Gradle Build plugin
 */
enum Platform {
    Android('Android'), Ios('iPhone')

    /**
     * For some platforms Unity has weird
     * BuildTarget names, so we do mapping
     */
    String unityBuildTarget

    private Platform(unityBuildTarget) {
        this.unityBuildTarget = unityBuildTarget
    }
}