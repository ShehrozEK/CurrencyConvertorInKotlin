package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import androidx.fragment.app.Fragment
import java.net.URL

interface AppContext {
    fun loadFragment(layoutId : Int, fragment : Fragment)
    fun showToast(text : String,duration:Int)
    fun getContext(): Context
    fun getResponseFromNetwork(url: URL):String
    fun getCountriesHashMap(): HashMap<String, Country>
}