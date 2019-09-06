package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
import wanandroid.xuboyu.com.wanandroid.bean.BaseTodoResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.TodoPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：TODO数据层实现类
 * author: XuBoYu
 * time: 2019/8/28
 **/
class TodoModelImpl : TodoModel {

    private var gTAsync: Deferred<TodoListResponse>? = null

    private var gdAsunc: Deferred<TodoListResponse>? = null

    private var atAsync: Deferred<TodoAddResponse>? = null

    private var uAsync: Deferred<BaseTodoResponse>? = null

    private var usAsync: Deferred<BaseTodoResponse>? = null

    private var dAsync: Deferred<BaseTodoResponse>? = null

    override fun getTodo(todoPresenter: TodoPresenter.getTodo, page: Int, status: Int, type: Int, priority: Int, orderby: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                todoPresenter.getTodoFaied(it.toString())
            }) {
                gTAsync?.cancelByActivite()
                gTAsync = RetrofitHelper.retrofitService.getTodoList(page,status,type,priority,orderby)
                val result = gTAsync?.await()
                result ?: let {
                    todoPresenter.getTodoFaied(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { todoPresenter.getTodoSuccess(result) }
            }
        }
    }

    override fun getToDoDefault(todoPresenter: TodoPresenter.getTodo, page: Int, status: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                todoPresenter.getTodoFaied(it.toString())
            }) {
                gdAsunc?.cancelByActivite()
                gdAsunc = RetrofitHelper.retrofitService.getTodoListDefalut(page, status)
                val result = gdAsunc?.await()
                result ?: let {
                    todoPresenter.getTodoFaied(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { todoPresenter.getTodoSuccess(result) }
            }
        }
    }

    override fun addTodo(todoPresenter: TodoPresenter.addTodo, title: String, content: String, date: String, type: Int, priority: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                todoPresenter.addFailed(it.toString())
            }) {
                atAsync?.cancelByActivite()
                atAsync = RetrofitHelper.retrofitService.addTodo(title,content,date,type,priority)
                val result = atAsync?.await()
                result ?: let {
                    todoPresenter.addFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { todoPresenter.addSuccess(result) }
            }
        }
    }

    override fun updateTodo(todoPresenter: TodoPresenter.updateTodo, id: Int, title: String, content: String, date: String, type: Int, priority: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                todoPresenter.updateFailed(it.toString())
            }) {
                uAsync?.cancelByActivite()
                uAsync = RetrofitHelper.retrofitService.updateTodo(id, title, content, date, type, priority)
                val result = uAsync?.await()
                result ?: let {
                    todoPresenter.updateFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { todoPresenter.updateSuccess(result) }
            }
        }
    }

    override fun updateStatusTodo(todoPresenter: TodoPresenter.updateTodo, id: Int, status: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                todoPresenter.updateFailed(it.toString())
            }) {
                usAsync?.cancelByActivite()
                usAsync = RetrofitHelper.retrofitService.updateStatusTodo(id,status)
                val result = usAsync?.await()
                result ?: let {
                    todoPresenter.updateFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { todoPresenter.updateSuccess(result) }
            }
        }
    }

    override fun deleteTodo(todoPresenter: TodoPresenter.deleteTodo, id: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                todoPresenter.deleteFailed(it.toString())
            }) {
                dAsync?.cancelByActivite()
                dAsync = RetrofitHelper.retrofitService.deleteTodo(id)
                val result = dAsync?.await()
                result ?: let {
                    todoPresenter.deleteFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { todoPresenter.deleteSuccess(result) }
            }
        }
    }

    override fun cancelRequest() {
        atAsync?.cancelByActivite()
        gTAsync?.cancelByActivite()
        gdAsunc?.cancelByActivite()
        uAsync?.cancelByActivite()
        usAsync?.cancelByActivite()
        dAsync?.cancelByActivite()
    }

}