package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
import wanandroid.xuboyu.com.wanandroid.bean.BaseTodoResponse
import wanandroid.xuboyu.com.wanandroid.model.TodoModel
import wanandroid.xuboyu.com.wanandroid.model.TodoModelImpl
import wanandroid.xuboyu.com.wanandroid.view.TodoView

/**
 * use：TODO中间层实现类
 * author: XuBoYu
 * time: 2019/8/28
 **/
class TodoPresenterImpl(private val todoView: TodoView) :
        TodoPresenter.addTodo,TodoPresenter.getTodo, TodoPresenter.updateTodo,
        TodoPresenter.deleteTodo{

    private val todoModelmpl: TodoModel = TodoModelImpl()

    override fun getTodo(page: Int, status: Int, type: Int, priority: Int, orderby: Int) {
        todoModelmpl.getTodo(this,page,status,type,priority,orderby)
    }

    override fun geTodoDefault(page: Int, status: Int) {
        todoModelmpl.getToDoDefault(this, page, status)
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

    override fun updateTodo(id: Int, title: String, content: String, date: String, type: Int, priority: Int) {
        todoModelmpl.updateTodo(this,id,title,content,date,type,priority)
    }

    override fun updateStatusTodo(id: Int, status: Int) {
        todoModelmpl.updateStatusTodo(this, id, status)
    }

    override fun updateSuccess(result: BaseTodoResponse) {
        if (result.errorCode != 0) {
            todoView.updateFailed(result.errorMsg)
        } else {
            todoView.updateSuccess(result)
        }
    }

    override fun updateFailed(errorMessage: String?) {
        todoView.updateFailed(errorMessage)
    }

    override fun deleteTodo(id: Int) {
        todoModelmpl.deleteTodo(this,id)
    }

    override fun deleteSuccess(result: BaseTodoResponse) {
        if (result.errorCode != 0) {
            todoView.deleteFailed(result.errorMsg)
        } else {
            todoView.deleteSuccess(result)
        }
    }

    override fun deleteFailed(errorMessage: String?) {
        todoView.deleteFailed(errorMessage)
    }

    fun cancelRequest() {
        todoModelmpl.cancelRequest()
    }

}