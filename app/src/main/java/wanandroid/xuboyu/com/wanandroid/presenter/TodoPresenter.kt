package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse

/**
 * use：TODO中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/28
 **/
interface TodoPresenter {

    /**
     * 获取TODO列表
     */
    interface getTodo {
        /**
         * 获取调用
         */
        fun getTodo(page: Int, status: Int, type: Int, priority: Int, orderby: Int)

        /**
         * 获取成功
         */
        fun getTodoSuccess(result: TodoListResponse)

        /**
         * 获取失败
         */
        fun getTodoFaied(errorMessage: String?)
    }

    /**
     * 新增TODO
     */
    interface addTodo {
        /**
         * 新增调用
         */
        fun addTodo(title: String, content: String, date: String, type: Int, priority: Int)

        /**
         * 新增成功
         */
        fun addSuccess(result: TodoAddResponse)

        /**
         * 新增失败
         */
        fun addFailed(errorMessage: String?)
    }

}