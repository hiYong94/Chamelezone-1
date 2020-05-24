package tk.yeonaeyong.shopinshop.data.repository.like

import tk.yeonaeyong.shopinshop.network.model.LikeResponse
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse

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