package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap


open class BaseActivity : AppCompatActivity(),AppContext {
    override fun checkNetworkConnectivity() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if(!networkInfo.isConnectedOrConnecting){
            showToast("No Network Connected",Toast.LENGTH_LONG)
        }
        else{
            val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue : Int = process.waitFor()
            if(exitValue==0){
                showToast("No Internet",Toast.LENGTH_LONG)
            }
        }
    }

    override fun notifySecondaryFragment(model:Model,amount:String) {
        val secondaryFragment  = supportFragmentManager.findFragmentById(R.id.secondaryFragment) as SecondaryFragment
        secondaryFragment.populateRecyclerView(getContext(),model,amount)
    }
    override fun loadFragment(layoutId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layoutId, fragment)
        transaction.commit()
    }
    override fun showToast(text: String,duration: Int) {
        Toast.makeText(this,text, duration).show()
    }
    override fun getContext(): Context {
        return applicationContext
    }
}