package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.model.CollectArticleModel
import wanandroid.xuboyu.com.wanandroid.model.HomeModel
import wanandroid.xuboyu.com.wanandroid.model.HomeModelImpl
import wanandroid.xuboyu.com.wanandroid.view.CollectArticleView
import wanandroid.xuboyu.com.wanandroid.view.HomeFragmentView

/**
 * use：首页fragment操作实现
 * author: XuBoYu
 * time: 2019/4/19
 **/
class HomeFragmentImpl(
        private val homeFragmentView: HomeFragmentView,
        private val collectArticleView: CollectArticleView
) : HomePresenter.OnHomeListListener, HomePresenter.OnBannerListener, HomePresenter.OnCollectArticleListener {

    private val homeModel: HomeModel = HomeModelImpl()
    private val collectArticleModel: CollectArticleModel = HomeModelImpl()

    /**
     * 获取首页列表
     */
    override fun getHomeList(page: Int) {
        homeModel.getHomeList(this,page)
    }

    /**
     * 首页获取成功
     */
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

    /**
     * 首页获取失败
     */
    override fun getHomeListFailed(errorMessage: String?) {
        homeFragmentView.getHomeListFailed(errorMessage)
    }

    /**
     * 获取轮播图
     */
    override fun getBanner() {
        homeModel.getBanner(this)
    }

    /**
     * 轮播图获取成功
     */
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

    /**
     * 轮播图获取失败
     */
    override fun getBannerFailed(errorMessage: String?) {
        homeFragmentView.getBannerFailed(errorMessage)
    }

    /**
     * 添加或取消收藏
     */
    override fun collectArticle(id: Int,
                                isAdd: Boolean,
                                isOfficial: Boolean,
                                title: String,
                                author: String,
                                link: String) {
        collectArticleModel.collectArticle(this,id,isAdd, isOfficial,title,author,link)
    }

    /**
     * 收藏成功
     */
    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        if (result.errorCode != 0) {
            collectArticleView.collectArticleFailed(result.errorMsg, isAdd)
        } else {
            collectArticleView.collectArticleSuccess(result, isAdd)
        }
    }

    /**
     * 收藏失败
     */
    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        collectArticleView.collectArticleFailed(errorMessage, isAdd)
    }

    /**
     * cancel request
     */
    fun cancelRequest() {
        homeModel.cancelBannerRequest()
        homeModel.cancelHomeListRequest()
        collectArticleModel.cancelCollectRequest()
    }
}