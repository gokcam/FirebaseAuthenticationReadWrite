package com.gok.firebaseyemektarifleri

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val guncelKullanici = auth.currentUser
        if (guncelKullanici != null)
        {
            val intent = Intent(this,TariflerimActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    fun girisYap(view: View)
    {
        auth.signInWithEmailAndPassword(emailEditText.text.toString(),passwordEditText.text.toString()).addOnCompleteListener {
            if (it.isSuccessful)
            {
                val guncelKullanici = auth.currentUser?.email.toString()
                Toast.makeText(this ,"Hoşgeldin: ${guncelKullanici}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,TariflerimActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener {
            Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }

    fun kayıtOl(view: View)
    {

        var email = emailEditText.text.toString()
        var sifre = passwordEditText.text.toString()

        auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener {
            if (it.isSuccessful)
            {
                val intent = Intent(applicationContext,TariflerimActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
}