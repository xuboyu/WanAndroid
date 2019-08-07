package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.HotSearchResponse

/**
 * use：其他模块抽离UI接口
 * author: XuBoYu
 * time: 2019/8/6
 **/
interface OhterFragmentView {
    /**
     * 获取热词成功
     */
    fun getHSListSuccess(result: HotSearchResponse)

    /**
     * 获取热词失败
     */
    fun getHSListFailed(errorMessage: String?)

    /**
     * 获取数组为0
     */
    fun getHSListZero()
}