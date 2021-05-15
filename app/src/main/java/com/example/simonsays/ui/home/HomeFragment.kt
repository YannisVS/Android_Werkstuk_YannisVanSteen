package com.example.simonsays.ui.home

import android.content.ContentValues
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simonsays.R
import kotlinx.coroutines.delay
import java.lang.reflect.Array
import kotlin.random.Random
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import kotlin.math.log

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var AnsweredArray = emptyArray<Any>()
    var SimonSaysCommands = emptyArray<Any>()
    var GameStarted = false
    var SimonSaysInProgress = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        val Easy = root.findViewById<RadioButton>(R.id.rad_easy)
        val Medium = root.findViewById<RadioButton>(R.id.rad_medium)
        val Hard = root.findViewById<RadioButton>(R.id.rad_hard)
        val buttonred = root.findViewById<Button>(R.id.btn_Red)
        val buttongreen = root.findViewById<Button>(R.id.btn_Green)
        val buttonblue = root.findViewById<Button>(R.id.btn_Blue)
        val buttonpurple = root.findViewById<Button>(R.id.btn_Purple)
        val buttonstart = root.findViewById<Button>(R.id.btn_Start)
        val Score = root.findViewById<TextView>(R.id.txt_Score)
        var SelectedDifficulty = 1000
        var Scorevalue = 0


        buttonred.setOnClickListener {
            if (!SimonSaysInProgress) {
            if (GameStarted) {
                AnsweredArray += 0
                val Currentlength = AnsweredArray.size
                val isEqual = AnsweredArray contentEquals SimonSaysCommands
                if (isEqual) {
                    SimonSaysCommands += Random.nextInt(0, 4)
                    println(SimonSaysCommands.size)
                    GameLoop(SimonSaysCommands, buttonred, buttongreen, buttonblue, buttonpurple, Score, SelectedDifficulty)
                } else if (AnsweredArray[Currentlength - 1] != SimonSaysCommands[Currentlength - 1]) {
                    Toast.makeText(activity, "Game Over", Toast.LENGTH_SHORT).show()
                    Wincheck(SimonSaysCommands.size)
                    ResetGame(Score)
                }
            } else if (!GameStarted) {
                Toast.makeText(activity, "You need to start the game to begin", Toast.LENGTH_SHORT).show()
            }
        }
        else if (SimonSaysInProgress) {
            Toast.makeText(activity, "You must wait until all is shown", Toast.LENGTH_SHORT).show()
        }
    }

        buttongreen.setOnClickListener {
        if (!SimonSaysInProgress) {
            if (GameStarted) {
                AnsweredArray += 1
                val Currentlength = AnsweredArray.size
                val isEqual = AnsweredArray contentEquals SimonSaysCommands
                if (isEqual) {
                    SimonSaysCommands += Random.nextInt(0, 4)
                    println(SimonSaysCommands.size)
                    GameLoop(SimonSaysCommands, buttonred, buttongreen, buttonblue, buttonpurple, Score, SelectedDifficulty)
                } else if (AnsweredArray[Currentlength - 1] != SimonSaysCommands[Currentlength - 1]) {
                    Toast.makeText(activity, "Game Over", Toast.LENGTH_SHORT).show()
                    Wincheck(SimonSaysCommands.size)
                    ResetGame(Score)
                }
            } else if (!GameStarted) {
                Toast.makeText(activity, "You need to start the game to begin", Toast.LENGTH_SHORT).show()
            }
        }
        else if (SimonSaysInProgress) {
            Toast.makeText(activity, "You must wait until all is shown", Toast.LENGTH_SHORT).show()
        }
    }

        buttonblue.setOnClickListener {
        if (!SimonSaysInProgress) {
            if (GameStarted) {
                AnsweredArray += 2
                val Currentlength = AnsweredArray.size
                val isEqual = AnsweredArray contentEquals SimonSaysCommands
                if (isEqual) {
                    SimonSaysCommands += Random.nextInt(0, 4)
                    println(SimonSaysCommands.size)
                    GameLoop(SimonSaysCommands, buttonred, buttongreen, buttonblue, buttonpurple, Score, SelectedDifficulty)
                } else if (AnsweredArray[Currentlength - 1] != SimonSaysCommands[Currentlength - 1]) {
                    Toast.makeText(activity, "Game Over", Toast.LENGTH_SHORT).show()
                    Wincheck(SimonSaysCommands.size)
                    ResetGame(Score)
                }
            } else if (!GameStarted) {
                Toast.makeText(activity, "You need to start the game to begin", Toast.LENGTH_SHORT).show()
            }
        }
         else if (SimonSaysInProgress) {
             Toast.makeText(activity, "You must wait until all is shown", Toast.LENGTH_SHORT).show()
         }

        }
        buttonpurple.setOnClickListener{
        if (!SimonSaysInProgress) {
            if (GameStarted){    AnsweredArray += 3
                val Currentlength = AnsweredArray.size
                val isEqual = AnsweredArray contentEquals SimonSaysCommands
                if (isEqual){
                    SimonSaysCommands += Random.nextInt(0, 4)
                    println(SimonSaysCommands.size)
                    GameLoop(SimonSaysCommands, buttonred, buttongreen, buttonblue, buttonpurple, Score, SelectedDifficulty)
                }
                else if (AnsweredArray[Currentlength- 1] != SimonSaysCommands[Currentlength- 1]){
                    Toast.makeText(activity, "Game Over", Toast.LENGTH_SHORT).show()
                    Wincheck(SimonSaysCommands.size)
                    ResetGame(Score)
                }
            }
            else if (!GameStarted){
                Toast.makeText(activity, "You need to start the game to begin", Toast.LENGTH_SHORT).show()
            }
        }
        else if (SimonSaysInProgress) {
            Toast.makeText(activity, "You must wait until all is shown", Toast.LENGTH_SHORT).show()
        }
    }

        buttonstart.setOnClickListener{
            if (Easy.isChecked){
                println(Easy.id)
                if (!GameStarted){
                    SelectedDifficulty = 1000
                    GameStarted = true
                    SimonSaysCommands += Random.nextInt(0, 4)
                    println(SimonSaysCommands.size)
                    GameLoop(SimonSaysCommands, buttonred, buttongreen, buttonblue, buttonpurple, Score, SelectedDifficulty)
                }
                else if (GameStarted){
                    Toast.makeText(activity, "Game is already in progress", Toast.LENGTH_SHORT).show()
                }
            }
            else if (Medium.isChecked){
                println(Medium.id)
                if (!GameStarted){
                    SelectedDifficulty = 500
                    GameStarted = true
                    SimonSaysCommands += Random.nextInt(0, 4)
                    println(SimonSaysCommands.size)
                    GameLoop(SimonSaysCommands, buttonred, buttongreen, buttonblue, buttonpurple, Score, SelectedDifficulty)
                }
                else if (GameStarted){
                    Toast.makeText(activity, "Game is already in progress", Toast.LENGTH_SHORT).show()
                }
            }
            else if (Hard.isChecked){
                println(Hard.id)
                if (!GameStarted){
                    SelectedDifficulty = 300
                    GameStarted = true
                    SimonSaysCommands += Random.nextInt(0, 4)
                    println(SimonSaysCommands.size)
                    GameLoop(SimonSaysCommands, buttonred, buttongreen, buttonblue, buttonpurple, Score, SelectedDifficulty)
                }
                else if (GameStarted){
                    Toast.makeText(activity, "Game is already in progress", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(activity, "Select a difficulty first", Toast.LENGTH_SHORT).show()
            }
        }
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }
    private fun ResetGame(UserScore: TextView){
        AnsweredArray = emptyArray<Any>()
        SimonSaysCommands = emptyArray<Any>()
        UserScore.text = (0).toString();
        GameStarted = false
    }

    private fun Wincheck(UserScore: Int){

        // Write a message to the database
        val database = FirebaseDatabase.getInstance("https://simonsays-402e3-default-rtdb.europe-west1.firebasedatabase.app/")
        var myRef = database.getReference("Pos1")
        var WinnerBool = false
        var CurrentscorePosition = 0
        for (i in 1..5) {
            myRef = database.getReference("Pos" + i)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val value = dataSnapshot!!.value as HashMap<String,Any>

                    if (value["score"] is String){
                        CurrentscorePosition = (value["score"] as String).toInt()
                    } else if(value["score"] is Long){
                        CurrentscorePosition = (value["score"] as Long).toInt()
                    }

                    if (!WinnerBool){
                        println("Leaderboard Score: $CurrentscorePosition UserScore: $UserScore")
                        if (CurrentscorePosition < UserScore){
                            WinnerBool = true
                            val Winnerobject = object {
                                val Name = "YVS"
                                val Position = value["position"]
                                val Score = UserScore - 1
                                }
                            println("current Pos $i")
                            myRef = database.getReference("Pos" + i)
                            myRef.setValue(Winnerobject);
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
        }


    }

    private fun GameLoop(Command: kotlin.Array<Any>, red: View, green: View, blue: View, pruple: View, UserScore: TextView, Difficulty: Int) {
        SimonSaysInProgress = true
        AnsweredArray  = emptyArray()
        UserScore.text = (Command.size - 1).toString();
        var teller = 0
        var delay = Difficulty
        var LightShowDelay = delay / 2


        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            SimonSaysInProgress = false
        }, (delay * (Command.size - 1) + LightShowDelay * (Command.size - 1)).toLong())
        Command.forEach {
            teller++
            if (it == 0) {
                handler.postDelayed({
                    red.setBackgroundColor(Color.parseColor("#EE0202"))
                }, (delay * teller).toLong())
                handler.postDelayed({
                    red.setBackgroundColor(Color.parseColor("#980000"))
                }, (delay * teller + LightShowDelay).toLong())
            } else if (it == 1) {
                handler.postDelayed({
                    green.setBackgroundColor(Color.parseColor("#1BDF05"))
                }, (delay * teller).toLong())
                handler.postDelayed({
                    green.setBackgroundColor(Color.parseColor("#109500"))
                }, (delay * teller + LightShowDelay).toLong())
            } else if (it == 2) {
                handler.postDelayed({
                    blue.setBackgroundColor(Color.parseColor("#0039E7"))
                }, (delay * teller).toLong())
                handler.postDelayed({
                    blue.setBackgroundColor(Color.parseColor("#002185"))
                }, (delay * teller + LightShowDelay).toLong())
            } else if (it == 3) {
                handler.postDelayed({
                    pruple.setBackgroundColor(Color.parseColor("#E302CB"))
                }, (delay * teller).toLong())
                handler.postDelayed({
                    pruple.setBackgroundColor(Color.parseColor("#970087"))
                }, (delay * teller + LightShowDelay).toLong())
            }
        }
    }
}




