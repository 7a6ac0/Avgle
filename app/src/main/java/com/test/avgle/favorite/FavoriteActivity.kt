package com.test.avgle.favorite

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.test.avgle.R
import com.test.avgle.main.MainActivity
import com.test.avgle.util.replaceFragmentInActivity
import com.test.avgle.util.setupActionBar

/**
 * Created by 7a6ac0 on 2018/1/23.
 */
class FavoriteActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var favoritePresenter: FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_activity)

        // Set up the toolbar.
        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        // Set up the navigation drawer.
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }

        setupDrawerContent(findViewById(R.id.nav_view))

        val favoriteFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as FavoriteFragment? ?: FavoriteFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        favoritePresenter = FavoritePresenter(favoriteFragment)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_category -> {
                    val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            // Close the navigation drawer when an item is selected.
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemId = item.itemId
        if (itemId == android.R.id.home) {
            // Open the navigation drawer when the home icon is selected from the toolbar.
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}