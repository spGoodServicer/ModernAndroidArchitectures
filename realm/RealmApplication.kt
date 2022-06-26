package com.nereus.craftbeer.realm

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.nereus.craftbeer.model.BeerPouring
import com.nereus.craftbeer.util.TLogger
import com.seikoinstruments.sdk.thermalprinter.PrinterException
import com.seikoinstruments.sdk.thermalprinter.PrinterManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.floor

@HiltAndroidApp
class RealmApplication : Application(), Configuration.Provider {
    private var mPrinterManager: PrinterManager? = null

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    companion object {
        lateinit var instance: RealmApplication private set
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
//        Realm.init(this)
//        val config = RealmConfiguration.Builder().build()
//        Realm.setDefaultConfiguration(config)
        Timber.plant(Timber.DebugTree())
//        delayedInit()
        instance = this

        //initialize local logger
        TLogger.Init(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY_MM_dd_HH_mm_ss")).toString() + "_log.txt")
        TLogger.writeln("RealmApplication::onCreate start")
        //TLogger.writeln(this.javaClass.name)
    }

    private fun delayedInit() {
        applicationScope.launch {
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    }


    fun setPrinterManager(manager: PrinterManager) {
        mPrinterManager = manager
    }

    fun getPrinterManager(): PrinterManager? {
        return mPrinterManager
    }

    fun getPrinterStatus(): Boolean {
        if (mPrinterManager == null) {
            return false
        }
        val buf = IntArray(1)
        try {
            mPrinterManager!!.getStatus(buf)
        } catch (e: PrinterException) {
            Timber.e(e)
            return false
        }
        return true
    }
}