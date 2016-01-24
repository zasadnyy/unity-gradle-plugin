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

    def getName() {
        return this.name
    }

    def storePath(storePath) {
        this.storePath = storePath
    }

    def storePassword(storePassword) {
        this.storePassword = storePassword
    }

    def keyAlias(keyAlias) {
        this.keyAlias = keyAlias
    }

    def keyPassword(keyPassword) {
        this.keyPassword = keyPassword
    }
}