package shehroz.com.currencyconvertorinkotlin

import android.os.Bundle

class MainActivity : BaseActivity() {
    // https://api.exchangerate-api.com/v4/latest/USD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     //   loadFragment(R.id.frameLayout,PrimaryFragment(this))
    }
}
