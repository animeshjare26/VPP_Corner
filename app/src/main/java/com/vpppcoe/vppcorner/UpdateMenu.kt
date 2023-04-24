package com.vpppcoe.vppcorner

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vpppcoe.vppcorner.databinding.ActivityUpdateMenuBinding
import java.io.ByteArrayOutputStream

class UpdateMenu : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateMenuBinding
    private lateinit var db: FirebaseDatabase
    lateinit var imagePath: String
    private lateinit var storageReference: StorageReference
    private var Uri: Uri? = null
    private var url : String? = null
    private lateinit var submitDetails: Button
    private lateinit var submitImage: Button
    private lateinit var itemNumber: EditText
    private lateinit var available: EditText
    private lateinit var updateImage: TextView
    private lateinit var image: ImageView
    private lateinit var name: EditText
    private lateinit var price: EditText
    private lateinit var time: EditText
    private lateinit var remove : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateMenuBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_update_menu)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)


        db = FirebaseDatabase.getInstance()
        submitDetails= findViewById(R.id.submit_details)
        submitImage = findViewById(R.id.submit_image)
        itemNumber = findViewById(R.id.et_item_number)
        available= findViewById(R.id.et_available)
        updateImage = findViewById(R.id.update_image_of_food)
        image = findViewById(R.id.image_view)
        name = findViewById(R.id.name)
        price = findViewById(R.id.price)
        time = findViewById(R.id.time)
        remove = findViewById(R.id.remove_item)


        remove.setOnClickListener {
            if(itemNumber.text.isNotEmpty()){
                val newRef = db.getReference("Food").child("${itemNumber.text}")
                newRef.removeValue()
            }
        }

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
//            image.setImageURI(uri)
            if (uri != null) {
                Uri = uri
                uploadImagetoFB(uri)
            }
            // Handle the returned Uri
        }

        updateImage.setOnClickListener {
//            uploadImage(image)
            getContent.launch("image/*")
        }

        submitImage.setOnClickListener {
//            uploadImagetoFB(Uri!!)
            val newRef = db.getReference("Food").child("${itemNumber.text}")
            val images = newRef.child("image")
            images.setValue(url.toString())
        }

        submitDetails.setOnClickListener {
            if (itemNumber.text.isNotEmpty() && available.text.isNotEmpty() && name.text.isNotEmpty() && price.text.isNotEmpty() && time.text.isNotEmpty()) {
                val newRef = db.getReference("Food").child("${itemNumber.text}")
                val avail = newRef.child("available")
                val names = newRef.child("name")
                val amount = newRef.child("price")
                val times = newRef.child("time")

                avail.setValue(available.text.toString().toBoolean()).addOnCompleteListener {
                    val vib: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vib.vibrate(1000)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }
                names.setValue(name.text.toString())
                amount.setValue(price.text.toString().toInt())
                times.setValue(time.text.toString())
            } else {
                Toast.makeText(this, "Empty Fields now allowed", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadImagetoFB(imageUri: Uri) {
        image.setImageResource(0)
        storageReference = FirebaseStorage.getInstance().reference.child(itemNumber.text.toString())
        val bitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, imageUri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream)
        val reducedImage: ByteArray = byteArrayOutputStream.toByteArray()
        if (itemNumber.text.isNotEmpty()) {
            storageReference.child(itemNumber.text.toString())
                .putBytes(reducedImage)
                .addOnSuccessListener { taskSnapshot ->
                    val task = taskSnapshot.storage.downloadUrl
                    task.addOnCompleteListener { it ->
                        if (it.isSuccessful) {
                            storageReference.downloadUrl.addOnSuccessListener {
                                if(it != null) {
                                    url = it.toString()
                                }
                            }

                        }
                        image.setImageURI(Uri)
                    Toast.makeText(applicationContext,"Updated Successfully",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_SHORT).show()
                }
        }
    }

}