package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
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

    private var atAsync: Deferred<TodoAddResponse>? = null

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

    override fun cancelRequest() {
        atAsync?.cancelByActivite()
    }

}