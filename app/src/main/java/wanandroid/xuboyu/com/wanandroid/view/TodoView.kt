package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse

/**
 * use：TODO界面相关UI抽离接口
 * author: XuBoYu
 * time: 2019/8/28
 **/
interface TodoView {

    /**
     * 获取TODO列表成功
     */
    fun getTodoListSuccess(result: TodoListResponse)

    /**
     * 获取TODO列表失败
     */
    fun getTodoListFailed(errorMsg: String?)

    /**
     * TODO列表为空
     */
    fun getTodoListZero()

    /**
     * 新增一条TODO添加成功
     */
    fun addSuccess(result: TodoAddResponse)

    /**
     * 新增一条TODO添加失败
     */
    fun addFailed(errorMsg: String?)


}