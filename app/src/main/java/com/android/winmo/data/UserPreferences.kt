package com.android.winmo.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {

    private val appContext = context.applicationContext
    private val dataStore = appContext.createDataStore("app_prefs")



    private suspend fun setValue(k: Preferences.Key<String>, v: String) {
        dataStore.edit { pref ->
            pref[k] = v
        }
    }

    private fun getValue(k: Preferences.Key<String>): Flow<String?> {
        //returns value to be observed as live data
        return dataStore.data.map { prefs ->
            prefs[k]
        }
    }

    companion object {
        private val EMAIL = stringPreferencesKey("google_email")
        private val USERNAME = stringPreferencesKey("google_username")
    }

}