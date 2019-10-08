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

class SecondaryFragment(): BaseFragment(){
    private lateinit var appContext: AppContext
    constructor(appContext: AppContext):this(){
        this.appContext = appContext
        BaseFragment(appContext)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_secondary, container, false) as View
        return view
    }
}
