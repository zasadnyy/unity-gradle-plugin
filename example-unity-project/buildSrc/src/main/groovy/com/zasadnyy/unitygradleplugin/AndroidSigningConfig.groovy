package com.zasadnyy.unitygradleplugin

/**
 * Created by vitalik on 23/01/16.
 */

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