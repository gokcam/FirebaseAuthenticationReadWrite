package com.gok.firebaseyemektarifleri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_tariflerim.*

class TariflerimActivity : AppCompatActivity() {
    private  lateinit var  auth: FirebaseAuth
    private  lateinit var  database : FirebaseFirestore
    private lateinit var recyclerViewAdapter : TariflerAdapter
    var postListesi = ArrayList<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tariflerim)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = TariflerAdapter(postListesi)
        recyclerView.adapter = recyclerViewAdapter
    }

    fun verileriAl() {

        database.collection("Post").orderBy("tarih", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {
                            val documents = snapshot.documents

                            postListesi.clear()

                            for (document in documents) {
                                val kullaniciEmail = document.get("kullaniciemail") as String
                                val kullaniciYorumu = document.get("kullaniciyorum") as String
                                val gorselUrl = document.get("gorselurl") as String

                                val indirilenPost = Post(kullaniciEmail,kullaniciYorumu,gorselUrl)
                                postListesi.add(indirilenPost)
                            }
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mInflater = menuInflater
        mInflater.inflate(R.menu.secenekler_menusu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cikis_yap)
        {
            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if(item.itemId == R.id.foto_paylas)
        {
            val intent = Intent(this,FotoPaylasmaActivity::class.java)
            startActivity(intent)
        }


        return super.onOptionsItemSelected(item)
    }
}