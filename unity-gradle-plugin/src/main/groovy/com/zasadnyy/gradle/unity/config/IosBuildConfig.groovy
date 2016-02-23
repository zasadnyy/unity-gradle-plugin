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

    void scriptingBackend(def scriptingBackend) {
        this.scriptingBackend = scriptingBackend
    }

    void sdkVersion(def sdkVersion) {
        this.sdkVersion = sdkVersion
    }

    void targetIosVersion(def targetIosVersion) {
        this.targetIosVersion = targetIosVersion
    }

    @Override
    Map asMap() {
        def map = super.asMap()
        map.Ios = [
                ShortBundleVersion: shortBundleVersion,
                ScriptingBackend  : scriptingBackend,
                SdkVersion        : sdkVersion,
                TargetIosVersion  : targetIosVersion
        ]
        return map
    }
}
