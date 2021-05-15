package com.example.simonsays.ui.dashboard

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simonsays.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        var Position = root.findViewById<TextView>(R.id.txt_Number1)
        var idString = "txt_Number"

        val database = FirebaseDatabase.getInstance("https://simonsays-402e3-default-rtdb.europe-west1.firebasedatabase.app/")
        var myRef = database.getReference("Pos1")

        for (i in 5 downTo 1) {
            myRef = database.getReference("Pos" + i)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot!!.value as HashMap<String,Any>
                    Log.d(TAG, "Value is: $value")
                    idString = ("Number$i")
                    Position = root.findViewWithTag<TextView>(idString)
                    Position.text = value["position"].toString() + " - " + value["name"].toString() + " - "  + value["score"].toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
        }

        return root
    }
}