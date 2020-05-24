package tk.yeonaeyong.shopinshop.view.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.ext.nextLineOptimize

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btn_back.setOnClickListener {
            finish()
        }
        tv_intro.nextLineOptimize()
    }
}