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
     * nickname
     */
    const val NICK_NAME_TEXT = "nickname"

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
    const val LOGOUT = "logout/json"

    /**
     * 首页文章列表
     */
    const val HomeList = "/article/list/{page}/json"

    /**
     * 首页banner轮播图
     */
    const val homeBanner = "/banner/json"


    /* ================================== END ==================================*/

    /* ================================== 开关属性设置 ==================================*/

    /**
     * Debug
     */
    const val INTERCEPTOR_ENABLE = true

    /* ================================== END ==================================*/

    /* ================================== OTHER CODE ==================================*/

    /**
     * 一些界面跳转参数设置
     */
    const val MY_REQUEST_CODE = 100
    const val HOME_REQUEST_CODE = 100

    /* ================================== END ==================================*/
}