package com.vinoth.flickrphotoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinoth.viewmodel.PhotoViewModel

class MainActivity : AppCompatActivity() {

    private var photoViewModel: PhotoViewModel = NewInstanceFactory().create(PhotoViewModel::class.java)
    private var recyclerview: RecyclerView? = null
    private var adapter: PhotoAdapter? = null
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerview)
        recyclerview?.setHasFixedSize(false)
        recyclerview?.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.searchView)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    photoViewModel.loadPhoto(query)
                }
                Toast.makeText(applicationContext, "Ferching new search", Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        photoViewModel.getLiveData()?.observe(this, { reponse ->
            adapter = PhotoAdapter(this, reponse?.toMutableList() ?: mutableListOf())
            recyclerview?.adapter = adapter
            adapter?.notifyDataSetChanged()
        })
    }
}