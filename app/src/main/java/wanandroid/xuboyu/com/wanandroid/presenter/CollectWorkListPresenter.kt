package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：收藏文章列表中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/5
 **/
interface CollectWorkListPresenter {

    /**
    * 获取收藏列表
    */
    fun getCollectWorkList(page: Int)

    interface OnCollectWorkListListener {

        /**
         * 获取成功
         * @param result CollectWebListResponse
         */
        fun getCollectWorkListSuccess(result: HomeListResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getCollectWorkListFailed(errorMessage: String?)
    }

    /**
     * 删除收藏
     */
    fun deleteWork(id: Int, originId: Int)

    interface OnDeleteWorkListener {
        /**
         * 删除成功
         * @param result
         */
        fun deleteWorkSuccess(result: HomeListResponse)

        /**
         * 删除失败
         * @param errorMessage
         */
        fun deleteWorkFialed(errorMessage: String?)
    }
}