package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.ArticleListResponse

/**
 * use：单个类型列表
 * author: XuBoYu
 * time: 2019/7/31
 **/
interface TypeArticleFragmentView {
    /**
     * 获取列表成功
     * @param result ArticleListResponse
     */
    fun getTypeArticleListSuccess(result: ArticleListResponse)

    /**
     * 获取列表失败
     * @param errorMessage error message
     */
    fun getTypeArticleListFailed(errorMessage: String?)

    /**
     * 获取列表长度为0
     */
    fun getTypeArticleListZero()

    /**
     * 获取列表长度小于20
     * @param result ArticleListResponse
     */
    fun getTypeArticleListSmall(result: ArticleListResponse)
}