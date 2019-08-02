package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse
import wanandroid.xuboyu.com.wanandroid.model.CollectWebListModel
import wanandroid.xuboyu.com.wanandroid.model.CollectWebListModelImpl
import wanandroid.xuboyu.com.wanandroid.view.CollectWebListView

/**
 * use：收藏网址列表中间层操作
 * author: XuBoYu
 * time: 2019/8/1
 **/
class CollectWebListPresenterImpl (private val collectWebListView: CollectWebListView) :
        CollectWebListPresenter, CollectWebListPresenter.OnCollectWebListListener {

    private val collectWebListModelImpl: CollectWebListModel = CollectWebListModelImpl()

    /**
     * 调用访问获取数据
     */
    override fun getCollectWebList() {
        collectWebListModelImpl.getCollectWebList(this)
    }

    /**
     * 成功回调通知UI层
     */
    override fun getCollectWebListSuccess(result: CollectWebListResponse) {
        if (result.errorCode != 0) {
            collectWebListView.getCollectWebListFailed(result.errorMsg)
            return
        }
        if (result == null) {
            collectWebListView.getCollectWebListZero()
        }
        collectWebListView.getCollectWebListSuccess(result)
    }

    /**
     * 失败回调通知UI层
     */
    override fun getCollectWebListFailed(errorMessage: String?) {
        collectWebListView.getCollectWebListFailed(errorMessage)
    }

    /**
     * 取消请求
     */
    fun cancelRequest() {
        collectWebListModelImpl.cancelCollectWebListRequest()
    }

}