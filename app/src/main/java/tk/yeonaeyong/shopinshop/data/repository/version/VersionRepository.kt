package tk.yeonaeyong.shopinshop.data.repository.version

import tk.yeonaeyong.shopinshop.network.model.VersionResponse

interface VersionRepository {
    fun getAppVersion(callback: VersionCallback<VersionResponse>)
}