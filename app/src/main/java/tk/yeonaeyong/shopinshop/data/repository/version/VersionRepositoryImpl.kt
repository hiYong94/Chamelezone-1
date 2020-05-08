package tk.yeonaeyong.shopinshop.data.repository.version

import tk.yeonaeyong.shopinshop.data.source.remote.version.VersionRemoteDataSource
import tk.yeonaeyong.shopinshop.network.model.VersionResponse

class VersionRepositoryImpl private constructor(
    private val remoteDataSource: VersionRemoteDataSource
) :
    VersionRepository {
    override fun getAppVersion(callback: VersionCallback<VersionResponse>) {
        remoteDataSource.getAppVersion(callback)
    }

    companion object {
        fun getInstance(remoteDataSource: VersionRemoteDataSource): VersionRepository =
            VersionRepositoryImpl(
                remoteDataSource
            )
    }
}