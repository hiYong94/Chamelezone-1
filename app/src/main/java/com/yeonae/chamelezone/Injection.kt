package com.yeonae.chamelezone

import android.content.Context
import com.yeonae.chamelezone.data.repository.member.MemberRepository
import com.yeonae.chamelezone.data.repository.member.MemberRepositoryImpl
import com.yeonae.chamelezone.data.repository.place.PlaceRepository
import com.yeonae.chamelezone.data.repository.place.PlaceRepositoryImpl
import com.yeonae.chamelezone.data.source.local.member.MemberLocalDataSourceImpl
import com.yeonae.chamelezone.data.source.remote.member.MemberRemoteDataSourceImpl
import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSourceImpl
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.room.database.UserDatabase

object Injection {
    fun memberRepository(context: Context): MemberRepository {
        return MemberRepositoryImpl.getInstance(
            MemberRemoteDataSourceImpl.getInstance(RetrofitConnection.memberService),
            MemberLocalDataSourceImpl.getInstance(UserDatabase.getInstance(context))
        )
    }
    fun placeRepository(context: Context) : PlaceRepository {
        return PlaceRepositoryImpl.getInstance(
            PlaceRemoteDataSourceImpl.getInstance(RetrofitConnection.placeService)
        )
    }
}