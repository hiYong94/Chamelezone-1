package com.yeonae.chamelezone.data.source.remote.like

import com.yeonae.chamelezone.data.repository.like.LikeCallback
import com.yeonae.chamelezone.network.model.LikeResponse
import com.yeonae.chamelezone.network.model.PlaceResponse

interface LikeRemoteDataSource {
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