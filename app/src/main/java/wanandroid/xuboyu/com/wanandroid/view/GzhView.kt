package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.GzhResponse

/**
 * use：公众号列表UI抽离接口
 * author: XuBoYu
 * time: 2019/8/8
 **/
interface GzhView {

    /**
     * 获取成功
     */
    fun getGzhSuccess(result: GzhResponse)

    /**
     * 获取失败
     */
    fun getGzhFailed(errorMsg: String?)

}