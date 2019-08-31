package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.TodoPresenter

/**
 * use：TODO数据层抽离接口
 * author: XuBoYu
 * time: 2019/8/28
 **/
interface TodoModel {

    /**
     * 获取列表
     */
    fun getTodo (todoPresenter: TodoPresenter.getTodo,
                 page: Int,
                 status: Int,
                 type: Int,
                 priority: Int,
                 orderby: Int)

    /**
     * 调用新增
     */
    fun addTodo (todoPresenter: TodoPresenter.addTodo,
                 title: String,
                 content: String,
                 date: String,
                 type: Int,
                 priority: Int)

    /**
     * 取消请求
     */
    fun cancelRequest()

}