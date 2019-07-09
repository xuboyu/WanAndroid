package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.LoginPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.LoginView

/**
 * use：登录界面
 * author: XuBoYu
 * time: 2019/4/17
 **/

class LoginActivity : BaseActivity(), LoginView {

    //获取登录状态
    private var isLogin: Boolean by Preference(Constant.LOGIN_KEY,false)

    //获取账号
    private var userName: String by Preference(Constant.USERNAME_KEY,"")

    //获取密码
    private var passWard: String by Preference(Constant.PASSWORD_KEY,"")

    private val loginPresenter: LoginPresenterImpl by lazy {
        LoginPresenterImpl(this)
    }

    override fun setLayoutId(): Int = R.layout.activity_login

    override fun cancelRequest() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login.setOnClickListener(onClickListener)
        register.setOnClickListener(onClickListener)
    }

    /**
     * 控件监听事件
     */
    private val onClickListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.login -> {
                if (checkContent()) {
                    loginPresenter.loginWanAndroid(username.text.toString(), password.text.toString())
                }
            }

            R.id.register -> {
                Intent(this, RegisterActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
    }

    /**
     * 检验账号密码填写规范
     */
    private fun checkContent(): Boolean {
        // 错误提示
        username.error = null
        password.error = null
        // cancel
        var cancel = false
        // view
        var focusView: View? = null
        // 获取用户填写的账号密码
        var userNameText = username.text.toString()
        var passWordText = password.text.toString()

        // 检查账号，不能为空
        if (TextUtils.isEmpty(userNameText)) {
            username.error = getString(R.string.user_not_empty)
            focusView = username
            cancel = true
        }

        //检查密码，不能为空且不得低于6位
        if (TextUtils.isEmpty(passWordText)) {
            password.error = getString(R.string.pwd_not_empty)
            focusView = password
            cancel = true
        } else if (passWordText.length < 6) {
            password.error = getString(R.string.pwd_too_short)
            focusView = password
            cancel = true
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus()
            }
            return false
        } else {
            return true
        }

    }


    override fun loginSuccess(result: LoginResponse) {
        toast(getString(R.string.login_success))
    }

    override fun loginFailed(errorMsg: String?) {
        toast(getString(R.string.login_fail)+"-"+errorMsg)
    }

}