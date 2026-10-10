package com.example.hvacapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hvacapp.ui.theme.HvacAppTheme

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.hvacapp.databinding.ActivityMainBinding // Αν χρησιμοποιείς ViewBinding

/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HvacAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HvacAppTheme {
        Greeting("Android")
    }
}

 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {

                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)


        binding.navView.setNavigationItemSelectedListener { menuItem ->

            val selectedFragment = when (menuItem.itemId) {
                R.id.nav_pelatologio -> PelatologioFragment()

                R.id.nav_istoriko -> IstorikoFragment()
                R.id.nav_peratosi -> PeratosiFragment()
                R.id.nav_prosopiko -> ProsopikoFragment()
                //R.id.nav_synergates -> SynergatesFragment()
                else -> PelatologioFragment()
            }


            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()


            supportActionBar?.title = menuItem.title


            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


       /* binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_istoriko -> supportActionBar?.title = "Ιστορικό"
                R.id.nav_pelatologio -> supportActionBar?.title = "Πελατολόγιο"
                R.id.nav_peratosi -> supportActionBar?.title = "Περάτωση"
                R.id.nav_prosopiko -> supportActionBar?.title = "Προσωπικό"
                R.id.nav_synergates -> supportActionBar?.title = "Συνεργάτες"
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        } */
    }
}