package com.example.mv_ted.ui.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mv_ted.R
import com.example.mv_ted.databinding.ActivityMainBinding
import com.example.mv_ted.models.data.model.services_and_broadcastReceivers.MainBroadcastReceiver

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val broadcastReceiver = MainBroadcastReceiver()
    private val _binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        if (savedInstanceState == null) {
            navigationToMainFragment()
        }
        initView()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    private fun navigationToMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_main_frame, MainFragment.newInstance())
            .commitNow()
    }

    private fun initView() {
        val toolbar: androidx.appcompat.widget.Toolbar = initToolbar()
        initDrawer(toolbar)
    }

    private fun initToolbar(): androidx.appcompat.widget.Toolbar {
        val toolbar = _binding.appBarMain.toolbar
        setSupportActionBar(toolbar)
        return toolbar
    }

    private fun initDrawer(toolbar: androidx.appcompat.widget.Toolbar) {
        val drawerLayout: DrawerLayout = _binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.closer_drawer_conent_text,
            R.string.open_string_text_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = _binding.navView
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.setting_item_menu -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    Toast.makeText(this, getString(R.string.NavigationFDawerText), Toast.LENGTH_SHORT).show()
                }
            }
           false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchMenu = menu?.findItem(R.id.search_menu)
        val searchView: SearchView = searchMenu?.actionView as SearchView
        searchView.setOnQueryTextFocusChangeListener(object : SearchView.OnQueryTextListener,
            View.OnFocusChangeListener {
            override fun onQueryTextSubmit(searcingText: String?): Boolean {
                Toast.makeText(baseContext, searcingText, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(searcingText: String?): Boolean {
                Toast.makeText(baseContext, searcingText, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onFocusChange(searcingText: View?, onFocus: Boolean) {
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_frame_menu -> {
                navigationToMainFragment()
                Toast.makeText(baseContext, getString(R.string.textOnOptionItemSelectedMainFrame), Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }
}