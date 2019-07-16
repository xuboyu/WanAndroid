package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.model.HomeModel
import wanandroid.xuboyu.com.wanandroid.model.HomeModelImpl
import wanandroid.xuboyu.com.wanandroid.view.HomeFragmentView

/**
 * use：首页fragment操作实现
 * author: XuBoYu
 * time: 2019/4/19
 **/
class HomeFragmentImpl(
        private val homeFragmentView: HomeFragmentView
) : HomePresenter.OnHomeListListener, HomePresenter.OnBannerListener {

    private val homeModel: HomeModel = HomeModelImpl()

    override fun getHomeList(page: Int) {
        homeModel.getHomeList(this,page)
    }

    override fun getHomeListSuccess(result: HomeListResponse) {
        if (result.errorCode != 0) {
            homeFragmentView.getHomeListFailed(result.errorMsg)
            return
        }
        // 列表总数
        val total = result.data.total
        if (total == 0) {
            homeFragmentView.getHomeListZero()
            return
        }
        // 当第一页小于一页总数时
        if (total < result.data.size) {
            homeFragmentView.getHomeListSmall(result)
            return
        }
        homeFragmentView.getHomeListSuccess(result)
    }

    override fun getHomeListFailed(errorMessage: String?) {
        homeFragmentView.getHomeListFailed(errorMessage)
    }

    override fun getBanner() {
        homeModel.getBanner(this)
    }

    override fun getBannerSuccess(result: BannerResponse) {
        if (result.errorCode != 0) {
            homeFragmentView.getBannerFailed(result.errorMsg)
            return
        }
        result.data ?: let {
            homeFragmentView.getBannerZero()
            return
        }
        homeFragmentView.getBannerSuccess(result)
    }

    override fun getBannerFailed(errorMessage: String?) {
        homeFragmentView.getBannerFailed(errorMessage)
    }

    /**
     * cancel request
     */
    fun cancelRequest() {
        homeModel.cancelBannerRequest()
        homeModel.cancelHomeListRequest()
//        collectArticleModel.cancelCollectRequest()
    }
}