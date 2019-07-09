package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse

/**
 * use：注册界面接口抽离
 * author: XuBoYu
 * time: 2019/7/5
 **/
interface RegisterView {

    /**
     * 注册成功
     * @param result
     */
    fun registerSuccess(result: LoginResponse)

    /**
     * 注册失败
     * @param errorMsg
     */
    fun registerFailed(errorMsg: String?)
}