package com.wordpress.obliviouscode.beaconscanner

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.test.mock.MockPackageManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener
import com.kontakt.sdk.android.ble.manager.ProximityManager
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener
import com.kontakt.sdk.android.common.KontaktSDK
import com.kontakt.sdk.android.common.profile.IBeaconDevice
import com.kontakt.sdk.android.common.profile.IBeaconRegion
import com.kontakt.sdk.android.common.profile.IEddystoneDevice
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var proximityManager: ProximityManagerContract? = null
    private val MY_PERMISSION2: Int = 0

    private var ed: ListView? = null
    private var ib: ListView? = null

    internal var edlistItems = ArrayList<String>()
    internal var iblistItems = ArrayList<String>()

    //    val adapter1 : ArrayAdapter<>
//    internal abstract var adapter2: ArrayAdapter<String>
    internal var mPermission = Manifest.permission.ACCESS_FINE_LOCATION
    internal var mPermission2 = Manifest.permission.ACCESS_COARSE_LOCATION


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(mPermission, mPermission2), REQUEST_CODE_PERMISSION)

            }
        } catch (e: Exception) {
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
        }


        KontaktSDK.initialize(this)



        ed = findViewById(R.id.eddyList) as ListView
        ib = findViewById(R.id.iList) as ListView



        proximityManager = ProximityManager(this)
        proximityManager!!.setIBeaconListener(createIBeaconListener())
        proximityManager!!.setEddystoneListener(createEddystoneListener())


    }


    override fun onStart() {
        super.onStart()
        startScanning()
    }

    override fun onStop() {
        proximityManager!!.stopScanning()
        super.onStop()
    }

    override fun onDestroy() {
        proximityManager!!.disconnect()
        proximityManager = null
        super.onDestroy()
    }

    private fun startScanning() {
        proximityManager!!.connect {
            proximityManager!!.startScanning()

            Toast.makeText(applicationContext, "Scanning", Toast.LENGTH_LONG).show()
        }
    }

    private fun createIBeaconListener(): IBeaconListener {
        return object : SimpleIBeaconListener() {
            override fun onIBeaconDiscovered(ibeacon: IBeaconDevice?, region: IBeaconRegion?) {
                Log.i("Sample", "IBeacon discovered: " + ibeacon!!.toString())
                iblistItems.add(ibeacon.toString())

                val adapter2 = ArrayAdapter(applicationContext,
                        android.R.layout.simple_list_item_1,
                        iblistItems)

                ib!!.adapter = adapter2

                adapter2.notifyDataSetChanged()
            }
        }
    }

    private fun createEddystoneListener(): EddystoneListener {
        return object : SimpleEddystoneListener() {
            override fun onEddystoneDiscovered(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {
                Log.i("Sample", "Eddystone discovered: " + eddystone!!.toString())
                edlistItems.add(eddystone.toString())

                val adapter1 = ArrayAdapter(applicationContext,
                        android.R.layout.simple_list_item_1,
                        edlistItems)

                ed!!.adapter = adapter1

                adapter1.notifyDataSetChanged()
            }
        }
    }

    companion object {

        private val REQUEST_CODE_PERMISSION = 2
    }
}