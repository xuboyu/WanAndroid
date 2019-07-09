package wanandroid.xuboyu.com.wanandroid.presenter

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

}