package example.com.totalnfl.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import example.com.totalnfl.BuildConfig
import example.com.totalnfl.data.OddsApiResponse
import example.com.totalnfl.data.PredictedMatch
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface OddsApiService {

    @GET("v4/sports/americanfootball_nfl/odds/?apiKey=${BuildConfig.ODDS_API_KEY}&regions=eu&markets=totals,spreads&oddsFormat=decimal")
    fun getOddsForMatches(): Single<List<OddsApiResponse>>

    companion object {
        private const val BASE_URL = "https://api.the-odds-api.com/"
        fun create(): OddsApiService {
            val moshi = Moshi.Builder()
                    .add(Date::class.java, Rfc3339DateJsonAdapter())
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(OddsApiService::class.java)
        }
    }
}