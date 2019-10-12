package shehroz.com.currencyconvertorinkotlin

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment

class MainActivity : BaseActivity() {
    lateinit var handler : Handler
    lateinit var runnable : Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler()
        runnable = Runnable(){
            checkNetworkConnectivity()
            handler.postDelayed(runnable,resources.getInteger(R.integer.interval).toLong())
        }
    }
    override fun onResume() {
        super.onResume()
        runnable.run()
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
    override fun onAttachFragment(fragment: Fragment) {
        if(fragment is PrimaryFragment){
            fragment.appContext = this
        }
    }
}
