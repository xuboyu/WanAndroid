package wanandroid.xuboyu.com.wanandroid.common

import android.widget.Toast

/**
 * use：公共参数定义
 * author: XuBoYu
 * time: 2019/4/12
 **/

object Constant {

    /* ================================== SharedPreferences 部分 ==================================*/

    /**
     * Share preferences name
     */
    const val SHARED_NAME = "_preferences"
    const val LOGIN_KEY = "login"
    const val USERNAME_KEY = "username"
    const val PASSWORD_KEY = "password"

    /* ================================== END ==================================*/

    /* ================================== TIP ==================================*/

    /**
     * result null
     */
    const val RESULT_NULL = "result null!"

    /**
     * Toast
     */
    @JvmField
    var showToast: Toast? = null

    /* ================================== END ==================================*/

    /* ================================== 接口部分 ==================================*/

    /**
     * baseUrl，支持https
     */
    const val REQUEST_BASE_URL = "https://wanandroid.com/"

    /**
     * 登录接口
     */
    const val LOGIN = "user/login"

    /**
     * 注册接口
     */
    const val REGISTER = "user/register"

    /**
     * 登出接口
     */
    const val LOGOUT = REQUEST_BASE_URL + "logout/json"

    /* ================================== END ==================================*/

    /* ================================== 开关属性设置 ==================================*/

    /**
     * Debug
     */
    const val INTERCEPTOR_ENABLE = true

    /* ================================== END ==================================*/
}