package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
import wanandroid.xuboyu.com.wanandroid.model.HomeModel
import wanandroid.xuboyu.com.wanandroid.model.HomeModelImpl
import wanandroid.xuboyu.com.wanandroid.view.LoginView
import kotlin.math.log

/**
 * use：登录presenter接口
 * author: XuBoYu
 * time: 2019/7/1
 **/
class LoginPresenterImpl(private val loginView: LoginView) : HomePresenter.OnLoginListener{

    private val homeModel: HomeModel = HomeModelImpl()

    override fun loginWanAndroid(username: String, password: String) {
        homeModel.loginWanAndroid(this,username,password)
    }

    override fun loginSuccess(result: LoginResponse) {
        if (result.errorCode != 0) {
            loginView.loginFailed(result.errorMsg)
        } else {
            loginView.loginSuccess(result)
//            loginView.loginRegisterAfter(result)
        }
    }

    override fun loginFailed(errorMessage: String?) {
        loginView.loginFailed(errorMessage)
    }

}