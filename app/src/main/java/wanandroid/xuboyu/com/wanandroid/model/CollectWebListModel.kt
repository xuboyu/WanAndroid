package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.CollectWebListPresenter

/**
 * use：收藏网址列表数据层抽离接口
 * author: XuBoYu
 * time: 2019/8/1
 **/
interface CollectWebListModel {

    /**
     * 获取接口调用
     */
    fun getCollectWebList(onCollectWebListListener: CollectWebListPresenter.OnCollectWebListListener)

    /**
     * 取消请求
     */
    fun cancelCollectWebListRequest()
}