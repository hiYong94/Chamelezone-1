package tk.yeonaeyong.shopinshop

import tk.yeonaeyong.shopinshop.data.repository.course.CourseRepository
import tk.yeonaeyong.shopinshop.data.repository.course.CourseRepositoryImpl
import tk.yeonaeyong.shopinshop.data.repository.like.LikeRepository
import tk.yeonaeyong.shopinshop.data.repository.like.LikeRepositoryImpl
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepository
import tk.yeonaeyong.shopinshop.data.repository.member.MemberRepositoryImpl
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepository
import tk.yeonaeyong.shopinshop.data.repository.place.PlaceRepositoryImpl
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepository
import tk.yeonaeyong.shopinshop.data.repository.review.ReviewRepositoryImpl
import tk.yeonaeyong.shopinshop.data.source.local.member.MemberLocalDataSourceImpl
import tk.yeonaeyong.shopinshop.data.source.remote.course.CourseRemoteDataSourceImpl
import tk.yeonaeyong.shopinshop.data.source.remote.like.LikeRemoteDataSourceImpl
import tk.yeonaeyong.shopinshop.data.source.remote.member.MemberRemoteDataSourceImpl
import tk.yeonaeyong.shopinshop.data.source.remote.place.PlaceRemoteDataSourceImpl
import tk.yeonaeyong.shopinshop.data.source.remote.review.ReviewRemoteDataSourceImpl
import tk.yeonaeyong.shopinshop.network.api.RetrofitConnection
import tk.yeonaeyong.shopinshop.network.room.database.UserDatabase
import tk.yeonaeyong.shopinshop.util.AppExecutors

object Injection {
    fun memberRepository(): MemberRepository {
        return MemberRepositoryImpl.getInstance(
            MemberRemoteDataSourceImpl.getInstance(RetrofitConnection.memberService),
            MemberLocalDataSourceImpl.getInstance(
                AppExecutors(),
                UserDatabase.getInstance(App.instance.context())
            )
        )
    }

    fun placeRepository(): PlaceRepository {
        return PlaceRepositoryImpl.getInstance(
            PlaceRemoteDataSourceImpl.getInstance(RetrofitConnection.placeService)
        )
    }

    fun courseRepository(): CourseRepository {
        return CourseRepositoryImpl.getInstance(
            CourseRemoteDataSourceImpl.getInstance(
                RetrofitConnection.courseService
            )
        )
    }

    fun reviewRepository(): ReviewRepository {
        return ReviewRepositoryImpl.getInstance(
            ReviewRemoteDataSourceImpl.getInstance(
                RetrofitConnection.reviewService
            )
        )
    }

    fun likeRepository(): LikeRepository {
        return LikeRepositoryImpl.getInstance(
            LikeRemoteDataSourceImpl.getInstance(RetrofitConnection.likeService)
        )
    }
}