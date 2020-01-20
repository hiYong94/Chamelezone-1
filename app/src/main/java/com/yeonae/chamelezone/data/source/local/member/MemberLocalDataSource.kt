package com.yeonae.chamelezone.data.source.local.member

import com.yeonae.chamelezone.network.model.MemberResponse

interface MemberLocalDataSource {
    fun loggedLogin(memberResponse: MemberResponse)
}