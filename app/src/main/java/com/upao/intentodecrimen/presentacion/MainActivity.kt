package com.upao.intentodecrimen.presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.upao.intentodecrimen.R
import com.upao.intentodecrimen.datos.CrimenRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CrimenRepository.inicializar(this)
    }
}