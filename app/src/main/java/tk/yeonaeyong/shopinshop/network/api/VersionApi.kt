package tk.yeonaeyong.shopinshop.network.api

import retrofit2.Call
import retrofit2.http.GET
import tk.yeonaeyong.shopinshop.network.model.VersionResponse

interface VersionApi {
    @GET("/v1.0/release/apkVersion")
    fun getAppVersion(
    ): Call<VersionResponse>
}