package shehroz.com.currencyconvertorinkotlin

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
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
import java.util.*

class PrimaryFragment(appContext: AppContext): BaseFragment(appContext),AdapterView.OnItemSelectedListener,Animation.AnimationListener {
    var appContext:AppContext = appContext
    lateinit var currencyTextView : TextView
    lateinit var errorTextView: TextView
    lateinit var currencyAmount : EditText
    lateinit var currencyAmountViewGroup: LinearLayout
    lateinit var blinkAnim : Animation
    var simpleCursorAdapter : SpinnerCustomAdapter? = null

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(simpleCursorAdapter!=null){
            val key =  simpleCursorAdapter!!.getItem(p2) as String
            currencyTextView.text = key
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_primary, container, false) as View
        currencyTextView = view.findViewById<TextView>(R.id.currency)
        errorTextView = view.findViewById<TextView>(R.id.error)
        currencyAmount = view.findViewById<EditText>(R.id.amount)
        currencyAmountViewGroup = view.findViewById<LinearLayout>(R.id.currencyAmountViewGroup)
        val countrySpinner = view.findViewById<Spinner>(R.id.countrySpinner)
        simpleCursorAdapter = SpinnerCustomAdapter(appContext.getContext(),appContext.getCountriesHashMap())
        countrySpinner.adapter = simpleCursorAdapter
        countrySpinner.onItemSelectedListener = this
        blinkAnim = AnimationUtils.loadAnimation(context,R.anim.blink)
        blinkAnim.setAnimationListener(this)
        view.findViewById<Button>(R.id.convert).setOnClickListener(View.OnClickListener {
            if(currencyAmount.text.isEmpty()){
                errorTextView.visibility = View.VISIBLE
                currencyAmountViewGroup.setBackgroundResource(R.drawable.error_bg)
                currencyAmount.clearFocus()
                currencyAmountViewGroup.startAnimation(blinkAnim)
            }
        })
        currencyAmount.addTextChangedListener(textWatcher)
        return view
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    override fun onAnimationEnd(p0: Animation?) {
        if(p0==blinkAnim){
            currencyAmountViewGroup.clearAnimation()
            errorTextView.visibility = View.GONE
            currencyAmountViewGroup.setBackgroundResource(0)
            currencyAmount.requestFocus()
            val keyboard = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(currencyAmount,InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun onAnimationStart(p0: Animation?) {}

    private val textWatcher = object :TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
            if(!blinkAnim.hasEnded()){
               blinkAnim.cancel()
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
}
