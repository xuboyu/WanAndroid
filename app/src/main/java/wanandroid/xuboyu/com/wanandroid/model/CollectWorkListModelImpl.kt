package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.CollectWorkListPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：个人收藏网址界面数据类实现
 * author: XuBoYu
 * time: 2019/8/5
 **/
class CollectWorkListModelImpl : CollectWorkListModel {

    private var cwAsync: Deferred<HomeListResponse>? = null

    private var getListAsync: Deferred<HomeListResponse>? = null

    override fun getCollectWorkList(onCollectWorkListListener: CollectWorkListPresenter.OnCollectWorkListListener, page: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onCollectWorkListListener.getCollectWorkListFailed(it.toString())
            }) {
                getListAsync?.cancelByActivite()
                getListAsync = RetrofitHelper.retrofitService.getLikeList(page)
                val result = getListAsync?.await()
                result ?: let {
                    onCollectWorkListListener.getCollectWorkListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onCollectWorkListListener.getCollectWorkListSuccess(result) }
            }
        }
    }

    override fun deleteWork(onDeleteWorkListener: CollectWorkListPresenter.OnDeleteWorkListener, id: Int, originId: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onDeleteWorkListener.deleteWorkFialed(it.toString())
            }) {
                cwAsync?.cancelByActivite()
                cwAsync = RetrofitHelper.retrofitService.removeMyCollect(id,originId)
                val result = cwAsync?.await()
                result ?: let {
                    onDeleteWorkListener.deleteWorkFialed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onDeleteWorkListener.deleteWorkSuccess(result) }
            }
        }
    }

    override fun cancelCollectWorkListRequest() {
        getListAsync?.cancelByActivite()
        cwAsync?.cancelByActivite()
    }

}