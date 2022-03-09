package com.example.farmington

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import android.*
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    var drawer:DrawerLayout?=null
    var pref:SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref=getSharedPreferences("pref",Context.MODE_PRIVATE)
        var str=pref!!.getString("name","")


        if(!isNetworkAvailable(this))
        {

            var alertDialog= androidx.appcompat.app.AlertDialog.Builder(this)
            alertDialog.setTitle("No Internet !!!")
            alertDialog.setMessage("Please Turn On Wifi or Mobile Data")
            alertDialog.setCancelable(false).setPositiveButton("Okay",
                DialogInterface.OnClickListener { dialog, which ->

                    var dialogIntent=Intent(Settings.ACTION_SETTINGS)
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this.startActivity(dialogIntent)

                }).show()

        }
    }

    public fun isNetworkAvailable(activity: Context):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }




}