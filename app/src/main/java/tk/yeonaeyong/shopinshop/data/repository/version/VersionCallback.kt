package tk.yeonaeyong.shopinshop.data.repository.version

interface VersionCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}