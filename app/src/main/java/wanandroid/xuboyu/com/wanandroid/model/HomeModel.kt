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
     * 取消登录访问
     */
    fun cancelLoginRequest()

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

    /**
     * 取消注册访问
     */
    fun cancelRegisterRequest()

    /**
     * 获取首页文章列表
     * @param onHomeListListener HomePresenter.OnHomeListListener
     * @param page page
     */
    fun getHomeList(
            onHomeListListener: HomePresenter.OnHomeListListener,
            page: Int = 0
    )

    /**
     * 取消首页列表请求
     */
    fun cancelHomeListRequest()

    /**
     * 获取首页轮播图
     * @param onBannerListener HomePresenter.OnBannerListener
     */
    fun getBanner(onBannerListener: HomePresenter.OnBannerListener)


    /**
     * 取消轮播图请求
     */
    fun cancelBannerRequest()

}