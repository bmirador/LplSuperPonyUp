package com.redprisma.lplsuperponyup.di

import com.redprisma.lplsuperponyup.ui.util.AssetPathState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetPathProviderImpl @Inject constructor() :
    AssetPathState {
    override val pathFlow: StateFlow<String>
        get() = MutableStateFlow("")

    override suspend fun setPath(newPath: String) {
        //stub
    }
}
