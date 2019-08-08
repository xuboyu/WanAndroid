package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.GzhResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.GzhPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：公众号列表数据类实现类
 * author: XuBoYu
 * time: 2019/8/8
 **/
class GzhModelImpl : GzhModel {

    private var gzhAsync: Deferred<GzhResponse>? = null

    private var gzhListAsync: Deferred<HomeListResponse>? = null

    override fun getGzh(gzhListener: GzhPresenter.OnGzhListener) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                gzhListener.getGzhFailed(it.toString())
            }) {
                gzhAsync?.cancelByActivite()
                gzhAsync = RetrofitHelper.retrofitService.getGzh()
                val result = gzhAsync?.await()
                result ?: let {
                    gzhListener.getGzhFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { gzhListener.getGzhSuccess(result) }
            }
        }
    }

    override fun getGzhList(gzhListListener: GzhPresenter.OnGzhListListener, id: Int, page: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                gzhListListener.getGzhListFailed(it.toString())
            }) {
                gzhListAsync?.cancelByActivite()
                gzhListAsync = RetrofitHelper.retrofitService.getGzhList(id,page)
                val result = gzhListAsync?.await()
                result ?: let {
                    gzhListListener.getGzhListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { gzhListListener.getGzhListSuccess(result) }
            }
        }
    }

    override fun cancelGzhRequest() {
        gzhAsync?.cancelByActivite()
        gzhListAsync?.cancelByActivite()
    }

}