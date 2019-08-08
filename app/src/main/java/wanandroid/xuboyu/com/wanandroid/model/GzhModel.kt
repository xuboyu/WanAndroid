package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.GzhPresenter

/**
 * use：公众号列表数据类接口
 * author: XuBoYu
 * time: 2019/8/8
 **/
interface GzhModel {

    /**
     * 获取列表
     */
    fun getGzh(gzhListener: GzhPresenter.OnGzhListener)

    fun getGzhList(gzhListListener: GzhPresenter.OnGzhListListener, id: Int, page: Int)

    /**
     * 取消接口访问
     */
    fun cancelGzhRequest()
}