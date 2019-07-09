package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse

/**
 * use：登录界面抽离接口
 * author: XuBoYu
 * time: 2019/4/19
 **/

interface LoginView {

    /**
     * 登录成功
     * @param result
     */
    fun loginSuccess(result: LoginResponse)

    /**
     * 登录失败返回失败信息（可为空）
     * @param errorMsg
     */
    fun loginFailed(errorMsg: String?)

}