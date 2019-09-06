package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
import wanandroid.xuboyu.com.wanandroid.bean.BaseTodoResponse

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
         * 默认参数调用获取
         */
        fun geTodoDefault(page: Int, status: Int)

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

    /**
     * 更新TODO
     */
    interface updateTodo {
        /**
         * 更新调用
         */
        fun updateTodo(id: Int, title: String, content: String, date: String, type: Int, priority: Int)

        /**
         * 仅更新状态
         */
        fun updateStatusTodo(id: Int, status: Int)

        /**
         * 更新成功
         */
        fun updateSuccess(result: BaseTodoResponse)

        /**
         * 更新失败
         */
        fun updateFailed(errorMessage: String?)
    }

    /**
     * 更新TODO
     */
    interface deleteTodo {
        /**
         * 更新调用
         */
        fun deleteTodo(id: Int)

        /**
         * 更新成功
         */
        fun deleteSuccess(result: BaseTodoResponse)

        /**
         * 更新失败
         */
        fun deleteFailed(errorMessage: String?)
    }

}