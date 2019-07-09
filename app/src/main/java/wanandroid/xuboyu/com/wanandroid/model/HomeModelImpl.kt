package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.HomePresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch
import kotlin.math.log

/**
 * use：首页Model实现类
 * author: XuBoYu
 * time: 2019/7/2
 **/
class HomeModelImpl : HomeModel{

    /**
     * Login async
     */
    private var loginAsync: Deferred<LoginResponse>? = null

    /**
     * Register async
     */
    private var registerAsync: Deferred<LoginResponse>? = null

    override fun loginWanAndroid(
            onLoginListener: HomePresenter.OnLoginListener,
            username: String,
            password: String) {

            async(UI) {
                tryCatch({
                    //catch区块
                    it.printStackTrace()
                    onLoginListener.loginFailed(it.toString())
                }) {
                    //try区块
                    loginAsync?.cancelByActivite()//存活取消
                    loginAsync = RetrofitHelper.retrofitService.loginWanAndroid(username, password)
                    val result = loginAsync?.await()
                    result ?: let {
                        onLoginListener.loginFailed(Constant.RESULT_NULL)
                        return@async
                    }
                    result?.let { onLoginListener.loginSuccess(it) }
                }
            }

    }

    override fun cancelLoginRequest() {
        loginAsync?.cancelByActivite()
    }

    override fun registerWanAndroid(
            onRegisterListener: HomePresenter.OnRegisterListener,
            username: String,
            password: String,
            repassword: String) {

        async(UI) {
            tryCatch ({
                //catch区块
                it.printStackTrace()
                onRegisterListener.registerFailed(it.toString())
            }) {
                //try区块
                registerAsync?.cancelByActivite()//存活取消
                registerAsync = RetrofitHelper.retrofitService.registerWanAndroid(username,password,repassword)
                val result = registerAsync?.await()
                result ?: let {
                    onRegisterListener.registerFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onRegisterListener.registerSuccess(it) }
            }
        }
    }

    override fun cancelRegisterRequest() {
        registerAsync?.cancelByActivite()
    }

}