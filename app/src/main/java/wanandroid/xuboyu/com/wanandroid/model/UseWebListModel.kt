package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.UseWebListPresenter

/**
 * use：常用网址列表数据层抽离接口
 * author: XuBoYu
 * time: 2019/8/1
 **/
interface UseWebListModel {
    /**
     * 获取接口调用
     */
    fun getUseWebList(onUseWebListListener: UseWebListPresenter.OnUseWebListListener)

    /**
     * 取消请求
     */
    fun cancelUseWebListRequest()
}