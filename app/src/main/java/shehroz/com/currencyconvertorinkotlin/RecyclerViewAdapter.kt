package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(context: Context,model: Model,amount:String) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    private var model  = model
    private var context  = context
    private var amount = amount
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false) as View
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return model.rates.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val key = model.rates.keys.toList().sorted().get(position)
        val value = model.rates.get(key) as Double
        val baseArrName = context.resources.getIdentifier(model.base,"array",context.packageName)
        val arrName = context.resources.getIdentifier(key,"array",context.packageName)
        if(baseArrName != arrName) {
            val countries = context.resources.getStringArray(arrName) as Array<String>
            holder.countryName.text = countries[0]
            holder.countryName.setTextColor(Color.DKGRAY)
            holder.countryCurrency.text = key
            val countryFlagRes = context.resources.getIdentifier(countries[1].split("drawable/")[1].split(".")[0], "drawable", context.packageName)
            holder.countryFlag.setImageResource(countryFlagRes)
            val baseCountry = context.resources.getStringArray(baseArrName) as Array<String>
            holder.from.text = amount+" "+baseCountry[2]+" equals to"
            holder.to.text = (amount.toDouble()*value).toFloat().toString()+" "+countries[2]
        }
    }

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view) , View.OnClickListener{
        val from = view.findViewById<TextView>(R.id.from_currency)
        val to = view.findViewById<TextView>(R.id.to_currency)
        val countryName = view.findViewById<TextView>(R.id.countryName)
        val countryCurrency = view.findViewById<TextView>(R.id.countryCurrency)
        val countryFlag = view.findViewById<ImageView>(R.id.countryFlag)

        override fun onClick(v: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}