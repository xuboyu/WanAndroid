package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.OtherFragmentPresenter

/**
 * use：其他模块数据类抽离接口
 * author: XuBoYu
 * time: 2019/8/6
 **/
interface OtherFragmentModel {

    /**
     * get friend list
     * @param otherFragmentPresenter OtherFragmentPresenter
     */
    fun getHSList(otherFragmentPresenter: OtherFragmentPresenter)

    /**
     * cancel friend list Request
     */
    fun cancelHSRequest()
}