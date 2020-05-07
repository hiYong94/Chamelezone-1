package tk.yeonaeyong.shopinshop.data.source.remote.version

import tk.yeonaeyong.shopinshop.data.repository.version.VersionCallback
import tk.yeonaeyong.shopinshop.network.model.VersionResponse

interface VersionRemoteDataSource {
    fun getAppVersion(callback: VersionCallback<VersionResponse>)
}