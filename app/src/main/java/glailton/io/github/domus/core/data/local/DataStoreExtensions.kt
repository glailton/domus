package glailton.io.github.domus.core.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(
    name = "PreferenceDataStore"
)