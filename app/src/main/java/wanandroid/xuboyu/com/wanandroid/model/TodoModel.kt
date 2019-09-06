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
     * 默认参数获取调用
     */
    fun getToDoDefault(todoPresenter: TodoPresenter.getTodo, page: Int, status: Int)

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
     * 更新调用
     */
    fun updateTodo (todoPresenter: TodoPresenter.updateTodo,
                    id: Int,
                    title: String,
                    content: String,
                    date: String,
                    type: Int,
                    priority: Int)

    /**
     * 仅更新状态
     */
    fun updateStatusTodo (todoPresenter: TodoPresenter.updateTodo,
                          id: Int,
                          status: Int)

    /**
     * 删除调用
     */
    fun deleteTodo (todoPresenter: TodoPresenter.deleteTodo, id: Int)

    /**
     * 取消请求
     */
    fun cancelRequest()

}