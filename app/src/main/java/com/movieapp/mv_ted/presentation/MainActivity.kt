package com.movieapp.mv_ted.presentation

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.movieapp.mv_ted.R
import com.movieapp.mv_ted.databinding.ActivityMainBinding
import com.movieapp.mv_ted.presentation.contactsreader.ContactProviderFragment
import com.movieapp.mv_ted.presentation.favorite.LikedMoviesFragment
import com.movieapp.mv_ted.presentation.main.MainFragment
import com.movieapp.mv_ted.utils.broadcastrecievers.MainBroadcastReceiver

class MainActivity : AppCompatActivity() {
    //TODO(Вынести в DI)
    private val broadcastReceiver = MainBroadcastReceiver()
    private val viewBinding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigationFragment(MainFragment.newInstance())
        }
        initView()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.EXTRA_REASON))

    }

    private fun navigationFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_main_frame, fragment)
            .commit()
    }

    private fun initView() {
        val toolbar: MaterialToolbar = initToolbar()
        initDrawer(toolbar)
    }

    private fun initToolbar(): MaterialToolbar {
        val toolbar = viewBinding.appBarMain.toolbar
        setSupportActionBar(toolbar)
        return toolbar
    }

    private fun initDrawer(toolbar: MaterialToolbar) {
        val drawerLayout: DrawerLayout = viewBinding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.closer_drawer_conent_text,
            R.string.open_string_text_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = viewBinding.navView
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorite_item_menu_drawer -> {
                    navigationFragment(LikedMoviesFragment.newInstance())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.main_frame_menu_drawer -> {
                    navigationFragment(MainFragment.newInstance())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.to_contacts_drawer_menu -> {
                    navigationFragment(ContactProviderFragment.newInstance())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    true
                }
            }
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
                navigationFragment(MainFragment.newInstance())
            }
            R.id.favorite_item_menu -> {
                navigationFragment(LikedMoviesFragment.newInstance())
            }
            R.id.to_contacts -> {
                navigationFragment(ContactProviderFragment.newInstance())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }
}