package com.yeonae.chamelezone

import android.content.Context
import com.yeonae.chamelezone.data.repository.course.CourseRepository
import com.yeonae.chamelezone.data.repository.course.CourseRepositoryImpl
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.member.MemberRepositoryImpl
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.data.repository.place.PlaceRepositoryImpl
import com.yeonae.chamelezone.data.source.local.member.MemberLocalDataSourceImpl
import com.yeonae.chamelezone.data.source.remote.course.CourseRemoteDataSourceImpl
import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSourceImpl
import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSourceImpl
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.room.database.UserDatabase
import com.yeonae.chamelezone.util.AppExecutors

object Injection {
    fun memberRepository(context: Context): MemberRepository {
        return MemberRepositoryImpl.getInstance(
            MemberRemoteDataSourceImpl.getInstance(RetrofitConnection.memberService),
            MemberLocalDataSourceImpl.getInstance(AppExecutors(), UserDatabase.getInstance(context))
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
}