package com.example.mv_ted.ui.view

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

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_main, MainFragment.newInstance())
                .commitNow()
        }
        initView();
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
                    Toast.makeText(this, "Drawer", Toast.LENGTH_SHORT).show()
                    return@setNavigationItemSelectedListener true
                }
            }
            return@setNavigationItemSelectedListener false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val search = menu?.findItem(R.id.search_menu)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.setOnQueryTextFocusChangeListener(object : SearchView.OnQueryTextListener,
            View.OnFocusChangeListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(baseContext, p0, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Toast.makeText(baseContext, p0, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onFocusChange(p0: View?, p1: Boolean) {
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_frame_menu -> {
                Toast.makeText(baseContext, "Main Frame", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}