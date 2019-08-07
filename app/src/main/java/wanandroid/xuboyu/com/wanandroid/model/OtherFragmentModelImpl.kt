package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.HotSearchResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.OtherFragmentPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：其他模块数据操作类
 * author: XuBoYu
 * time: 2019/8/6
 **/
class OtherFragmentModelImpl : OtherFragmentModel {

    private var hsAsync: Deferred<HotSearchResponse>? = null

    override fun getHSList(otherFragmentPresenter: OtherFragmentPresenter) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                    otherFragmentPresenter.getHSListFailed(it.toString())
            }) {
                hsAsync?.cancelByActivite()
                hsAsync = RetrofitHelper.retrofitService.getHotSearch()
                val result = hsAsync?.await()
                result ?: let {
                    otherFragmentPresenter.getHSListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { otherFragmentPresenter.getHSListSuccess(result) }
            }
        }
    }

    override fun cancelHSRequest() {
        hsAsync?.cancelByActivite()
    }

}