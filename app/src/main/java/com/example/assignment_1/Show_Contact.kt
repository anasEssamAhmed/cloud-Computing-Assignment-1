package com.example.assignment_1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Show_Contact : AppCompatActivity() {
    var db = Firebase.firestore
    var arrayData = ArrayList<data>()
    lateinit var rec: RecyclerView
    lateinit var progress : ProgressBar
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contact)
        rec = findViewById(R.id.rec)
        rec.layoutManager = LinearLayoutManager(this)
        progress = findViewById(R.id.prgress)
        progress.visibility = View.VISIBLE
        count()
    }

    fun getData() {
        db.collection("contacts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var name = document.data.get(key = "name") as String
                    var number = document.data.get(key = "number") as String
                    var address = document.data.get(key = "address") as String
                    arrayData.add(data(name, number, address))
                    var adapter = adapter(this, arrayData)
                    rec.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w("read Data", "Error getting documents.", exception)
            }
    }
    fun count() {
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                getData()
                progress.visibility = View.GONE
            }
        }
        timer.start()
    }
}