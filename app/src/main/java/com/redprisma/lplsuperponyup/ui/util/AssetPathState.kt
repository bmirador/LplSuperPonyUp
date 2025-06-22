package com.redprisma.lplsuperponyup.ui.util

import kotlinx.coroutines.flow.StateFlow

interface AssetPathState {
    val pathFlow: StateFlow<String>
    suspend fun setPath(newPath: String)
}