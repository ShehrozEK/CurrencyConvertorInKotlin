package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap


open class BaseActivity : AppCompatActivity(),AppContext {
    override fun loadFragment(layoutId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(layoutId, fragment)
        transaction.commit()
    }
    override fun showToast(text: String,duration: Int) {
        Toast.makeText(this,text, duration).show()
    }
    override fun getResponseFromNetwork(url: URL):String {
        val httpURLConnection = url.openConnection() as HttpURLConnection
        var inputStream = httpURLConnection.inputStream
        val scanner = Scanner(inputStream)
        scanner.useDelimiter("\\A")
        while(scanner.hasNext()){
            return scanner.next()
        }
        return ""
    }
    override fun getContext(): Context {
        return applicationContext
    }
}