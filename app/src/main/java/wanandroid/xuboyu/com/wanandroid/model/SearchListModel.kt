package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.SearchPresenter

/**
 * use：搜索列表数据层抽离接口
 * author: XuBoYu
 * time: 2019/8/7
 **/
interface SearchListModel {
    /**
     * 获取搜索结果列表
     */
    fun getSearchList(
            onSearchListListener: SearchPresenter,
            page: Int = 0,
            k: String
    )

    /**
     * 取消请求
     */
    fun cancelRequest()

    /**
     * 添加或删除收藏
     */
    fun collectArticle(
            onCollectArticleListener: SearchPresenter.OnCollectArticleListener,
            id: Int,
            isAdd: Boolean,
            isOfficial: Boolean,
            title: String,
            author: String,
            link: String
    )

}