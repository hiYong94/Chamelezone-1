package com.yeonae.chamelezone.data.repository.like

import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

interface LikeRepository {
    fun selectLike(
        memberNumber: Int,
        placeNumber: Int,
        callBack: LikeCallBack<LikeResponse>
    )

    fun deleteLike(
        likeNumber: Int,
        memberNumber: Int,
        placeNumber: Int,
        callBack: LikeCallBack<LikeResponse>
    )

    fun getMyLikeList(memberNumber: Int, callBack: LikeCallBack<List<PlaceResponse>>)
}