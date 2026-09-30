package com.baidaidai.rootless_store.domain.install.model

data class LocalPackageMetaInfo(
    val displayName: String,
    val author: String,
    val validSigner: Boolean = false,
    val isSigned: Boolean? = null,
)
