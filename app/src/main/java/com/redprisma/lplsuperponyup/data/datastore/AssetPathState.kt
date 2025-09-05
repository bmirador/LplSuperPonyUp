package com.redprisma.lplsuperponyup.data.datastore

import kotlinx.coroutines.flow.StateFlow

interface AssetPathState {
    val pathFlow: StateFlow<String>
    suspend fun setPath(newPath: String)
}