package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：收藏事件ui相关接口
 * author: XuBoYu
 * time: 2019/7/29
 **/
interface CollectArticleView{

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