package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.model.SearchListModel
import wanandroid.xuboyu.com.wanandroid.model.SearchListModelIpml
import wanandroid.xuboyu.com.wanandroid.view.SearchListView

/**
 * use：搜索列表中间层实现类
 * author: XuBoYu
 * time: 2019/8/7
 **/
class SearchPresenterImpl (private val searchListView: SearchListView)
        : SearchPresenter, SearchPresenter.OnCollectArticleListener {

    override fun collectArticle(id: Int, isAdd: Boolean, isOfficial: Boolean, title: String, author: String, link: String) {
        searchListModelImpl.collectArticle(this,id,isAdd, isOfficial,title,author,link)
    }

    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        if (result.errorCode != 0) {
            searchListView.collectArticleFailed(result.errorMsg, isAdd)
        } else {
            searchListView.collectArticleSuccess(result, isAdd)
        }
    }

    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        searchListView.collectArticleFailed(errorMessage, isAdd)
    }

    private val searchListModelImpl: SearchListModel = SearchListModelIpml()

    override fun getSearchList(page: Int, k: String) {
        searchListModelImpl.getSearchList(this,page,k)
    }

    override fun getSearchListSuccess(result: HomeListResponse) {
        if (result.errorCode != 0) {
            searchListView.getSearchListFailed(result.errorMsg)
        }
        // 列表总数
        val total = result.data.total
        if (total == 0) {
            searchListView.getSearchListZero()
            return
        }
        // 当第一页小于一页总数时
        if (total < result.data.size) {
            searchListView.getSearchListSmall(result)
            return
        }
        searchListView.getSearchListSuccess(result)
    }

    override fun getSearchListFailed(errorMessage: String?) {
        searchListView.getSearchListFailed(errorMessage)
    }

    fun cancelRequest() {
        searchListModelImpl.cancelRequest()
    }

}