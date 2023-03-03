package com.example.assignment_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val add = findViewById<Button>(R.id.addContact)
        val showListContact = findViewById<Button>(R.id.showContact)
        add.setOnClickListener {
            val name = findViewById<EditText>(R.id.name).text.toString()
            val number = findViewById<EditText>(R.id.Number).text.toString()
            val address = findViewById<EditText>(R.id.address).text.toString()
            val contact = hashMapOf(
                "name" to name,
                "number" to number,
                "address" to address
            )
            db.collection("contacts").add(contact)
                .addOnSuccessListener {
                    Toast.makeText(this, "Added successfully" , Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Added failed" , Toast.LENGTH_LONG).show()
                }
        }
        showListContact.setOnClickListener {
            var i = Intent(this  , Show_Contact :: class.java)
            startActivity(i)
        }
    }
}