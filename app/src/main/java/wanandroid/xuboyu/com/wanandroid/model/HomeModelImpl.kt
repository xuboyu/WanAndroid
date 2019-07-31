package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.HomePresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch
import kotlin.math.log

/**
 * use：首页Model实现类
 * author: XuBoYu
 * time: 2019/7/2
 **/
class HomeModelImpl : HomeModel, CollectArticleModel{

    /**
     * 登录 async
     */
    private var loginAsync: Deferred<LoginResponse>? = null

    /**
     * 注册 async
     */
    private var registerAsync: Deferred<LoginResponse>? = null

    /**
     * 首页获取文章列表 async
     */
    private var homeListAsync: Deferred<HomeListResponse>? = null

    /**
     * 获取首页轮播图 async
     */
    private var homeBannerAsync: Deferred<BannerResponse>? = null

    /**
     * 收藏 async
     */
    private var collectArticleAsync: Deferred<HomeListResponse>? = null

    /**
     * 获取知识体系 async
     */
    private var treeListAsync: Deferred<TreeListResponse>? = null

    /**
     * 登录
     */
    override fun loginWanAndroid(
            onLoginListener: HomePresenter.OnLoginListener,
            username: String,
            password: String) {

            async(UI) {
                tryCatch({
                    //catch区块
                    it.printStackTrace()
                    onLoginListener.loginFailed(it.toString())
                }) {
                    //try区块
                    loginAsync?.cancelByActivite()//存活取消
                    loginAsync = RetrofitHelper.retrofitService.loginWanAndroid(username, password)
                    val result = loginAsync?.await()
                    result ?: let {
                        onLoginListener.loginFailed(Constant.RESULT_NULL)
                        return@async
                    }
                    result?.let { onLoginListener.loginSuccess(it) }
                }
            }

    }

    /**
     * 取消登录
     */
    override fun cancelLoginRequest() {
        loginAsync?.cancelByActivite()
    }

    /**
     * 注册
     */
    override fun registerWanAndroid(
            onRegisterListener: HomePresenter.OnRegisterListener,
            username: String,
            password: String,
            repassword: String) {

        async(UI) {
            tryCatch ({
                //catch区块
                it.printStackTrace()
                onRegisterListener.registerFailed(it.toString())
            }) {
                //try区块
                registerAsync?.cancelByActivite()//存活取消
                registerAsync = RetrofitHelper.retrofitService.registerWanAndroid(username,password,repassword)
                val result = registerAsync?.await()
                result ?: let {
                    onRegisterListener.registerFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onRegisterListener.registerSuccess(it) }
            }
        }
    }

    /**
     * 取消注册
     */
    override fun cancelRegisterRequest() {
        registerAsync?.cancelByActivite()
    }

    /**
     * 获取首页
     */
    override fun getHomeList(
            onHomeListListener: HomePresenter.OnHomeListListener,
            page: Int) {

        async(UI) {
            tryCatch({
                //catch区块
                it.printStackTrace()
                onHomeListListener.getHomeListFailed(it.toString())
            }) {
                //try区块
                homeListAsync?.cancelByActivite()
                homeListAsync = RetrofitHelper.retrofitService.getHomeList(page)
                val result = homeListAsync?.await()
                result ?: let {
                    onHomeListListener.getHomeListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onHomeListListener.getHomeListSuccess(it) }
            }
        }
    }

    /**
     * 取消获取首页
     */
    override fun cancelHomeListRequest() {
        homeListAsync?.cancelByActivite()
    }

    /**
     * 获取banner
     */
    override fun getBanner(onBannerListener: HomePresenter.OnBannerListener) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onBannerListener.getBannerFailed(it.toString())
            }) {
                homeBannerAsync?.cancelByActivite()
                homeBannerAsync = RetrofitHelper.retrofitService.getBanner()
                val result = homeBannerAsync?.await()
                result ?: let {
                    onBannerListener.getBannerFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { onBannerListener.getBannerSuccess(result) }
            }
        }
    }

    /**
     * 取消获取banner
     */
    override fun cancelBannerRequest() {
        homeBannerAsync?.cancelByActivite()
    }

    /**
     * 收藏或取消文章
     */
    override fun collectArticle(
            onCollectArticleListener: HomePresenter.OnCollectArticleListener,
            id: Int,
            isAdd: Boolean,
            isOfficial: Boolean,
            title: String,
            author: String,
            link: String) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                onCollectArticleListener.collectArticleFailed(it.toString(), isAdd)
            }) {
                collectArticleAsync?.cancelByActivite()
                if (isAdd) {
                    if (isOfficial) {
                        //收藏站内
                        collectArticleAsync = RetrofitHelper.retrofitService.addColltectIn(id)
                    } else {
                        //收藏站外
                        collectArticleAsync = RetrofitHelper.retrofitService.addCollectOut(title,author,link)
                    }
                } else {
                    //取消收藏
                    collectArticleAsync = RetrofitHelper.retrofitService.removeCollect(id)
                }
                val result = collectArticleAsync?.await()
                result ?: let {
                    onCollectArticleListener.collectArticleFailed(Constant.RESULT_NULL,isAdd)
                    return@async
                }
                result?.let { onCollectArticleListener.collectArticleSuccess(result,isAdd) }

                }
            }
        }

    /**
     * 取消收藏
     */
    override fun cancelCollectRequest() {
        collectArticleAsync?.cancelByActivite()
    }

    /**
     * 知识体系获取
     */
    override fun getTreeList(onTreeListListener: HomePresenter.OnTreeListListener) {
        async(UI) {
            tryCatch ({
                it.printStackTrace()
                onTreeListListener.getTreeListFailed(it.toString())
            }) {
                treeListAsync?.cancelByActivite()
                treeListAsync = RetrofitHelper.retrofitService.getTreeList()
                var result = treeListAsync?.await()
                result ?: let {
                    onTreeListListener.getTreeListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let {
                    onTreeListListener.getTreeListSuccess(result)
                }
            }
        }
    }

    /**
     * 取消知识体系请求
     */
    override fun cancelTreeRequest() {
        treeListAsync?.cancelByActivite()
    }
}