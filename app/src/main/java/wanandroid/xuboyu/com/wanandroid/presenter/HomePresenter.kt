package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse

interface HomePresenter {

    /**
     * login 监听接口
     */
    interface OnLoginListener {
        /**
         * login
         * @param username username
         * @param password password
         */
        fun loginWanAndroid(username: String, password: String)

        /**
         * login success
         * @param result LoginResponse
         */
        fun loginSuccess(result: LoginResponse)

        /**
         * login failed
         * @param errorMessage error message
         */
        fun loginFailed(errorMessage: String?)
    }

    /**
     * register 监听接口
     */
    interface OnRegisterListener {
        /**
         * register
         * @param username username
         * @param password password
         * @param repassword repassword
         */
        fun registerWanAndroid(username: String, password: String, repassword: String)

        /**
         * register success
         * @param result LoginResponse
         */
        fun registerSuccess(result: LoginResponse)

        /**
         * register failed
         * @param errorMessage error message
         */
        fun registerFailed(errorMessage: String?)
    }

    /**
     * 获取首页文章列表
     */
    interface OnHomeListListener {

        /**
         * 获取文章列表
         * @param page page
         */
        fun getHomeList(page: Int = 0)

        /**
         * 获取成功
         * @param result result
         */
        fun getHomeListSuccess(result: HomeListResponse)

        /**
         * 获取失败
         * @param errorMessage error message
         */
        fun getHomeListFailed(errorMessage: String?)
    }

    /**
     * 获取首页轮播图
     */
    interface OnBannerListener {
        /**
         * 获取轮播banner
         */
        fun getBanner()

        /**
         * 获取成功
         * @param result BannerResponse
         */
        fun getBannerSuccess(result: BannerResponse)

        /**
         * 获取失败
         * @param errorMessage error message
         */
        fun getBannerFailed(errorMessage: String?)
    }

    /**
     * 收藏文章
     */
    interface OnCollectArticleListener {
        /**
         *  添加或删除
         *  @param id article id
         *  @param isAdd true 添加, false 删除
         *  @param isOfficial true 站内， false 站外
         *  @param title 站外文章标题
         *  @param author 站外文章作者
         *  @param link 站外文章链接
         */
        fun collectArticle(id: Int,
                           isAdd: Boolean,
                           isOfficial: Boolean,
                           title: String,
                           author: String,
                           link: String)


        /**
         * 收藏添加成功
         * @param result HomeListResponse
         * @param isAdd true add, false remove
         */
        fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean)

        /**
         * 收藏添加失败
         * @param errorMessage error message
         * @param isAdd true add, false remove
         */
        fun collectArticleFailed(errorMessage: String?, isAdd: Boolean)
    }

    /**
     * 获取知识体系接口
     */
    interface OnTreeListListener {

        /**
         * 获取体系列表
         */
        fun getTreeList()

        /**
         * 获取成功
         * @param result result
         */
        fun getTreeListSuccess(result: TreeListResponse)

        /**
         * 获取失败
         * @param errorMessage error message
         */
        fun getTreeListFailed(errorMessage: String?)
    }

}