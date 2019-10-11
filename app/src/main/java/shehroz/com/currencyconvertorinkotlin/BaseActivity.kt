package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap


open class BaseActivity : AppCompatActivity(),AppContext {
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