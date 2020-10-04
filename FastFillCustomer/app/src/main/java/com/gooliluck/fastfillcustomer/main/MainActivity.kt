package com.gooliluck.fastfillcustomer.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gooliluck.fastfillcustomer.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel : MainViewModel
    private var listener = View.OnClickListener { v ->
        v?.let {
            Log.e("jptest","show id ${it.id}")

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)
        fab.setOnClickListener {
            mainViewModel.showFabMenu.value?.let { show ->
                if(!show) {
                    mainViewModel.setFabMenuShow(show = true)
                } else {
                    mainViewModel.setFabMenuShow(show = false)
                }
            }
        }


        add_new_user.setOnClickListener {
            mainViewModel.setFabMenuShow(show = false)
            mainViewModel.setFabShowing(false)
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.addNewUserFragment)
        }

        add_new_order.setOnClickListener {
            mainViewModel.setFabMenuShow(show = false)
            mainViewModel.setFabShowing(false)
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.addOrderFragment)
        }

        mainViewModel.navControl.observe(this, Observer {
            it?.let { jpNavControl ->
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(jpNavControl.id)
                mainViewModel.clearNavControl()
            }
        })

        mainViewModel.showFabMenu.observe(this, Observer {
            when(it){
                true -> {
                    add_new_user.animate().translationY(-resources.getDimension(R.dimen.standard_55)).alpha(1.0f).scaleX(1.0f).scaleY(1.0f)
                    add_new_order.animate().translationY(-resources.getDimension(R.dimen.standard_105)).alpha(1.0f).scaleX(1.0f).scaleY(1.0f)

                }
                false -> {
                    add_new_user.animate().translationY(0.0f).alpha(0.0f).scaleX(0.0f).scaleY(0.0f)
                    add_new_order.animate().translationY(0.0f).alpha(0.0f).scaleX(0.0f).scaleY(0.0f)
                }
            }
        })

        mainViewModel.showFab.observe(this, Observer {
            when(it){
                true -> {
                    showFabFloat()
                }
                false -> {
                    closeFabFloat()
                }
            }
        })
    }
    private fun showFabFloat(){
        fab.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f)
    }
    private fun closeFabFloat(){
        fab.animate().alpha(0.0f).scaleY(0.0f).scaleX(0.0f)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
