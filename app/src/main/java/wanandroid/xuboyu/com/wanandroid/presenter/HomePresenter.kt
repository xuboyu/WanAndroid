package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse

interface HomePresenter {

    /**
     * login 监听接口
     */
    interface OnLoginListener {
        /**
         * login
         * @param username username
         * @param password password
         */
        fun loginWanAndroid(username: String, password: String)

        /**
         * login success
         * @param result LoginResponse
         */
        fun loginSuccess(result: LoginResponse)

        /**
         * login failed
         * @param errorMessage error message
         */
        fun loginFailed(errorMessage: String?)
    }

    /**
     * register 监听接口
     */
    interface OnRegisterListener {
        /**
         * register
         * @param username username
         * @param password password
         * @param repassword repassword
         */
        fun registerWanAndroid(username: String, password: String, repassword: String)

        /**
         * register success
         * @param result LoginResponse
         */
        fun registerSuccess(result: LoginResponse)

        /**
         * register failed
         * @param errorMessage error message
         */
        fun registerFailed(errorMessage: String?)
    }

    /**
     * 获取首页文章列表
     */
    interface OnHomeListListener {

        /**
         * 获取文章列表
         * @param page page
         */
        fun getHomeList(page: Int = 0)

        /**
         * 获取成功
         * @param result result
         */
        fun getHomeListSuccess(result: HomeListResponse)

        /**
         * 获取失败
         * @param errorMessage error message
         */
        fun getHomeListFailed(errorMessage: String?)
    }

    /**
     * get banner listener
     */
    interface OnBannerListener {
        /**
         * get banner
         */
        fun getBanner()

        /**
         * get banner success
         * @param result BannerResponse
         */
        fun getBannerSuccess(result: BannerResponse)

        /**
         * get banner failed
         * @param errorMessage error message
         */
        fun getBannerFailed(errorMessage: String?)
    }

}