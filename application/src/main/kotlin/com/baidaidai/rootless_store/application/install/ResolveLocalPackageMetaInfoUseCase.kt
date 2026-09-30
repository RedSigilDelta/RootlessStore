package com.baidaidai.rootless_store.application.install

import android.net.Uri
import com.baidaidai.illusioncube.IllusionCube
import com.baidaidai.rootless_store.data.fileSystem.gateway.AndroidFileSystemReadOperatorGatewayImpl
import com.baidaidai.rootless_store.domain.install.model.LocalPackageMetaInfo
import com.baidaidai.rootless_store.domain.plugin.manifest.MagiskProp
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ResolveLocalPackageMetaInfoUseCase @Inject constructor(
    private val androidFileSystemReadOperatorGatewayImpl: AndroidFileSystemReadOperatorGatewayImpl
) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    operator fun invoke(
        uri: Uri
    ): LocalPackageMetaInfo? {
        val pluginManifestJson = runCatching {
            androidFileSystemReadOperatorGatewayImpl.loadRawPluginManifest(uri)
        }.getOrDefault("")

        if (pluginManifestJson.isNotBlank()) {
            return runCatching {
                val pluginManifest = androidFileSystemReadOperatorGatewayImpl.parsePluginManifest(pluginManifestJson)
                LocalPackageMetaInfo(
                    displayName = pluginManifest.pluginRenderingName,
                    author = pluginManifest.author,
                    validSigner = false,
                    isSigned = null,
                )
            }.getOrNull()
        }

        val environmentManifestJson = runCatching {
            androidFileSystemReadOperatorGatewayImpl.loadRawEnvironmentManifest(uri)
        }.getOrDefault("")

        if (environmentManifestJson.isNotBlank()) {
            return runCatching {
                val environmentManifest = androidFileSystemReadOperatorGatewayImpl.parseEnvironmentManifest(environmentManifestJson)
                LocalPackageMetaInfo(
                    displayName = environmentManifest.environmentRenderingName,
                    author = environmentManifest.author,
                    validSigner = false,
                    isSigned = null,
                )
            }.getOrNull()
        }

        val magiskModulePropContent = runCatching {
            androidFileSystemReadOperatorGatewayImpl.loadRawMagiskModuleProp(uri)
        }.getOrDefault("")

        if (magiskModulePropContent.isBlank()) return null
        if (!IllusionCube.Prop.validate(magiskModulePropContent)) return null

        return runCatching {
            val magiskModulePropJson = IllusionCube.Prop(magiskModulePropContent).encodeAsJson()
            val magiskProp = json.decodeFromString<MagiskProp>(magiskModulePropJson)
            LocalPackageMetaInfo(
                displayName = magiskProp.name,
                author = magiskProp.author,
                validSigner = false,
                isSigned = null,
            )
        }.getOrNull()
    }
}
