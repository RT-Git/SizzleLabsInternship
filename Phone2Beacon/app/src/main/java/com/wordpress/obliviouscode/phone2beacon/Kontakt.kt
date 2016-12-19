package com.wordpress.obliviouscode.phone2beacon

import android.app.IntentService
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.Intent
import android.util.Log
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter
import java.util.*

/**
 * Created by Ravi on 19-12-2016.
 */
class Kontakt : IntentService("Kontakt") {
    internal val TAG = "KontaktBeacon"

    public override fun onHandleIntent(intent: Intent?) {

        val beacon = Beacon.Builder()
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6") // UUID for beacon
                .setId2("1") // Major for beacon
                .setId3("5") // Minor for beacon
                .setManufacturer(0x01FD) // Kontakt
                .setTxPower(-56) // Power in dB
                .setDataFields(Arrays.asList(*arrayOf(0L))) // Remove this for beacon layouts without d: fields
                .build()

        val beaconParser = BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")

        val beaconTransmitter = BeaconTransmitter(applicationContext, beaconParser)
        beaconTransmitter.startAdvertising(beacon, object : AdvertiseCallback() {

            override fun onStartFailure(errorCode: Int) {
                Log.e(TAG, "Advertisement start failed with code: " + errorCode)
            }

            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                Log.i(TAG, "Advertisement start succeeded.")
            }
        })


    }
}