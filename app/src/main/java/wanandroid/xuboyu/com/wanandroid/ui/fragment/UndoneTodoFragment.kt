package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_addtodo.*
import kotlinx.android.synthetic.main.fragment_undon_todo.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.UndoneTodoListAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.bean.BaseTodoResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
import wanandroid.xuboyu.com.wanandroid.presenter.TodoPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.utils.MyDialog
import wanandroid.xuboyu.com.wanandroid.utils.StickyDecoration
import wanandroid.xuboyu.com.wanandroid.view.TodoView
import java.text.SimpleDateFormat
import java.util.*

/**
 * use：未完成TODO
 * author: XuBoYu
 * time: 2019/9/6
 **/
class UndoneTodoFragment: BaseFragment(), TodoView {

    private val datas = mutableListOf<TodoListResponse.Data.Datas>()

    private val todoPresenterImpl: TodoPresenterImpl by lazy {
        TodoPresenterImpl(this)
    }

    private val todoListAdapter: UndoneTodoListAdapter by lazy {
        UndoneTodoListAdapter(activity,datas)
    }

    var button1Dialog: MyDialog? = null

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
//                todoPresenterImpl.getTodo(page,0,1,1,3)
                todoPresenterImpl.geTodoDefault(page1, 0)
            }, todo_rv)
            onItemClickListener = this@UndoneTodoFragment.onItemClickListener
            onItemChildClickListener = this@UndoneTodoFragment.onItemChildClickListener
            setEmptyView(R.layout.fragment_home_empty)
        }
        //获取列表数据
