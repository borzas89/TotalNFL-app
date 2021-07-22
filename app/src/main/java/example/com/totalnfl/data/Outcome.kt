package example.com.totalnfl.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Outcome {
    @Json(name = "name")
    var name: String? = null

    @Json(name = "price")
    var price: Double? = null

    @Json(name = "point")
    var point: Double? = null

}