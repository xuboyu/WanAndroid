package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.ArticleListResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：收藏文章列表UI抽离接口
 * author: XuBoYu
 * time: 2019/8/5
 **/
interface CollectWorkListView {

    /**
     * 成功回调
     * @param result 请求bean
     */
    fun getCollectWorkListSuccess(result: HomeListResponse)

    /**
     * 失败回调
     * @param errorMessage 失败信息
     */
    fun getCollectWorkListFailed(errorMessage: String?)

    /**
     * 获取列表长度为0
     */
    fun getCollectWorkListZero()

    /**
     * 获取长度小于20
     */
    fun getCollectWorkListSmall(result: HomeListResponse)

    /**
     * 删除收藏成功回调
     * @param result
     */
    fun deleteWorkSuccess(result: HomeListResponse)

    /**
     * 删除失败
     * @param errorMessage
     */
    fun deleteWorkFailed(errorMessage: String?)

}