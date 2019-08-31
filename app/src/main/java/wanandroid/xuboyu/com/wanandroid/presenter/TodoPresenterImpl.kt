package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
import wanandroid.xuboyu.com.wanandroid.model.TodoModel
import wanandroid.xuboyu.com.wanandroid.model.TodoModelImpl
import wanandroid.xuboyu.com.wanandroid.view.TodoView

/**
 * use：TODO中间层实现类
 * author: XuBoYu
 * time: 2019/8/28
 **/
class TodoPresenterImpl(private val todoView: TodoView) :
        TodoPresenter.addTodo,TodoPresenter.getTodo {

    private val todoModelmpl: TodoModel = TodoModelImpl()

    override fun getTodo(page: Int, status: Int, type: Int, priority: Int, orderby: Int) {
        todoModelmpl.getTodo(this,page,status,type,priority,orderby)
    }

    override fun getTodoSuccess(result: TodoListResponse) {
        if (result.errorCode != 0) {
            todoView.getTodoListFailed(result.errorMsg)
        }
        //列表总数
        val total = result.data.total
        if (total == 0) {
            todoView.getTodoListZero()
            return
        }
        todoView.getTodoListSuccess(result)
    }

    override fun getTodoFaied(errorMessage: String?) {
        todoView.getTodoListFailed(errorMessage)
    }

    override fun addTodo(title: String, content: String, date: String, type: Int, priority: Int) {
        todoModelmpl.addTodo(this,title,content,date,type,priority)
    }

    override fun addSuccess(result: TodoAddResponse) {
        if (result.errorCode != 0) {
            todoView.addFailed(result.errorMsg)
        } else {
            todoView.addSuccess(result)
        }
    }

    override fun addFailed(errorMessage: String?) {
        todoView.addFailed(errorMessage)
    }

    fun cancelRequest() {
        todoModelmpl.cancelRequest()
    }

}