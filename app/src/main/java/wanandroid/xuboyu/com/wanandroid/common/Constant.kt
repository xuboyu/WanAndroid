package wanandroid.xuboyu.com.wanandroid.common

/**
 * use：公共参数定义
 * author: XuBoYu
 * time: 2019/4/12
 **/

object Constant {
    /**
     * baseUrl，支持https
     */
    const val REQUEST_BASE_URL = "https://wanandroid.com/"

    /**
     * 登录接口
     */
    const val LOGIN = REQUEST_BASE_URL + "user/login"

    /**
     * 注册接口
     */
    const val REGISTER = REQUEST_BASE_URL + "user/register"

    /**
     * 登出接口
     */
    const val LOGOUT = REQUEST_BASE_URL + "logout/json"
}