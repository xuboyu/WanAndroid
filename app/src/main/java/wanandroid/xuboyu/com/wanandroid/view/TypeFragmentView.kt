package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse

/**
 * use：分类列表UI接口抽离
 * author: XuBoYu
 * time: 2019/7/31
 **/
interface TypeFragmentView {
    /**
     * 获取类别列表成功
     */
    fun getTypeListSuccess(result: TreeListResponse)

    /**
     * 获取类别列表失败
     */
    fun getTypeListFailed(errorMessage: String?)

    /**
     * 列表为空
     */
    fun getTypeListZero()
}