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
     * password
     */
    const val PASSWORD_TEXT = "password"

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
    const val HOMELIST = "/article/list/{page}/json"

    /**
     * 首页banner轮播图
     */
    const val HOMEBANNER = "/banner/json"

    /**
     * 收藏站内文章
     */
    const val ADDCOLLECTIN = "/lg/collect/{id}/json"

    /**
     * 收藏站外文章
     */
    const val ADDCOLLECTOUT = "/lg/collect/add/json"

    /**
     * 取消收藏文章
     */
    const val REMOVE_COLLECT = "/lg/uncollect_originId/{id}/json"


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
    const val REGISTER_REQUEST_CODE = 100

    /**
     * 分享相关设置
     */
    const val CONTENT_SHARE_TYPE = "text/plain"
    //纯文本的形式，浏览器在获取到这种文件时并不会对其进行处理
    //text/html的意思是将文件的content-type设置为text/html的形式，浏览器在获取到这种文件时会自动调用html的解析器对文件进行相应的处理。

    /**
     * 文章跳转 url
     */
    const val CONTENT_URL_KEY = "url"
    /**
     * 文章跳转 title
     */
    const val CONTENT_TITLE_KEY = "title"
    /**
     * 文章 id
     */
    const val CONTENT_ID_KEY = "id"
    /**
     * 文章 userid
     */
    const val CONTENT_USER_ID = "userId"
    /**
     * 文章 收藏状态
     */
    const val CONTENT_IS_COLLECT = "collect"
    /**
     * 文章 作者
     */
    const val CONTENT_AUTHOR = "author"

    /* ================================== END ==================================*/
}