package com.yeonae.chamelezone.data.repository.place

import com.yeonae.chamelezone.data.sourse.remote.place.PlaceRemoteDataSource

class PlaceRepositoryImpl private constructor(private val remoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override fun registerPlace() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlace() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePlace() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun modifyPlace() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        fun getInstance(remoteDataSource: PlaceRemoteDataSource): PlaceRepository =
            PlaceRepositoryImpl(remoteDataSource)
    }
}