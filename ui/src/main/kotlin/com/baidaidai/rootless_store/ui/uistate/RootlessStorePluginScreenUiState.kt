package com.baidaidai.rootless_store.ui.uistate

import android.net.Uri
import com.baidaidai.rootless_store.domain.install.model.LocalPackageMetaInfo

data class RootlessStorePluginScreenUiState(
    val localPackageUri: Uri? = null,
    val localPackageMetaInfo: LocalPackageMetaInfo? = null,
    val isLocalPackageConfirmationSheetVisible: Boolean = false
)
