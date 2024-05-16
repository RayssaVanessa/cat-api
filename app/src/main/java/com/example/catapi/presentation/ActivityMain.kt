package com.example.catapi.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.catapi.R

class ActivityMain : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null) {
            supportFragmentManager.commit{
              setReorderingAllowed(true)
                add(R.id.apiCatContainer, CatApiFragment())
          }
        }
    }
}