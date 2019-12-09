package com.tugas.kopikuhkopi.Admin.MenuAdmin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.tugas.kopikuhkopi.R
import kotlinx.android.synthetic.main.activity_admin_add_menu.*
import kotlinx.android.synthetic.main.content_admin_add_menu.*
import java.io.IOException

class AddMenuActivity : AppCompatActivity(), View.OnClickListener {
    private var ImagePath: Uri? = null
    lateinit var databaseRef: DatabaseReference



    override fun onClick(v: View?) {
        if (v == btn_choose_image) {
            ChooseFile()
        } else if (v == fab_done) {
            UploadFile()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_menu)


        //init referensi
        databaseRef = FirebaseDatabase.getInstance().getReference("Menu")

        //button command
        btn_choose_image.setOnClickListener {
            ChooseFile()
        }

        fab_done.setOnClickListener {
            UploadFile()
        }


    }

    private fun ChooseFile() {
        val intentImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentImg, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            ImagePath = data.data;
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, ImagePath)
                image!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun UploadFile() {
        if (ImagePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val storageRef = FirebaseStorage.getInstance().getReference("Menu")

            storageRef.putFile(ImagePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "File Uploaded", Toast.LENGTH_SHORT).show()
                    storageRef.downloadUrl.addOnSuccessListener {
                        val name = eta_name.text.toString()
                        val price = eta_price.text.toString()
                        val image = it.toString()
                        val Id = databaseRef.push().key.toString()
                        val menu = Menu(Id, name, price, image)

                        databaseRef.child(Id).setValue(menu).addOnCompleteListener {
                            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
                            eta_name.setText("")
                            eta_price.setText("")
                        }
                        finish()
                    }
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded" + progress.toInt() + "%...")
                }
        }
    }
}

//    private var imgPath: Uri? = null
//
//    override fun onClick(v: View?) {
//        when(v){
//            fab_done -> {
////                val name = eta_name.text.toString()
////                val price = eta_price.text.toString()
//                val storageRef = FirebaseStorage
//                    .getInstance()
//                    .getReference("menus")
//                val databaseRef = FirebaseDatabase
//                    .getInstance()
//                    .getReference("menus")
//
//                storageRef.putFile(imgPath!!)
//                    .addOnSuccessListener {
//                        storageRef.downloadUrl.addOnSuccessListener {
//                            val name = eta_name.text.toString()
//                            val price = eta_price.text.toString()
//                            val menuId = databaseRef.push().key.toString()
//                            val image = it.toString()
//                            val menu = Menu(menuId, name, price, image)
//
//                            databaseRef.child(menuId).setValue(menu).addOnCompleteListener{
//                                Toast.makeText(this, "Upload Successfully", Toast.LENGTH_SHORT).show()
//                                eta_name.setText("")
//                                eta_price.setText("")
//                            }
////                            databaseRef.child("image").setValue(it.toString())
////                            databaseRef.child("name").setValue(name)
////                            databaseRef.child("price").setValue(price)
////
////                            Toast.makeText(this, "Add Menu Successfully", Toast.LENGTH_SHORT).show()
//
//                            finish()
//                        }
//                    }
//                    .addOnFailureListener{
//                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
////                        println("Info File : ${it.message}")
//                    }
//            }
//
//            btn_choose_image -> {
//                val iImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                startActivityForResult(iImg, 0)
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_admin_add_menu)
//
//
//        fab_done.setOnClickListener(this)
//        btn_choose_image.setOnClickListener(this)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            imgPath = data?.data
//        }
//    }
//}
