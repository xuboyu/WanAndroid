package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：搜索列表中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/7
 **/
interface SearchPresenter {

    fun getSearchList(page: Int = 0, k: String)

    /**
     * 获取搜索结果列表成功
     */
    fun getSearchListSuccess(result: HomeListResponse)

    /**
     * 获取搜索结果列表失败
     */
    fun getSearchListFailed(errorMessage: String?)

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
}