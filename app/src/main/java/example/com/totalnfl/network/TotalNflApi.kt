package example.com.totalnfl.network

import example.com.totalnfl.data.api.AdjustmentDto
import example.com.totalnfl.data.api.MarketDto
import example.com.totalnfl.data.api.PredictedMatchDto
import io.reactivex.Single
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TotalNflApi {

    @GET("predicted-pre-matches/")
    fun getPredictedPreMatches(): Single<List<PredictedMatchDto>>

    @GET("predicted-reg-matches/")
    fun getPredictedRegMatches(): Single<List<PredictedMatchDto>>

    @GET("predicted-pre-matches/week/{week}/")
    fun getPredictedPreMatchesByWeek(@Path("week") week: String): Single<List<PredictedMatchDto>>

    @GET("predicted-reg-matches/week/{week}/")
    fun getPredictedRegMatchesByWeek(@Path("week") week: String): Single<List<PredictedMatchDto>>

    @GET("predicted-matches/type/{type}/week/{week}/")
    fun getPredictedMatchesByWeek(@Path("type") type: String,@Path("week") week: String): Single<List<PredictedMatchDto>>

    @GET("predicted-reg-matches/{id}/")
    fun getPredictedMatchById(@Path("id") id: String): Single<PredictedMatchDto>

    @GET("adjustments/team")
    fun getAdjustmentsByTeamName(@Query("name") name: String): Single<AdjustmentDto>

    @GET("prediction/day/{day}/")
    fun getPredictedMatchesByDay(@Path("day") day: String): Single<List<PredictedMatchDto>>

    @GET("market/id")
    fun getMarketByCommonMatchId(@Query("commonId") commonMatchId: String): Single<MarketDto>

    companion object {
        private const val BASE_URL = "https://totalnfl-server.herokuapp.com/api/v2/"

        fun create(): TotalNflApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TotalNflApi::class.java)
        }
    }
}