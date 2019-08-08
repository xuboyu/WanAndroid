package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.GzhResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.model.CollectArticleModel
import wanandroid.xuboyu.com.wanandroid.model.GzhModel
import wanandroid.xuboyu.com.wanandroid.model.GzhModelImpl
import wanandroid.xuboyu.com.wanandroid.model.HomeModelImpl
import wanandroid.xuboyu.com.wanandroid.view.GzhListView
import wanandroid.xuboyu.com.wanandroid.view.GzhView

/**
 * use：公众号中间层实现类
 * author: XuBoYu
 * time: 2019/8/8
 **/
class GzhPresenterImpl(private val gzhView: GzhView?,
                       private val gzhListView: GzhListView?)
    : GzhPresenter.OnGzhListener,GzhPresenter.OnGzhListListener,
    HomePresenter.OnCollectArticleListener{

    private val gzhListModel: GzhModel = GzhModelImpl()
    private val collectArticleModel: CollectArticleModel = HomeModelImpl()

    override fun getGzh() {
        gzhListModel.getGzh(this)
    }

    override fun getGzhSuccess(result: GzhResponse) {
        if (result.errorCode != 0) {
            gzhView?.getGzhFailed(result.errorMsg)
            return
        }
        gzhView?.getGzhSuccess(result)
    }

    override fun getGzhFailed(errorMessage: String?) {
        gzhView?.getGzhFailed(errorMessage)
    }

    override fun getGzhList(id: Int, page: Int) {
        gzhListModel.getGzhList(this,id,page)
    }

    override fun getGzhListSuccess(result: HomeListResponse) {
        if (result.errorCode != 0) {
            gzhListView?.getGzhListFailed(result.errorMsg)
            return
        }
        gzhListView?.getGzhListSuccess(result)
    }

    override fun getGzhListFailed(errorMessage: String?) {
        gzhListView?.getGzhListFailed(errorMessage)
    }

    override fun collectArticle(id: Int, isAdd: Boolean, isOfficial: Boolean, title: String, author: String, link: String) {
        collectArticleModel.collectArticle(this,id,isAdd, isOfficial,title,author,link)
    }

    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        if (result.errorCode != 0) {
            gzhListView?.docWorkFailed(result.errorMsg, isAdd)
        } else {
            gzhListView?.docWorkSuccess(result, isAdd)
        }
    }

    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        gzhListView?.docWorkFailed(errorMessage, isAdd)
    }

    fun cancelRequest() {
        gzhListModel.cancelGzhRequest()
        collectArticleModel.cancelCollectRequest()
    }

}