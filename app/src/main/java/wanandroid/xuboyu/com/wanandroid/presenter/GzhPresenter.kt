package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.GzhResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：公众号列表中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/8
 **/
interface GzhPresenter {

    /**
     * 公众号列表
     */
    interface OnGzhListener {

        /**
         * 获取收藏列表
         */
        fun getGzh()

        /**
         * 获取成功
         * @param result GzhResponse
         */
        fun getGzhSuccess(result: GzhResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getGzhFailed(errorMessage: String?)
    }

    /**
     * 公众号文章列表
     */
    interface OnGzhListListener {

        /**
         * 获取收藏列表
         */
        fun getGzhList(id: Int, page: Int)

        /**
         * 获取成功
         * @param result HomeListResponse
         */
        fun getGzhListSuccess(result: HomeListResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getGzhListFailed(errorMessage: String?)


    }


}