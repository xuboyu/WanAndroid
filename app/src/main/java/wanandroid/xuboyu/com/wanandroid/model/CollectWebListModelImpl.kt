package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.CollectWebListPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：收藏网址操作实现
 * author: XuBoYu
 * time: 2019/8/1
 **/
class CollectWebListModelImpl : CollectWebListModel {

    /**
     * 请求收藏async
     */
    private var cwAsync: Deferred<CollectWebListResponse>? = null

    override fun getCollectWebList(onCollectWebListListener: CollectWebListPresenter.OnCollectWebListListener) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onCollectWebListListener.getCollectWebListFailed(it.toString())
            }) {
                cwAsync?.cancelByActivite()
                cwAsync = RetrofitHelper.retrofitService.getCollectWebList()
                val result = cwAsync?.await()
                result ?: let {
                    onCollectWebListListener.getCollectWebListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onCollectWebListListener.getCollectWebListSuccess(result) }
            }
        }
    }

    override fun cancelCollectWebListRequest() {
        cwAsync?.cancelByActivite()
    }

}