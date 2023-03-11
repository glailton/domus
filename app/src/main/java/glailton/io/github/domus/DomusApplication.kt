package glailton.io.github.domus

import android.app.Application
import glailton.io.github.domus.core.di.domusModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DomusApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@DomusApplication)
            modules(domusModules)
        }
    }
}