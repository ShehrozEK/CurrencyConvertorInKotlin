package shehroz.com.currencyconvertorinkotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Model {
    @SerializedName("base")
    @Expose
    var base : String = ""
    @SerializedName("date")
    @Expose
    var date : String = ""
    @SerializedName("time_last_updated")
    @Expose
    var timeLastUpdated : Int = 0
    @SerializedName("rates")
    lateinit var rates : HashMap<String,Double>
}