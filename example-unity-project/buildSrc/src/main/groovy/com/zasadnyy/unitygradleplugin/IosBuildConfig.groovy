package com.zasadnyy.unitygradleplugin

import org.gradle.api.Project


/**
 * @author Vitaliy Zasadnyy on 20/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */
public class IosBuildConfig extends BuildConfig {

    String shortBundleVersion
    IosScriptingBackend scriptingBackend
    IosSdkVersion sdkVersion
    IosVersion targetIosVersion

    IosBuildConfig(Project project) {
        super(project)
    }
    
    void shortBundleVersion(String shortBundleVersion) {
        this.shortBundleVersion = shortBundleVersion
    }
    
    void scriptingBackend(IosScriptingBackend scriptingBackend) {
        this.scriptingBackend = scriptingBackend
    }
    
    void sdkVersion(IosSdkVersion sdkVersion) {
        this.sdkVersion = sdkVersion
    }
    
    void targetIosVersion(IosVersion targetIosVersion) {
        this.targetIosVersion = targetIosVersion
    }

    @Override
    Map asMap() {
        def map = super.asMap()
        map.Ios = [
                ShortBundleVersion: shortBundleVersion,
                ScriptingBackend: scriptingBackend,
                SdkVersion: sdkVersion,
                TargetIosVersion: targetIosVersion
        ]
        return map
    }
}
