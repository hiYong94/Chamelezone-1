package com.yeonae.chamelezone.data.repository.like

import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

interface LikeRepository {
    fun selectLike(
        memberNumber: Int,
        placeNumber: Int,
        callback: LikeCallback<LikeResponse>
    )

    fun deleteLike(
        memberNumber: Int,
        placeNumber: Int,
        callback: LikeCallback<LikeResponse>
    )

    fun getMyLikeList(memberNumber: Int, callback: LikeCallback<List<PlaceResponse>>)
}