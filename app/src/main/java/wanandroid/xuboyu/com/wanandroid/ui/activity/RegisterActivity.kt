package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_login.register
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.username
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
import wanandroid.xuboyu.com.wanandroid.loge
import wanandroid.xuboyu.com.wanandroid.presenter.RegisterPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.RegisterView

/**
 * use：注册界面
 * author: XuBoYu
 * time: 2019/7/5
 **/
class RegisterActivity: BaseActivity(), RegisterView {

    private val registerPresenter: RegisterPresenterImpl by lazy {
        RegisterPresenterImpl(this)
    }

    override fun setLayoutId(): Int = R.layout.activity_register

    override fun cancelRequest() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        back.setOnClickListener(onClickListener)
        register.setOnClickListener(onClickListener)
    }

    /**
     * 控件监听事件
     */
    private val onClickListener = View.OnClickListener { view ->
        when(view.id) {

            //返回按钮
            R.id.back -> {
                finish()
            }

            //注册按钮
            R.id.register -> {
                if(checkContent()) {
                    registerPresenter.registerWanAndroid(
                            username.text.toString(),
                            pwd.text.toString(),
                            repwd.text.toString())
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
        pwd.error = null
        repwd.error = null
        // cancel
        var cancel = false
        // view
        var focusView: View? = null
        // 获取用户填写的账号密码
        var userNameText = username.text.toString()
        var passWordText = pwd.text.toString()
        var repassWordText = repwd.text.toString()


        // 检查账号，不能为空
        if (TextUtils.isEmpty(userNameText)) {
            username.error = getString(R.string.user_not_empty)
            focusView = username
            cancel = true
        }

        //检查密码，不能为空且不得低于6位
        if (TextUtils.isEmpty(passWordText)) {
            pwd.error = getString(R.string.pwd_not_empty)
            focusView = pwd
            cancel = true
        } else if (passWordText.length < 6) {
            pwd.error = getString(R.string.pwd_too_short)
            focusView = pwd
            cancel = true
        }

        //检查确认密码格式及内容一致
        if (TextUtils.isEmpty(repassWordText)) {
            repwd.error = getString(R.string.pwd_not_empty)
            focusView = repwd
            cancel = true
        } else if (repassWordText.length < 6) {
            repwd.error = getString(R.string.pwd_too_short)
            focusView = repwd
            cancel = true
        } else if (!TextUtils.equals(passWordText,repassWordText)) {
            repwd.error = getString(R.string.repwd_not_same)
            focusView = repwd
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

    override fun registerSuccess(result: LoginResponse) {
        toast(getString(R.string.register_success))
    }

    override fun registerFailed(errorMsg: String?) {
        toast(getString(R.string.register_fail) + "-" + errorMsg)
    }

}