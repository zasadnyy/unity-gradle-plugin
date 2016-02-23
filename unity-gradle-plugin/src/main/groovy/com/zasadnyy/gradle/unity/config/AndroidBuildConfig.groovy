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
import org.gradle.api.NamedDomainObjectContainer

class AndroidBuildConfig extends BuildConfig {

    NamedDomainObjectContainer<AndroidSigningConfig> signingConfigs
    AndroidSigningConfig signingConfig
    boolean splitApplicationBinary
    int bundleVersionCode

    AndroidBuildConfig(Project project) {
        super(project)
        this.signingConfigs = project.container(AndroidSigningConfig)
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
