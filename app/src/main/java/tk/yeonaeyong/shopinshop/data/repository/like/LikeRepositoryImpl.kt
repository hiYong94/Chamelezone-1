package tk.yeonaeyong.shopinshop.data.repository.like

import tk.yeonaeyong.shopinshop.data.source.remote.like.LikeRemoteDataSource
import tk.yeonaeyong.shopinshop.network.model.LikeResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

class LikeRepositoryImpl private constructor(
    private val remoteDataSource: LikeRemoteDataSource
) :
    LikeRepository {
    override fun selectLike(
        memberNumber: Int,
        placeNumber: Int,
        callback: LikeCallback<LikeResponse>
    ) {
        remoteDataSource.selectLike(memberNumber, placeNumber, callback)
    }

    override fun deleteLike(
        memberNumber: Int,
        placeNumber: Int,
        callback: LikeCallback<LikeResponse>
    ) {
        remoteDataSource.deleteLike(memberNumber, placeNumber, callback)
    }

    override fun getMyLikeList(memberNumber: Int, callback: LikeCallback<List<PlaceResponse>>) {
        remoteDataSource.getMyLikeList(memberNumber, callback)
    }

    companion object {
        fun getInstance(remoteDataSource: LikeRemoteDataSource): LikeRepository =
            LikeRepositoryImpl(
                remoteDataSource
            )
    }
}