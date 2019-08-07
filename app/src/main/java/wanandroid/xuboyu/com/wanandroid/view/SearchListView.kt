package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：搜索列表界面UI抽离接口
 * author: XuBoYu
 * time: 2019/8/7
 **/
interface SearchListView {
    /**
     * 获取搜索结果列表失败
     */
    fun getSearchListSuccess(result: HomeListResponse)

    /**
     * 获取搜索结果列表失败
     */
    fun getSearchListFailed(errorMessage: String?)

    /**
     * 获取搜索结果列表为空
     */
    fun getSearchListZero()

    /**
     * 获取搜索结果列表小于20
     */
    fun getSearchListSmall(result: HomeListResponse)

    /**
     * 收藏添加成功回调接口
     * @param result HomeListResponse
     * @param isAdd true add, false remove
     */
    fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean)

    /**
     * 收藏添加失败回调接口
     * @param errorMessage error message
     * @param isAdd true add, false remove
     */
    fun collectArticleFailed(errorMessage: String?, isAdd: Boolean)
}