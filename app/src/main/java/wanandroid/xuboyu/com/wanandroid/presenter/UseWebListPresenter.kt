package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse

/**
 * use：常用网址中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/1
 **/
interface UseWebListPresenter {
    /**
     * 获取收藏列表
     */
    fun getUseWebList()

    /**
     * 常用网址列表
     */
    interface OnUseWebListListener {

        /**
         * 获取成功
         * @param result CollectWebListResponse
         */
        fun getUseWebListSuccess(result: CollectWebListResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getUseWebListFailed(errorMessage: String?)
    }

    /**
     * 收藏网址
     */
    fun collectWeb(name: String, link: String)

    /**
     * 收藏网址
     */
    interface OnCollectWebListener {
        /**
         * 收藏成功回调
         * @param result CollectWebListResponse
         */
        fun collectWebSuccess(result: CollectWebListResponse)

        /**
         * 收藏失败回调
         * @param errorMessage
         */
        fun collectWebFailed(errorMessage: String?)
    }
}