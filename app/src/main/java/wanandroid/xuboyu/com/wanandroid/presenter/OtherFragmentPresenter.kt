package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.HotSearchResponse

/**
 * use：其他模块中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/6
 **/
interface OtherFragmentPresenter {

    /**
     * 获取数据
     */
    fun getHSList()

    /**
     * 获取成功
     */
    fun getHSListSuccess(result: HotSearchResponse)

    /**
     * 获取失败
     * @param errorMessage error message
     */
    fun getHSListFailed(errorMessage: String?)
}