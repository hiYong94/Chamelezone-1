package tk.yeonaeyong.shopinshop.data.repository.place

interface PlaceCallback<T>{
    fun onSuccess(response: T)
    fun onFailure(message: String)
}