package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.ArticleListResponse

/**
 * use：单个列表中间层
 * author: XuBoYu
 * time: 2019/7/31
 **/
interface TypeArticlePresenter {
    /**
     * 获取单个类型列表
     * @param page page
     * @param cid cid
     */
    fun getTypeArticleList(page: Int = 0, cid: Int)

    /**
     * 获取结果接口
     */
    interface OnTypeArticleListListener {
        /**
         * 获取成功
         * @param result ArticleListResponse
         */
        fun getTypeArticleListSuccess(result: ArticleListResponse)

        /**
         * 获取失败
         * @param errorMessage error message
         */
        fun getTypeArticleListFailed(errorMessage: String?)
    }
}