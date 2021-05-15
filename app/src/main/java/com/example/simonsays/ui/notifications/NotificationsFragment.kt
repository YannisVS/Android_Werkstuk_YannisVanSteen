package com.example.simonsays.ui.notifications

import android.content.ContentValues
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simonsays.R
import com.example.simonsays.ui.home.HomeViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

var MusicPlaying = false



class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        var  Leaderboard = root.findViewById<TextView>(R.id.txt_profileScore)

        // Write a message to the database
        val database = FirebaseDatabase.getInstance("https://simonsays-402e3-default-rtdb.europe-west1.firebasedatabase.app/")
        var myRef = database.getReference("Pos1")
var FoundScore = false;
        for (i in 1..5) {
            myRef = database.getReference("Pos" + i)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val value = dataSnapshot!!.value as HashMap<String,Any>

                    var CurrentUser =  value["name"] as String
                    if (!FoundScore){
                        if (CurrentUser == "YVS"){
                            FoundScore = true
                            println("User Leaderboard position: $CurrentUser")
                            Leaderboard.text = ("Position: " + value["position"].toString() + "\n" + "Name: " + value["name"].toString() + "\n" + "Score: " + value["score"].toString())
                        }else if(CurrentUser != "YVS"){
                            Leaderboard.text = ("You don't have a leaderboard score yet")
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
        }


        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }
}

