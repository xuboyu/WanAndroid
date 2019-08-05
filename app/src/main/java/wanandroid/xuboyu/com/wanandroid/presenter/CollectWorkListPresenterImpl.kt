package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.model.CollectWorkListModel
import wanandroid.xuboyu.com.wanandroid.model.CollectWorkListModelImpl
import wanandroid.xuboyu.com.wanandroid.view.CollectWorkListView

/**
 * use：收藏文章中间层抽离接口实现类
 * author: XuBoYu
 * time: 2019/8/5
 **/
class CollectWorkListPresenterImpl (private val collectWorkListView: CollectWorkListView) :
        CollectWorkListPresenter, CollectWorkListPresenter.OnCollectWorkListListener,
        CollectWorkListPresenter.OnDeleteWorkListener {

    private val collectWorkListModelImpl: CollectWorkListModel = CollectWorkListModelImpl()

    /**
     * 获取个人收藏文章列表
     */
    override fun getCollectWorkList(page: Int) {
        collectWorkListModelImpl.getCollectWorkList(this,page)
    }

    /**
     * 列表获取成功
     */
    override fun getCollectWorkListSuccess(result: HomeListResponse) {
        if (result.errorCode != 0) {
            collectWorkListView.getCollectWorkListFailed(result.errorMsg)
            return
        }
        // 列表总数
        val total = result.data.total
        if (total == 0) {
            collectWorkListView.getCollectWorkListZero()
            return
        }
        // 当第一页小于一页总数时
        if (total < result.data.size) {
            collectWorkListView.getCollectWorkListSmall(result)
            return
        }
        collectWorkListView.getCollectWorkListSuccess(result)
    }

    /**
     * 列表获取失败
     */
    override fun getCollectWorkListFailed(errorMessage: String?) {
        collectWorkListView.getCollectWorkListFailed(errorMessage)
    }

    /**
     * 取消收藏
     */
    override fun deleteWork(id: Int, originId: Int) {
        collectWorkListModelImpl.deleteWork(this, id, originId)
    }

    /**
     * 取消成功
     */
    override fun deleteWorkSuccess(result: HomeListResponse) {
        if (result.errorCode != 0) {
            collectWorkListView.deleteWorkFailed(result.errorMsg)
        } else {
            collectWorkListView.deleteWorkSuccess(result)
        }
    }

    /**
     * 取消失败
     */
    override fun deleteWorkFialed(errorMessage: String?) {
        collectWorkListView.deleteWorkFailed(errorMessage)
    }

    fun cancelRequest() {
        collectWorkListModelImpl.cancelCollectWorkListRequest()
    }

}