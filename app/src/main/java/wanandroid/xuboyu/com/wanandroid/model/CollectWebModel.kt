package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.UseWebListPresenter

/**
 * use：收藏网址抽离接口
 * author: XuBoYu
 * time: 2019/8/2
 **/
interface CollectWebModel {

    /**
     * 收藏网址，无排重
     */
    fun collectWeb(
            onCollectWebListener: UseWebListPresenter.OnCollectWebListener,
            name: String,
            link: String
    )

    /**
     * 取消接口访问
     */
    fun cancelCollectRequest()
}