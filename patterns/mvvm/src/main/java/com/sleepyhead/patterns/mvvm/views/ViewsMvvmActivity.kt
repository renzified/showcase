package com.sleepyhead.patterns.mvvm.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sleepyhead.patterns.mvvm.R
import com.sleepyhead.patterns.mvvm.databinding.ActivityViewsMvvmBinding

class ViewsMvvmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewsMvvmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewsMvvmBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
