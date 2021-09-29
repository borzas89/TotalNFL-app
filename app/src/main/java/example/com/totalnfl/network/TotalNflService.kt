package example.com.totalnfl.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import example.com.totalnfl.data.api.Adjustments
import example.com.totalnfl.data.api.PredictedMatch
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface TotalNflService {

    @GET("predicted-pre-matches/")
    fun getPredictedPreMatches(): Single<List<PredictedMatch>>

    @GET("predicted-reg-matches/")
    fun getPredictedRegMatches(): Single<List<PredictedMatch>>

    @GET("predicted-pre-matches/week/{week}/")
    fun getPredictedPreMatchesByWeek(@Path("week") week: String): Single<List<PredictedMatch>>

    @GET("predicted-reg-matches/week/{week}/")
    fun getPredictedRegMatchesByWeek(@Path("week") week: String): Single<List<PredictedMatch>>

    @GET("predicted-matches/type/{type}/week/{week}/")
    fun getPredictedMatchesByWeek(@Path("type") type: String,@Path("week") week: String): Single<List<PredictedMatch>>

    @GET("predicted-reg-matches/{id}/")
    fun getPredictedMatchById(@Path("id") id: String): Single<PredictedMatch>

    @GET("adjustments/team")
    fun getAdjustmentsByTeamName(@Query("name") name: String): Single<Adjustments>

    companion object {
        private const val BASE_URL = "https://totalnfl-server.herokuapp.com/api/v2/"

        fun create(): TotalNflService {
            val moshi = Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TotalNflService::class.java)
        }
    }
}