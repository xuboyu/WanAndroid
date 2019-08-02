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
 * use：常用网址实现
 * author: XuBoYu
 * time: 2019/8/1
 **/
class UseWebListModelImpl : UseWebListModel {

    private var uwAsync: Deferred<CollectWebListResponse>? = null

    override fun getUseWebList(onUseWebListListener: UseWebListPresenter.OnUseWebListListener) {
        async(UI) {
            tryCatch ({
                it.printStackTrace()
                onUseWebListListener.getUseWebListFailed(it.toString())
            }) {
                uwAsync?.cancelByActivite()
                uwAsync = RetrofitHelper.retrofitService.getUseWebList()
                val result = uwAsync?.await()
                result ?: let {
                    onUseWebListListener.getUseWebListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onUseWebListListener.getUseWebListSuccess(result) }
            }
        }
    }

    override fun cancelUseWebListRequest() {
        uwAsync?.cancelByActivite()
    }

}