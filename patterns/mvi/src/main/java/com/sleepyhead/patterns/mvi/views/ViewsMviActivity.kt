package com.sleepyhead.patterns.mvi.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sleepyhead.patterns.mvi.databinding.ActivityViewsMviBinding

class ViewsMviActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewsMviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewsMviBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
