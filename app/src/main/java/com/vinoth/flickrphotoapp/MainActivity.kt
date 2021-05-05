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
                    searchView?.setQuery(query, false)
                    searchView?.clearFocus()
                    Toast.makeText(applicationContext, "Fetching ${query}", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView?.setQuery("Nature", true)
        Toast.makeText(applicationContext, "Fetching Nature", Toast.LENGTH_LONG).show()
        searchView?.clearFocus()

        photoViewModel.getLiveData()?.observe(this, { reponse ->
            if (adapter == null) {
                adapter = PhotoAdapter(this, reponse?.toMutableList() ?: mutableListOf())
                recyclerview?.adapter = adapter
                adapter?.notifyDataSetChanged()
            } else {
                adapter?.addItem(reponse?.toMutableList() ?: mutableListOf())
            }
        })
    }
}