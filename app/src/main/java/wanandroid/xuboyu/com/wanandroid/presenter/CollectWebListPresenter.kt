package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.ArticleListResponse
import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse

/**
 * use：收藏网址列表中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/1
 **/
interface CollectWebListPresenter {

    /**
     * 获取收藏列表
     */
    fun getCollectWebList()

    interface OnCollectWebListListener {

        /**
         * 获取成功
         * @param result CollectWebListResponse
         */
        fun getCollectWebListSuccess(result: CollectWebListResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getCollectWebListFailed(errorMessage: String?)
    }

    /**
     * 删除收藏
     */
    fun deleteWeb(id: Int)

    interface OnDeleteWebListener {
        /**
         * 删除成功
         * @param result
         */
        fun deleteWebSuccess(result: ArticleListResponse)

        /**
         * 删除失败
         * @param errorMessage
         */
        fun deleteWebFialed(errorMessage: String?)
    }
}