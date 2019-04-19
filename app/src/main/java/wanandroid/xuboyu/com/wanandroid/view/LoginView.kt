package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.LoginBean

/**
 * use：登录界面抽离接口
 * author: XuBoYu
 * time: 2019/4/19
 **/

interface LoginView {

    /**
     * 登录成功返回LoginBean
     */
    fun loginSuccess(result: LoginBean)

    /**
     * 登录失败返回失败信息（可为空）
     */
    fun loginFailed(errorMsg: String?)

//    fun registerSuccess()
}