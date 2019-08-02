package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.UseWebListPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：收藏网址实现
 * author: XuBoYu
 * time: 2019/8/2
 **/
class CollectWebModelImpl : CollectWebModel {

    private var cwAsync : Deferred<CollectWebListResponse>? = null

    override fun collectWeb(onCollectWebListener: UseWebListPresenter.OnCollectWebListener, name: String, link: String) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onCollectWebListener.collectWebFailed(it.toString())
            }) {
                cwAsync?.cancelByActivite()
                cwAsync = RetrofitHelper.retrofitService.collectWeb(name,link)
                val result = cwAsync?.await()
                result ?: let {
                    onCollectWebListener.collectWebFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onCollectWebListener.collectWebSuccess(result) }
            }
        }
    }

    override fun cancelCollectRequest() {
        cwAsync?.cancelByActivite()
    }

}