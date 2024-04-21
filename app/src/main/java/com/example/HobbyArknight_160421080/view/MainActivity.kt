package com.example.HobbyArknight_160421080.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.HobbyArknight_160421080.R
import com.example.HobbyArknight_160421080.databinding.ActivityMainBinding
import com.example.HobbyArknight_160421080.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var navController: NavController

    companion object{
        var user: User? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
        bind.bottomNav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, bind.drawerLayout)
        NavigationUI.setupWithNavController(bind.navView, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, bind.drawerLayout)
                || super.onSupportNavigateUp()
    }

    fun DisableNavBar() {
        bind.bottomNav.isVisible = false
        bind.navView.isVisible = false

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        bind.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun EnableNavBar() {
        bind.bottomNav.isVisible = true
        bind.navView.isVisible = true

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        bind.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    }

    override fun onBackPressed() {
        if(user != null)
        {

            Toast.makeText(this, "Logout Terlebih Dahulu", Toast.LENGTH_SHORT).show()
        }
        else
        {
            super.onBackPressed()
        }
    }

}