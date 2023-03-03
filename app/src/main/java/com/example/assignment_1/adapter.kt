package com.example.assignment_1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class adapter(var context: Context, var data: ArrayList<data>) :
    RecyclerView.Adapter<adapter.viewHolder>() {
    var db = Firebase.firestore

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameContact = itemView.findViewById<TextView>(R.id.nameContact)
        var numberContact = itemView.findViewById<TextView>(R.id.numberContact)
        var addressContact = itemView.findViewById<TextView>(R.id.addressContact)
        var delete = itemView.findViewById<Button>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.show, parent, false)
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.nameContact.text = data[position].name
        holder.addressContact.text = data[position].address
        holder.numberContact.text = data[position].number
        holder.delete.setOnClickListener {
            db.collection("contacts")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        var number = document.data.get(key = "number") as String
                        if (number == data[position].number) {
                            var id = document.id.toString()
                            db.collection("contacts")
                                .document(id).delete()
                            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}