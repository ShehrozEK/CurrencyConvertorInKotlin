package shehroz.com.currencyconvertorinkotlin

import androidx.fragment.app.Fragment

open class BaseFragment() : Fragment(){
    lateinit var ctx: AppContext
    constructor(ctx: AppContext):this(){
        this.ctx = ctx
    }
}