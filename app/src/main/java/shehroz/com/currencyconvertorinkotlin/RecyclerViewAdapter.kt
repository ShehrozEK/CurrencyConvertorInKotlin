package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class RecyclerViewAdapter(context: Context, resource:Int, model: Model) : ArrayAdapter<Model>(context,resource){
    private var model  = model
    override fun getCount(): Int {
        return model.rates.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent) as View
        val from = view.findViewById<TextView>(R.id.from_currency)
        val to = view.findViewById<TextView>(R.id.to_currency)
        val countryName = view.findViewById<TextView>(R.id.countryName)
        val countryCurrency = view.findViewById<TextView>(R.id.countryCurrency)
        val countryFlag = view.findViewById<ImageView>(R.id.countryFlag)
        model.rates.keys.toList().get(position)
        return super.getView(position, convertView, parent)
    }
}