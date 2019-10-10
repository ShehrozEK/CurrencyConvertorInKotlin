package shehroz.com.currencyconvertorinkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onAttachFragment(fragment: Fragment) {
        if(fragment is PrimaryFragment){
            fragment.appContext = this
        }
    }
}
