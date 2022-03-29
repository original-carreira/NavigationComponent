package com.example.navigationcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navigationcomponent.databinding.ActivityMainBinding
import com.example.navigationcomponent.iu.arquivos.ArquivoFragment
import com.example.navigationcomponent.iu.start.StartFragment

class MainActivity : AppCompatActivity(){
   private lateinit var binding: ActivityMainBinding
   private lateinit var appBarConfiguration: AppBarConfiguration
   private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarJetpack)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.Container_fragmento) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.Container_fragmento)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}