package com.redprisma.lplsuperponyup.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.redprisma.lplsuperponyup.ui.util.AssetPathState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

private const val MOCK_PREFS = "mock_prefs"
private const val MOCK_ASSET_KEY = "mock_asset_path"
private const val DEFAULT_MOCK = "mock/comment-json.json"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = MOCK_PREFS
)

@Singleton
class AssetPathProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AssetPathState {
    private val store = context.dataStore

    private val key = stringPreferencesKey(MOCK_ASSET_KEY)
    private val _default = DEFAULT_MOCK

    override val pathFlow: StateFlow<String> = store.data.map {
        it[key] ?: _default
    }.stateIn(
        scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
        started = SharingStarted.Eagerly,
        initialValue = _default
    )

    override suspend fun setPath(newPath: String) {
        store.edit { it[key] = newPath }
    }
}