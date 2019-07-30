package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.HomePresenter

/**
 * use：收藏接口
 * author: XuBoYu
 * time: 2019/7/29
 **/
interface CollectArticleModel {

    /**
     * 添加或删除收藏
     */
    fun collectArticle(
            onCollectArticleListener: HomePresenter.OnCollectArticleListener,
            id: Int,
            isAdd: Boolean,
            isOfficial: Boolean,
            title: String,
            author: String,
            link: String
    )

    /**
     * 取消接口访问
     */
    fun cancelCollectRequest()
}