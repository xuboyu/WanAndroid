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
}