package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SecondaryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var earthView: ImageView
    private lateinit var loading_gif: View
    var adapter: RecyclerViewAdapter? = null
    private val serializableKey : String = "Adapter"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_secondary, container, false) as View
        with(view){
            recyclerView = findViewById(R.id.countries)
            loading_gif  = findViewById(R.id.loading)
            earthView    = findViewById(R.id.earthView)
        }
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        Glide.with(this).asGif().load(R.drawable.earth).into(earthView)
        if(savedInstanceState!=null){
            val recyclerViewAdapter = savedInstanceState.getSerializable(serializableKey) as RecyclerViewAdapter?
            if(recyclerViewAdapter!=null){
                adapter = recyclerViewAdapter
                recyclerView.removeAllViews()
                recyclerView.adapter = adapter
            }
        }
        return view
    }

    fun populateRecyclerView(context: Context,model: Model,amount: String){
        loading_gif.visibility = View.VISIBLE
        adapter = RecyclerViewAdapter(context,model,amount)
        recyclerView.adapter = adapter
        loading_gif.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if(adapter!=null){
            outState.putSerializable(serializableKey,adapter)
        }
        super.onSaveInstanceState(outState)
    }
}
