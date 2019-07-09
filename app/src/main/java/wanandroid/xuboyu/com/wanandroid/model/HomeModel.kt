package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.HomePresenter

/**
 * use：首页Model接口
 * author: XuBoYu
 * time: 2019/7/2
 **/
interface HomeModel {

    /**
     * 登录操作
     * @param onLoginListener HomePresenter.OnLoginListener
     * @param username username
     * @param password password
     */
    fun loginWanAndroid(
            onLoginListener: HomePresenter.OnLoginListener,
            username: String,
            password: String
    )

    /**
     * 注册操作
     * @param onRegisterListener HomePresenter.OnRegisterListener
     * @param username username
     * @param password password
     * @param repassword repassword
     */
    fun registerWanAndroid(
            onRegisterListener: HomePresenter.OnRegisterListener,
            username: String,
            password: String,
            repassword: String
    )
}