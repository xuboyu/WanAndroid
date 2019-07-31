package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.ArticleListResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.TypeArticlePresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：单个列表接口实现操作
 * author: XuBoYu
 * time: 2019/7/31
 **/

class TypeArticleModelImpl : TypeArticleModel {

    /**
     * Type Article list list async
     */
    private var typeArticleListAsync: Deferred<ArticleListResponse>? = null

    /**
     * get Type Article list
     * @param onTypeArticleListListener TypeArticlePresenter.OnTypeArticleListListener
     * @param page page
     * @param cid cid
     */
    override fun getTypeArticleList(
            onTypeArticleListListener: TypeArticlePresenter.OnTypeArticleListListener,
            page: Int,
            cid: Int
    ) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onTypeArticleListListener.getTypeArticleListFailed(it.toString())
            }) {
                typeArticleListAsync?.cancelByActivite()
                typeArticleListAsync = RetrofitHelper.retrofitService.getArticleList(page, cid)
                val result = typeArticleListAsync?.await()
                result ?: let {
                    onTypeArticleListListener.getTypeArticleListFailed(Constant.RESULT_NULL)
                    return@async
                }
                if (result != null) {
                    onTypeArticleListListener.getTypeArticleListSuccess(result)
                }
            }
        }
    }

    /**
     * cancel request
     */
    override fun cancelRequest() {
        typeArticleListAsync?.cancelByActivite()
    }
}
