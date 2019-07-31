package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.TypeArticlePresenter

/**
 * use：单个类型列表操作接口
 * author: XuBoYu
 * time: 2019/7/31
 **/
interface TypeArticleModel {
    /**
     * 获取列表
     * @param onTypeArticleListListener TypeArticlePresenter.OnTypeArticleListListener
     * @param page page
     * @param cid cid
     */
    fun getTypeArticleList(
            onTypeArticleListListener: TypeArticlePresenter.OnTypeArticleListListener,
            page: Int = 0,
            cid: Int
    )

    /**
     * 取消请求
     */
    fun cancelRequest()
}