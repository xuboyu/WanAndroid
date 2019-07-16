package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：首页界面接口
 * author: XuBoYu
 * time: 2019/7/15
 **/
interface HomeFragmentView {

    /**
     * 首页文章列表获取成功
     */
    fun getHomeListSuccess(result: HomeListResponse)

    /**
     * 首页文章列表获取失败
     */
    fun getHomeListFailed(errorMessage: String?)

    /**
     * 获取list为0
     */
    fun getHomeListZero()

    /**
     * 列表size<20
     */
    fun getHomeListSmall(result: HomeListResponse)

    /**
     * 轮播图获取成功
     * @param result BannerResponse
     */
    fun getBannerSuccess(result: BannerResponse)

    /**
     * 轮播图获取失败
     * @param errorMessage error message
     */
    fun getBannerFailed(errorMessage: String?)

    /**
     * 轮播图列表为空
     */
    fun getBannerZero()
}