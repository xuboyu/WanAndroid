package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.CollectWorkListPresenter

/**
 * use：个人收藏列表操作类抽离接口
 * author: XuBoYu
 * time: 2019/8/5
 **/
interface CollectWorkListModel {

    /**
     * 获取接口调用
     */
    fun getCollectWorkList(onCollectWorkListListener: CollectWorkListPresenter.OnCollectWorkListListener, page: Int)

    /**
     * 删除接口调用
     */
    fun deleteWork(onDeleteWorkListener: CollectWorkListPresenter.OnDeleteWorkListener, id: Int, originId: Int)

    /**
     * 取消请求
     */
    fun cancelCollectWorkListRequest()
}