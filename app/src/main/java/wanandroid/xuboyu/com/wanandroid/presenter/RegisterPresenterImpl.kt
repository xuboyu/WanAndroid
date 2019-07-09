package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
import wanandroid.xuboyu.com.wanandroid.loge
import wanandroid.xuboyu.com.wanandroid.model.HomeModel
import wanandroid.xuboyu.com.wanandroid.model.HomeModelImpl
import wanandroid.xuboyu.com.wanandroid.view.RegisterView

/**
 * use：注册presenter接口
 * author: XuBoYu
 * time: 2019/7/5
 **/
class RegisterPresenterImpl(private val registerView: RegisterView) : HomePresenter.OnRegisterListener {

    private val homeModel: HomeModel = HomeModelImpl()

    override fun registerWanAndroid(username: String, password: String, repassword: String) {
        homeModel.registerWanAndroid(this, username, password, repassword)
    }

    override fun registerSuccess(result: LoginResponse) {
        if (result.errorCode != 0) {
            registerView.registerFailed(result.errorMsg)
        } else {
            registerView.registerSuccess(result)
        }
    }

    override fun registerFailed(errorMessage: String?) {
        registerView.registerFailed(errorMessage)
    }

    fun cancelRequest() {
        homeModel.cancelRegisterRequest()
    }

}