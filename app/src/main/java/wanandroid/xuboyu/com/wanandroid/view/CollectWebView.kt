package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse

/**
 * use：收藏网址UI抽离接口
 * author: XuBoYu
 * time: 2019/8/2
 **/
interface CollectWebView {
    /**
     * 成功回调
     * @param result 请求bean
     */
    fun getCollectWebSuccess(result: CollectWebListResponse)

    /**
     * 失败回调
     * @param errorMessage 失败信息
     */
    fun getCollectWebFailed(errorMessage: String?)
}