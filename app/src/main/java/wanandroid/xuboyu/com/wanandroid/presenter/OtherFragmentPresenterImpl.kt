package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.HotSearchResponse
import wanandroid.xuboyu.com.wanandroid.model.OtherFragmentModel
import wanandroid.xuboyu.com.wanandroid.model.OtherFragmentModelImpl
import wanandroid.xuboyu.com.wanandroid.view.OhterFragmentView

/**
 * use：其他模块中间层实现
 * author: XuBoYu
 * time: 2019/8/6
 **/
class OtherFragmentPresenterImpl(private val otherFragmentView: OhterFragmentView) : OtherFragmentPresenter {

    private val otherFragmentModel: OtherFragmentModel = OtherFragmentModelImpl()

    override fun getHSList() {
        otherFragmentModel.getHSList(this)
    }

    override fun getHSListSuccess(result: HotSearchResponse) {
        if (result.errorCode != 0) {
            otherFragmentView.getHSListFailed(result.errorMsg)
            return
        }
        if (result.data == null) {
            otherFragmentView.getHSListZero()
            return
        }
        if (result.data?.size == 0) {
            otherFragmentView.getHSListZero()
            return
        }
        otherFragmentView.getHSListSuccess(result)
    }

    override fun getHSListFailed(errorMessage: String?) {
        otherFragmentView.getHSListFailed(errorMessage)
    }

    /**
     * 取消请求
     */
    fun cancelRequest() {
        otherFragmentModel.cancelHSRequest()
    }

}