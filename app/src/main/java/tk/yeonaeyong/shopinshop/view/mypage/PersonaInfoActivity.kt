package tk.yeonaeyong.shopinshop.view.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_personal_info.*
import tk.yeonaeyong.shopinshop.R

class PersonaInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        btn_back.setOnClickListener {
            finish()
        }
    }
}