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

public class AndroidSigningConfig {
    String storePath
    String storePassword
    String keyAlias
    String keyPassword

    private String name

    AndroidSigningConfig(String name) {
        this.name = name
    }

    String getName() {
        return this.name
    }

    void storePath(String storePath) {
        this.storePath = storePath
    }

    void storePassword(String storePassword) {
        this.storePassword = storePassword
    }

    void keyAlias(String keyAlias) {
        this.keyAlias = keyAlias
    }

    void keyPassword(String keyPassword) {
        this.keyPassword = keyPassword
    }

    Map asMap() {
        return [
            StorePath: storePath,
            StorePassword: storePassword,
            KeyAlias: keyAlias,
            KeyPassword: keyPassword
        ]
    }
}