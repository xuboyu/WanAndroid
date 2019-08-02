package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse

/**
 * use：收藏网址列表UI接口抽离
 * author: XuBoYu
 * time: 2019/8/1
 **/
interface CollectWebListView {

    /**
     * 成功回调
     * @param result 请求bean
     */
    fun getCollectWebListSuccess(result: CollectWebListResponse)

    /**
     * 失败回调
     * @param errorMessage 失败信息
     */
    fun getCollectWebListFailed(errorMessage: String?)

    /**
     * 获取列表长度为0
     */
    fun getCollectWebListZero()
}