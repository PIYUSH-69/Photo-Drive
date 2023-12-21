package com.example.material.adapters


import android.app.Activity
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.recyclerview.widget.RecyclerView
import com.example.material.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.File


class recycleadapter(val array: ArrayList<photos>): RecyclerView.Adapter<recycleadapter.viewholder>() {


    class viewholder(view:View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.gallery,parent,false)
         return viewholder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        var photo=array.get(position)
        Picasso.get()
            .load(photo.photourl)
//            .resize()
//            .centerCrop()
            .into( holder.itemView.findViewById<ImageView>(R.id.image))


        val download=holder.itemView.findViewById<Button>(R.id.button)
        download.setOnClickListener {
            var uid= FirebaseAuth.getInstance().currentUser?.uid.toString()

//            val database = Firebase.database("https://material-ba9f6-default-rtdb.asia-southeast1.firebasedatabase.app/")
//            val myRef = database.getReference("PHOTO").child(uid).child(photo.path)

            var myRef = com.google.firebase.ktx.Firebase.storage.reference.child(uid).child("IMAGES")
            var filename=photo.path+"puch.jpg"
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(directory, filename)
            Toast.makeText(it.context, photo.path, Toast.LENGTH_SHORT).show()
            myRef.child(photo.path).getFile(file)
                    .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                        Log.e("firebase", " local tem file created  created ")
                    })
                    .addOnFailureListener(
                        OnFailureListener { exception ->
                            Log.e(
                                "firebase ",
                                ";local tem file not created  created $exception"
                            )
                        })
        }
    }
}

