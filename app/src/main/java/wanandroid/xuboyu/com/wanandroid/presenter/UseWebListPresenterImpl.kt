package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse
import wanandroid.xuboyu.com.wanandroid.model.UseWebListModel
import wanandroid.xuboyu.com.wanandroid.model.UseWebListModelImpl
import wanandroid.xuboyu.com.wanandroid.view.UseWebListView

/**
 * use：常用网址中间层抽离接口实现
 * author: XuBoYu
 * time: 2019/8/1
 **/
class UseWebListPresenterImpl (private val useWebListView: UseWebListView) :
        UseWebListPresenter, UseWebListPresenter.OnUseWebListListener {

    private val useWebListModelImpl: UseWebListModel = UseWebListModelImpl()

    override fun getUseWebList() {
        useWebListModelImpl.getUseWebList(this)
    }

    override fun getUseWebListSuccess(result: CollectWebListResponse) {
        if (result.errorCode != 0) {
            useWebListView.getUseWebListFailed(result.errorMsg)
            return
        }
        if (result == null) {
            useWebListView.getUseWebListZero()
        }
        useWebListView.getUseWebListSuccess(result)
    }

    override fun getUseWebListFailed(errorMessage: String?) {
        useWebListView.getUseWebListFailed(errorMessage)
    }

    /**
     * 取消请求
     */
    fun cancelRequest() {

    }
}