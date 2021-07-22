package example.com.totalnfl.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Market {
    @Json(name = "key")
    var key: String? = null

    @Json(name = "outcomes")
    var outcomes: List<Outcome>? = null

}