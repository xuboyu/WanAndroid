package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.SearchPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：搜索列表数据层实现类
 * author: XuBoYu
 * time: 2019/8/7
 **/
class SearchListModelIpml : SearchListModel {

    private var sAsync: Deferred<HomeListResponse>? = null

    /**
     * 收藏 async
     */
    private var collectArticleAsync: Deferred<HomeListResponse>? = null

    override fun getSearchList(onSearchListListener: SearchPresenter, page: Int, k: String) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onSearchListListener.getSearchListFailed(it.toString())
            }) {
                sAsync?.cancelByActivite()
                sAsync = RetrofitHelper.retrofitService.getSearchList(page, k)
                val result = sAsync?.await()
                result ?: let {
                    onSearchListListener.getSearchListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onSearchListListener.getSearchListSuccess(result) }
            }
        }
    }

    /**
     * 收藏或取消文章
     */
    override fun collectArticle(
            onCollectArticleListener: SearchPresenter.OnCollectArticleListener,
            id: Int,
            isAdd: Boolean,
            isOfficial: Boolean,
            title: String,
            author: String,
            link: String) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onCollectArticleListener.collectArticleFailed(it.toString(), isAdd)
            }) {
                collectArticleAsync?.cancelByActivite()
                if (isAdd) {
                    if (isOfficial) {
                        //收藏站内
                        collectArticleAsync = RetrofitHelper.retrofitService.addColltectIn(id)
                    } else {
                        //收藏站外
                        collectArticleAsync = RetrofitHelper.retrofitService.addCollectOut(title,author,link)
                    }
                } else {
                    //取消收藏
                    collectArticleAsync = RetrofitHelper.retrofitService.removeCollect(id)
                }
                val result = collectArticleAsync?.await()
                result ?: let {
                    onCollectArticleListener.collectArticleFailed(Constant.RESULT_NULL,isAdd)
                    return@async
                }
                result?.let { onCollectArticleListener.collectArticleSuccess(result,isAdd) }

            }
        }
    }

    override fun cancelRequest() {
        sAsync?.cancelByActivite()
        collectArticleAsync?.cancelByActivite()
    }

}