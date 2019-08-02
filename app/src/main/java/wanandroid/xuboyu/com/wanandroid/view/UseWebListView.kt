package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse

/**
 * use：常用网址UI抽离接口
 * author: XuBoYu
 * time: 2019/8/1
 **/
interface UseWebListView {
    /**
     * 成功回调
     * @param result 请求bean
     */
    fun getUseWebListSuccess(result: CollectWebListResponse)

    /**
     * 失败回调
     * @param errorMessage 失败信息
     */
    fun getUseWebListFailed(errorMessage: String?)

    /**
     * 获取列表长度为0
     */
    fun getUseWebListZero()
}