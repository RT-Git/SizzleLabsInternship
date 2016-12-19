package com.wordpress.obliviouscode.phone2beacon

import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter

import java.util.Arrays

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = Intent(this@MainActivity, iBeacon::class.java)
        val j = Intent(this@MainActivity, Kontakt::class.java)
        setContentView(R.layout.activity_main)

        val b = findViewById(R.id.start) as Button
        b.setOnClickListener {
            startService(i)
        }

        val c = findViewById(R.id.kontaktStart) as Button
        c.setOnClickListener { startService(j) }

    }
}
