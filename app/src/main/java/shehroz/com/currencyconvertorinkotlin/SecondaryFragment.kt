package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.time.temporal.TemporalAmount
import java.util.*

class SecondaryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var earthView: ImageView
    private lateinit var loading_gif: View
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_secondary, container, false) as View
        recyclerView = view.findViewById(R.id.countries)
        loading_gif = view.findViewById(R.id.loading)
        earthView = view.findViewById(R.id.earthView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        Glide.with(this).asGif().load(R.drawable.earth).into(earthView)
        return view
    }

    fun populateRecyclerView(context: Context,model: Model,amount: String){
        loading_gif.visibility = View.VISIBLE
        adapter = RecyclerViewAdapter(context,model,amount)
        recyclerView.adapter = adapter
        loading_gif.visibility = View.GONE
    }
}
