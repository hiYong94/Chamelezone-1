package tk.yeonaeyong.shopinshop.network.api

import retrofit2.Call
import retrofit2.http.GET
import tk.yeonaeyong.shopinshop.network.model.KeywordResponse

interface KeywordApi {
    @GET("/v1.0/keyword")
    fun getKeywordList(
    ): Call<List<KeywordResponse>>

    @GET("/v1.0/keyword/rank")
    fun getKeywordRank(
    ): Call<List<KeywordResponse>>
}