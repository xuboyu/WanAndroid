package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse

/**
 * use：公众号文章列表UI抽离接口
 * author: XuBoYu
 * time: 2019/8/8
 **/
interface GzhListView {

    /**
     * 成功回调
     * @param result 请求bean
     */
    fun getGzhListSuccess(result: HomeListResponse)

    /**
     * 失败回调
     * @param errorMessage 失败信息
     */
    fun getGzhListFailed(errorMessage: String?)

    /**
     * 获取列表长度为0
     */
    fun getGzhListZero()

    /**
     * 获取长度小于20
     */
    fun getGzhListSmall(result: HomeListResponse)

    /**
     * 删除或收藏成功回调
     * @param result
     */
    fun docWorkSuccess(result: HomeListResponse,isAdd: Boolean)

    /**
     * 删除或收藏失败
     * @param errorMessage
     */
    fun docWorkFailed(errorMessage: String?,isAdd: Boolean)
}