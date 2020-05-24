package tk.yeonaeyong.shopinshop.data.repository.member

interface MemberCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}