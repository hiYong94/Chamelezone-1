package com.yeonae.chamelezone.data.repository.like

import com.yeonae.chamelezone.data.source.remote.like.LikeRemoteDataSource
import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

class LikeRepositoryImpl private constructor(
    private val remoteDataSource: LikeRemoteDataSource
) :
    LikeRepository {
    override fun selectLike(
        memberNumber: Int,
        placeNumber: Int,
        callBack: LikeCallBack<LikeResponse>
    ) {
        remoteDataSource.selectLike(memberNumber, placeNumber, callBack)
    }

    override fun deleteLike(
        memberNumber: Int,
        placeNumber: Int,
        callBack: LikeCallBack<LikeResponse>
    ) {
        remoteDataSource.deleteLike(memberNumber, placeNumber, callBack)
    }

    override fun getMyLikeList(memberNumber: Int, callBack: LikeCallBack<List<PlaceResponse>>) {
        remoteDataSource.getMyLikeList(memberNumber, callBack)
    }

    companion object {
        fun getInstance(remoteDataSource: LikeRemoteDataSource): LikeRepository =
            LikeRepositoryImpl(remoteDataSource)
    }
}