//        todoPresenterImpl.getTodo(1,0,1,1,3)
        todoPresenterImpl.geTodoDefault(page, 0)
        //弹窗布局初始化
        initDialog()
        add_todo.setOnClickListener(onClickListener)
    }

    /**
     * scroll to top
     */
    fun smoothScrollToPosition() = todo_rv.scrollToPosition(0)

    /**
     * onItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            // 弹窗配置
            button1Dialog = MyDialog(activity,R.style.BottomDialogStyle)
            val view: View = LayoutInflater.from(activity).inflate(R.layout.activity_addtodo,null)
            button1Dialog!!.setContentView(view)
            val layoutParams: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.width = resources.displayMetrics.widthPixels
            view.layoutParams = layoutParams
            // 配置参数
            button1Dialog!!.title_edit.setText(datas[position].title)
            button1Dialog!!.content_edit.setText(datas[position].content)
            button1Dialog!!.add_todo_t.text = "UPDATE"
            button1Dialog!!.todo_time.text = datas[position].dateStr
            // 日期选择框
            button1Dialog!!.todo_time.setOnClickListener {
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(activity,
                        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                            button1Dialog!!.todo_time.text = String.format("%d-%d-%d", year, month + 1, dayOfMonth)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.datePicker.minDate = Date().time
                datePickerDialog.show()
            }
            // 勾选初始化
            when {
                datas[position].type == 1 -> {
                    button1Dialog!!.todo_work.isChecked = true
                    button1Dialog!!.todo_sport.isChecked = false
                    button1Dialog!!.todo_play.isChecked = false
                }
                datas[position].type == 2 -> {
                    button1Dialog!!.todo_work.isChecked = false
                    button1Dialog!!.todo_sport.isChecked = true
                    button1Dialog!!.todo_play.isChecked = false
                }
                datas[position].type == 3 -> {
                    button1Dialog!!.todo_work.isChecked = false
                    button1Dialog!!.todo_sport.isChecked = false
                    button1Dialog!!.todo_play.isChecked = true
                }
            }

            when {
                datas[position].priority == 1 -> {
                    button1Dialog!!.priority_i.isChecked = true
                    button1Dialog!!.priority_s.isChecked = false
                }
                datas[position].priority == 2 -> {
                    button1Dialog!!.priority_i.isChecked = false
                    button1Dialog!!.priority_s.isChecked = true
                }
            }

            var type = datas[position].type //勾选类型 (工作：1，生活：2，娱乐：3）
            var priority = datas[position].priority //勾选优先级（重要：1，一般：2）
            //勾选框
            button1Dialog!!.todo_work.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    type = 1
                    button1Dialog!!.todo_sport.isChecked = false
                    button1Dialog!!.todo_play.isChecked = false
                } else {
                    if (!button1Dialog!!.todo_sport.isChecked && !button1Dialog!!.todo_play.isChecked) {
                        type = 1
//                    button1Dialog!!.todo_work.isChecked = true
                        activity.toast(getString(R.string.last_one_type))
                    }
                }
            }
            button1Dialog!!.todo_sport.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    type = 2
                    button1Dialog!!.todo_work.isChecked = false
                    button1Dialog!!.todo_play.isChecked = false
                } else {
                    if (!button1Dialog!!.todo_work.isChecked && !button1Dialog!!.todo_play.isChecked) {
                        type = 1
                        activity.toast(getString(R.string.last_one_type))
                    }
                }
            }
            button1Dialog!!.todo_play.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    type = 3
                    button1Dialog!!.todo_work.isChecked = false
                    button1Dialog!!.todo_sport.isChecked = false
                } else {
                    if (!button1Dialog!!.todo_work.isChecked && !button1Dialog!!.todo_sport.isChecked) {
                        type = 1
                        activity.toast(getString(R.string.last_one_type))
                    }
                }
            }
            button1Dialog!!.priority_i.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    priority = 1
                    button1Dialog!!.priority_s.isChecked = false
                } else {
                    if(!button1Dialog!!.priority_s.isChecked) {
                        priority = 2
                        activity.toast(getString(R.string.last_ont_p))
                    }
                }
            }
            button1Dialog!!.priority_s.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    priority = 2
                    button1Dialog!!.priority_i.isChecked = false
                } else {
                    if(!button1Dialog!!.priority_i.isChecked) {
                        priority = 2
                        activity.toast(getString(R.string.last_ont_p))
                    }
                }
            }
            button1Dialog!!.add_todo_t.setOnClickListener {
                when {
                    button1Dialog!!.title_edit.text.toString() == "" -> activity.toast(getString(R.string.title_tip))
                    button1Dialog!!.content_edit.text.toString() == "" -> activity.toast(getString(R.string.content_tip))
                    else -> todoPresenterImpl.updateTodo(datas[position].id, button1Dialog!!.title_edit.text.toString(),
                            button1Dialog!!.content_edit.text.toString(), button1Dialog!!.todo_time.text.toString(),
                            type, priority)
                }
            }
            // 弹出弹窗
            button1Dialog!!.show()
        }
    }

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
                            .setMessage("确定删除该条待办事项吗？")
                            .setTitle("删除")
                            .setPositiveButton("确定") { _, _ ->
                                todoPresenterImpl.deleteTodo(data.id)
                    //                                todoListAdapter.remove(position)
                    //                                datas.removeAt(position)
                            }
                            .setNeutralButton("取消", null)
                            .create()
                            .show()
                }
                //仅更新状态
                R.id.update_status_todo -> {
                    AlertDialog.Builder(activity)
                            .setMessage("确定将该条待办事项更新至已完成吗？")
                            .setTitle("事项完成")
                            .setPositiveButton("确定") { _, _ ->
                                todoPresenterImpl.updateStatusTodo(data.id,1)
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
        todoPresenterImpl.geTodoDefault(page, 0)
//        todoPresenterImpl.getTodo(1,0,1,1,3)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.add_todo -> {
                button1Dialog!!.show()
            }
        }
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
        //清空数据
        button1Dialog!!.title_edit.setText("")
        button1Dialog!!.content_edit.setText("")
        button1Dialog!!.todo_work.isChecked = true
        button1Dialog!!.todo_sport.isChecked = false
        button1Dialog!!.todo_play.isChecked = false
        button1Dialog!!.priority_i.isChecked = false
        button1Dialog!!.priority_s.isChecked = true
        button1Dialog!!.dismiss()
        refreshData()
        activity.toast("新增成功")
    }

    override fun addFailed(errorMsg: String?) {
        activity.toast("新增失败")
    }

    override fun updateSuccess(response: BaseTodoResponse) {
        //清空数据
        button1Dialog!!.title_edit.setText("")
        button1Dialog!!.content_edit.setText("")
        button1Dialog!!.todo_work.isChecked = true
        button1Dialog!!.todo_sport.isChecked = false
        button1Dialog!!.todo_play.isChecked = false
        button1Dialog!!.priority_i.isChecked = false
        button1Dialog!!.priority_s.isChecked = true
        button1Dialog!!.dismiss()
        refreshData()
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

    /**
     * 弹窗调用
     */
    private fun initDialog() {
        //弹窗配置
        button1Dialog = MyDialog(activity,R.style.BottomDialogStyle)
        var view: View = LayoutInflater.from(activity).inflate(R.layout.activity_addtodo,null)
        button1Dialog!!.setContentView(view)
        var layoutParams: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = resources.displayMetrics.widthPixels
        view.layoutParams = layoutParams
        //提交按钮
        var type = 1 //默认类型工作 (工作：1，生活：2，娱乐：3）
        var priority = 2 //默认优先级一般 （重要：1，一般：2）
        button1Dialog!!.add_todo_t.setOnClickListener {
            when {
                button1Dialog!!.title_edit.text.toString() == "" -> activity.toast(getString(R.string.title_tip))
                button1Dialog!!.content_edit.text.toString() == "" -> activity.toast(getString(R.string.content_tip))
                else -> todoPresenterImpl.addTodo(button1Dialog!!.title_edit.text.toString(),
                        button1Dialog!!.content_edit.text.toString(), button1Dialog!!.todo_time.text.toString(),
                        type, priority)
            }
        }
        //默认显示当天时间
        button1Dialog!!.todo_time.text = getNowData()
        //日期选择框
        button1Dialog!!.todo_time.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                button1Dialog!!.todo_time.text = String.format("%d-%d-%d", year, month + 1, dayOfMonth)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.datePicker.minDate = Date().time
            datePickerDialog.show()
        }
        //勾选框
        button1Dialog!!.todo_work.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                type = 1
                button1Dialog!!.todo_sport.isChecked = false
                button1Dialog!!.todo_play.isChecked = false
            } else {
                if (!button1Dialog!!.todo_sport.isChecked && !button1Dialog!!.todo_play.isChecked) {
                    type = 1
//                    button1Dialog!!.todo_work.isChecked = true
                    activity.toast(getString(R.string.last_one_type))
                }
            }
        }
        button1Dialog!!.todo_sport.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                type = 2
                button1Dialog!!.todo_work.isChecked = false
                button1Dialog!!.todo_play.isChecked = false
            } else {
                if (!button1Dialog!!.todo_work.isChecked && !button1Dialog!!.todo_play.isChecked) {
                    type = 1
                    activity.toast(getString(R.string.last_one_type))
                }
            }
        }
        button1Dialog!!.todo_play.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                type = 3
                button1Dialog!!.todo_work.isChecked = false
                button1Dialog!!.todo_sport.isChecked = false
            } else {
                if (!button1Dialog!!.todo_work.isChecked && !button1Dialog!!.todo_sport.isChecked) {
                    type = 1
                    activity.toast(getString(R.string.last_one_type))
                }
            }
        }
        button1Dialog!!.priority_i.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                priority = 1
                button1Dialog!!.priority_s.isChecked = false
            } else {
                if(!button1Dialog!!.priority_s.isChecked) {
                    priority = 2
                    activity.toast(getString(R.string.last_ont_p))
                }
            }
        }
        button1Dialog!!.priority_s.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                priority = 2
                button1Dialog!!.priority_i.isChecked = false
            } else {
                if(!button1Dialog!!.priority_i.isChecked) {
                    priority = 2
                    activity.toast(getString(R.string.last_ont_p))
                }
            }
        }
    }

    /**
     * 获取当天时间
     * 格式 yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    private fun getNowData(): String {
        val data : Date = Date()
        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val sim : String = dateFormat.format(data)
        return sim
    }
}