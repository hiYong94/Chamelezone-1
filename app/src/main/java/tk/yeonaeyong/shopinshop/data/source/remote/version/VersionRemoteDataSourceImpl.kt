package tk.yeonaeyong.shopinshop.data.source.remote.version

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tk.yeonaeyong.shopinshop.data.Network
import tk.yeonaeyong.shopinshop.data.repository.version.VersionCallback
import tk.yeonaeyong.shopinshop.network.api.RetrofitConnection.versionService
import tk.yeonaeyong.shopinshop.network.api.VersionApi
import tk.yeonaeyong.shopinshop.network.model.VersionResponse

class VersionRemoteDataSourceImpl private constructor(private val versionApi: VersionApi) :
    VersionRemoteDataSource {
    override fun getAppVersion(callback: VersionCallback<VersionResponse>) {
        versionService.getAppVersion().enqueue(object :Callback<VersionResponse>{
            override fun onFailure(call: Call<VersionResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }

            override fun onResponse(
                call: Call<VersionResponse>,
                response: Response<VersionResponse>
            ) {
                if (response.code() == Network.SUCCESS) {
                    response.body()?.let { callback.onSuccess(it) }
                }
            }

        })
    }

    companion object {
        fun getInstance(versionApi: VersionApi): VersionRemoteDataSource =
            VersionRemoteDataSourceImpl(
                versionApi
            )
    }
}