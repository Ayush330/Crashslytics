package com.example.crashslytics

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crashslytics.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding


    override fun onStart() {
        super.onStart()
        CheckForConnectivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.progressBar.visibility = View.VISIBLE


        /**calling the ktor client function**/

        getDataOnToScreen()

        /**Refreshing the screen on swiping**/

        binding.simpleSwipeRefreshLayout.setOnRefreshListener {
            Log.i("Ayush","Refreshed")
            getDataOnToScreen()
            binding.simpleSwipeRefreshLayout.isRefreshing=false
        }

    }

    private fun getDataOnToScreen() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = KTORClient(this@MainActivity).getData()
            withContext(Dispatchers.Main){
                binding.dataAdapter.apply {
                    adapter = Adapter(response)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    fun CheckForConnectivity()
    {
        val manager = this@MainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true
        
        if(!isConnected)
        {
            Snackbar.make(binding.progressBar,"No network connection available",Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.MAGENTA)
                .setAction("Settings",goToSettings(this@MainActivity))
                .setActionTextColor(Color.GREEN)
                .show()
        }
    }

    class goToSettings(private val context:Context) : View.OnClickListener{


        override fun onClick(v: View) {
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            context.startActivity(intent)
        }
    }


}