package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_undon_todo.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.DoneTodoListAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.bean.BaseTodoResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
import wanandroid.xuboyu.com.wanandroid.presenter.TodoPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.utils.StickyDecoration
import wanandroid.xuboyu.com.wanandroid.view.TodoView

/**
 * use：已完成TODO界面
 * author: XuBoYu
 * time: 2019/9/6
 **/
class DoneTodoFragment : BaseFragment(), TodoView {
    private val datas = mutableListOf<TodoListResponse.Data.Datas>()

    private val todoPresenterImpl: TodoPresenterImpl by lazy {
        TodoPresenterImpl(this)
    }

    private val todoListAdapter: DoneTodoListAdapter by lazy {
        DoneTodoListAdapter(activity,datas)
    }

    var page : Int = 1

    private var mainView: View? = null

    override fun cancelRequest() {
        todoPresenterImpl.cancelRequest()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainView ?: let {
            mainView = inflater.inflate(R.layout.fragment_undon_todo, container, false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        todo_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        todo_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = todoListAdapter
            addItemDecoration(StickyDecoration(context, object : StickyDecoration.DecorationCallback {
                override fun getData(position: Int): String {
                    return datas[position].dateStr
                }
            }))
        }

        todoListAdapter.run {
            bindToRecyclerView(todo_rv)
            setOnLoadMoreListener({
                val page1 = todoListAdapter.data.size / 20 + page
                todoPresenterImpl.geTodoDefault(page1, 1)
            }, todo_rv)
            onItemChildClickListener = this@DoneTodoFragment.onItemChildClickListener
            setEmptyView(R.layout.fragment_home_empty)
        }
        //获取列表数据
        todoPresenterImpl.geTodoDefault(page, 1)
    }

    /**
     * scroll to top
     */
    fun smoothScrollToPosition() = todo_rv.scrollToPosition(0)


    /**
     * onItemChildClickListener
     */
    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        if (datas.size != 0) {
            val data = datas[position]
            when (view.id) {
                //删除todo
                R.id.delete_todo -> {
                    AlertDialog.Builder(activity)
                            .setMessage("确定删除该条事项吗？")
                            .setTitle("删除")
                            .setPositiveButton("确定") { _, _ ->
                                todoPresenterImpl.deleteTodo(data.id)
                            }
                            .setNeutralButton("取消", null)
                            .create()
                            .show()
                }
            }
        }
    }

    /**
     * RefreshListener 滑动监听
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    /**
     * refresh 数据刷新
     */
    private fun refreshData() {
        todo_swipe.isRefreshing = true
        todoPresenterImpl.geTodoDefault(page, 1)
    }

    override fun getTodoListSuccess(result: TodoListResponse) {
        result.data.datas?.let {
            todoListAdapter.run {
                // 列表总数
                val total = result.data.total
                if (result.data.offset >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }

                if (todo_swipe.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                if (result.data.size >= 20) {
                    setEnableLoadMore(true)
                } else if (result.data.size < 20) {
                    setEnableLoadMore(false)
                }
                activity.toast(getString(R.string.get_data_success))
            }
        }
        todo_swipe.isRefreshing = false
    }

    override fun getTodoListFailed(errorMsg: String?) {
        todoListAdapter.setEnableLoadMore(false)
        todoListAdapter.loadMoreFail()
        errorMsg?.let {
            activity.toast(it)
        } ?: let {
            activity.toast(getString(R.string.get_data_error) + "-" + errorMsg)
        }
        todo_swipe.isRefreshing = false
    }

    override fun getTodoListZero() {
        activity.toast("列表长度为0")
    }

    override fun addSuccess(result: TodoAddResponse) {
        activity.toast("新增成功")
    }

    override fun addFailed(errorMsg: String?) {
        activity.toast("新增失败")
    }

    override fun updateSuccess(response: BaseTodoResponse) {
        activity.toast("更新成功")
    }

    override fun updateFailed(errorMsg: String?) {
        activity.toast("更新失败")
    }

    override fun deleteSuccess(response: BaseTodoResponse) {
        refreshData()
        activity.toast("删除成功")
    }

    override fun deleteFailed(errorMsg: String?) {
        activity.toast("删除失败")
    }

}