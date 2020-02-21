package com.yeonae.chamelezone.data.source.remote.like

import com.yeonae.chamelezone.data.repository.like.LikeCallBack
import com.yeonae.chamelezone.network.model.PlaceResponse

interface LikeRemoteDataSource {
    fun selectLike(
        memberNumber: Int,
        placeNumber: Int,
        callBack: LikeCallBack<Boolean>
    )

    fun deleteLike(
        likeNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        callBack: LikeCallBack<Boolean>
    )

    fun getMyLikeList(memberNumber: Int, callBack: LikeCallBack<List<PlaceResponse>>)
}