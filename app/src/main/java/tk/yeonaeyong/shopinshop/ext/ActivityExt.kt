package tk.yeonaeyong.shopinshop.ext

import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import tk.yeonaeyong.shopinshop.view.LoadingDialogFragment

fun AppCompatActivity.showLoading() {
    LoadingDialogFragment().show(supportFragmentManager, "dialog")
}

fun AppCompatActivity.hideLoading() {
    supportFragmentManager.findFragmentByTag("dialog")?.let {
        (it as? LoadingDialogFragment)?.dismiss()
    }
}

fun EditText.setTouchForScrollBars() {
    setOnTouchListener { view, event ->
        view.parent.requestDisallowInterceptTouchEvent(true)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
        }
        false
    }
}