package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.model.CollectArticleModel
import wanandroid.xuboyu.com.wanandroid.model.HomeModelImpl
import wanandroid.xuboyu.com.wanandroid.view.CollectArticleView

/**
 * use：文章 等界面 接口
 * author: XuBoYu
 * time: 2019/7/30
 **/
class ContentPresenterImpl(private val collectArticleView: CollectArticleView) :
        HomePresenter.OnCollectArticleListener {

    private val collectArticleModel: CollectArticleModel = HomeModelImpl()

    override fun collectArticle(id: Int, isAdd: Boolean, isOfficial: Boolean, title: String, author: String, link: String) {
        collectArticleModel.collectArticle(this,id,isAdd, isOfficial,title,author,link)
    }

    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        if (result.errorCode != 0) {
            collectArticleView.collectArticleFailed(result.errorMsg, isAdd)
        } else {
            collectArticleView.collectArticleSuccess(result, isAdd)
        }
    }

    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        collectArticleView.collectArticleFailed(errorMessage, isAdd)
    }

    fun cancelRequest() {
        collectArticleModel.cancelCollectRequest()
    }

}