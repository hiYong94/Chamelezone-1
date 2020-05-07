package tk.yeonaeyong.shopinshop.view.home

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.MultipleDialogFragment
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.SingleDialogFragment
import tk.yeonaeyong.shopinshop.ext.Url.STORE_URL
import tk.yeonaeyong.shopinshop.view.home.presenter.StartContract
import tk.yeonaeyong.shopinshop.view.home.presenter.StartPresenter

class StartActivity : AppCompatActivity(), StartContract.View {
    override lateinit var presenter: StartContract.Presenter

    override fun showDialog(message: String) {
        val newFragment = SingleDialogFragment.newInstance(R.string.auto_login_fail)
        newFragment.show(supportFragmentManager, "dialog")
        val editor = getSharedPreferences("setting", 0)?.edit()
        editor?.clear()
        editor?.apply()
        presenter.logout()
    }

    override fun showAppUpdateDialog(version: String) {
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA)
        if (packageInfo.versionName != version) {
            val newFragment = MultipleDialogFragment.newInstance(
                getString(R.string.app_update), object :
                    MultipleDialogFragment.OnClickListener {
                    override fun onClick() {
                        val marketLaunch = Intent(
                            Intent.ACTION_VIEW
                        )
                        marketLaunch.data = Uri
                            .parse(STORE_URL)
                        startActivity(marketLaunch)
                        finish()
                    }
                }
            )
            newFragment.show(supportFragmentManager, "dialog")
        } else {
            setting()
        }
    }

    override fun showMessage(isSuccess: Boolean) {
        if (isSuccess) {
            startHomeActivity()
        }
    }

    private fun startHomeActivity() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        presenter = StartPresenter(
            Injection.memberRepository(), Injection.versionRepository(), this
        )
        presenter.getAppVersion()
    }

    fun setting() {
        val setting = getSharedPreferences("setting", 0)
        val email = setting.getString("ID", "")
        val password = setting.getString("PW", "")
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            presenter.userLogin(email, password)
        } else {
            presenter.logout()
        }
    }
}