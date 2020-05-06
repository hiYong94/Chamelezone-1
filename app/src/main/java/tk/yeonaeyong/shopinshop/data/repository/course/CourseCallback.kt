package tk.yeonaeyong.shopinshop.data.repository.course

interface CourseCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(message: String)
}