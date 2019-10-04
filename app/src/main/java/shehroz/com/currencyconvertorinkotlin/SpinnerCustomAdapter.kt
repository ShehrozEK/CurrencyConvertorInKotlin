package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import java.io.File

class SpinnerCustomAdapter(context: Context,hashMap: HashMap<String,Country>): BaseAdapter() {
    var context:Context = context
    var countryMap:HashMap<String,Country> = hashMap

    override fun isEmpty(): Boolean {
       return countryMap.isEmpty()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return getCustomView(p0,p2)
    }

    override fun getDropDownView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return getCustomView(p0,p2)
    }

    override fun getCount(): Int {
        return countryMap.size
    }

    override fun getItem(p0: Int): Any {
        return countryMap.entries.toTypedArray()[p0].key
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    private fun getCustomView(pos:Int,viewGroup: ViewGroup?):View{
        val view = LayoutInflater.from(context).inflate(R.layout.custom_spinner,viewGroup,false) as View
        view.setPadding(5,10,5,10)
        val key = countryMap.keys.toTypedArray()[pos]
        val countryName = view.findViewById<TextView>(R.id.countryName)
        countryName.setPadding(10,0,0,0)
        countryName.setTextColor(Color.BLACK)
        countryName.text = countryMap.get(key)?.countryName
        val countryFlag = view.findViewById<ImageView>(R.id.countryFlag)
        val uri = countryMap.get(key)!!.countryFlag.split("drawable/")
        val imageResourceId : Int = context.resources.getIdentifier(uri[1].split(".")[0],"drawable", context.packageName)
        countryFlag.setImageResource(imageResourceId)
        val countryCurrency = view.findViewById<TextView>(R.id.countryCurrency)
        countryCurrency.setTextColor(Color.DKGRAY)
        countryCurrency.text = countryMap.get(key)?.countryCurrency
        return view
    }
}