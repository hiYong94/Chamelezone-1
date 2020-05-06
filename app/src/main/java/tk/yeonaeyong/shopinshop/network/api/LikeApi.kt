package tk.yeonaeyong.shopinshop.network.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import tk.yeonaeyong.shopinshop.network.model.LikeResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

interface LikeApi {
    @POST("/v1.0/user/{memberNumber}/like")
    fun selectLike(
        @Path("memberNumber") memberNumber: Int,
        @Body placeNumber: JsonObject
    ): Call<LikeResponse>

    @HTTP(method = "DELETE", path = "/v1.0/user/{memberNumber}/like", hasBody = true)
    fun deleteLike(
        @Path("memberNumber") memberNumber: Int,
        @Body placeNumber: JsonObject
    ): Call<LikeResponse>

    @GET("/v1.0/user/{memberNumber}/likes")
    fun getMyLikeList(
        @Path("memberNumber") memberNumber: Int
    ): Call<List<PlaceResponse>>
}