package com.zasadnyy.unitygradleplugin

import org.gradle.api.Project
import org.gradle.api.NamedDomainObjectContainer

/**
 * Created by vitalik on 23/01/16.
 */

class AndroidBuildConfig extends BuildConfig {

    NamedDomainObjectContainer<AndroidSigningConfig> signingConfigs
    AndroidSigningConfig signingConfig
    boolean splitApplicationBinary
    int bundleVersionCode

    AndroidBuildConfig(Project project) {
        super(project)
        this.signingConfigs = project.container(AndroidSigningConfig)
        this.splitApplicationBinary = false
    }

    void signingConfigs(Closure closure) {
        signingConfigs.configure(closure)
    }

    void signingConfig(AndroidSigningConfig signingConfig) {
        this.signingConfig = signingConfig
    }

    void splitApplicationBinary(boolean splitApplicationBinary) {
        this.splitApplicationBinary = splitApplicationBinary
    }

    void bundleVersionCode(int bundleVersionCode) {
        this.bundleVersionCode = bundleVersionCode
    }

    @Override
    Map asMap() {
        def map = super.asMap()
        map.Android = [
                SplitApplicationBinary: splitApplicationBinary,
                BundleVersionCode: bundleVersionCode,
                SigningConfig: signingConfig.asMap()
        ]
        return map
    }
}
