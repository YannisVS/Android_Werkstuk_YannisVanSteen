package com.example.simonsays

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.simonsays.ui.notifications.MusicPlaying
import com.example.simonsays.ui.notifications.NotificationsFragment
import com.example.simonsays.ui.notifications.NotificationsViewModel

class MainActivity : AppCompatActivity(){

    private lateinit var binding: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val Audioswitch = findViewById<Switch>(R.id.switch_audio)
        var mp: MediaPlayer= MediaPlayer.create(this, R.raw.song)

        Audioswitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mp.start()
            } else if (!isChecked) {
                mp.pause()
            }
        }



    }


}

