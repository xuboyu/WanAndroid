package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.activity_todo.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.utils.DensityUtil
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_addtodo.*
import kotlinx.android.synthetic.main.activity_todo.add_todo
import wanandroid.xuboyu.com.wanandroid.bean.TodoAddResponse
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse
import wanandroid.xuboyu.com.wanandroid.presenter.TodoPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.utils.MyDialog
import wanandroid.xuboyu.com.wanandroid.view.TodoView
import java.text.SimpleDateFormat
import java.util.*

/**
 * use：代办事项界面
 * author: XuBoYu
 * time: 2019/8/27
 **/
class ToDoActivity : BaseActivity(), TodoView {

    private val todoPresenterImpl: TodoPresenterImpl by lazy {
        TodoPresenterImpl(this)
    }

    var button1Dialog: MyDialog? = null

    override fun setLayoutId(): Int = R.layout.activity_todo

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun cancelRequest() {
        todoPresenterImpl.cancelRequest()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.run {
            title = getString(R.string.todo_text)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        //获取列表数据
        todoPresenterImpl.getTodo(1,0,1,1,1)
        //弹窗布局初始化
        initDialog()
        add_todo.setOnClickListener(onClickListener)

    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.add_todo -> {
                button1Dialog!!.show()
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //返回 back
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getTodoListSuccess(result: TodoListResponse) {
        toast("加载成功")
    }

    override fun getTodoListFailed(errorMsg: String?) {
        toast("加载失败")
    }

    override fun getTodoListZero() {
        toast("列表长度为0")
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
        toast("新增成功")
    }

    override fun addFailed(errorMsg: String?) {
        toast("新增失败")
    }

    /**
     * 弹窗调用
     */
    private fun initDialog() {
        //弹窗配置
        button1Dialog = MyDialog(this,R.style.BottomDialogStyle)
        var view: View = LayoutInflater.from(this).inflate(R.layout.activity_addtodo,null)
        button1Dialog!!.setContentView(view)
        var layoutParams: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = resources.displayMetrics.widthPixels
        view.layoutParams = layoutParams
        //提交按钮
        var type = 1 //默认类型工作 (工作：1，生活：2，娱乐：3）
        var priority = 2 //默认优先级一般 （重要：1，一般：2）
        button1Dialog!!.add_todo_t.setOnClickListener({
            if (button1Dialog!!.title_edit.text.toString().equals("")) {
                toast(getString(R.string.title_tip))
            } else if (button1Dialog!!.content_edit.text.toString().equals("")) {
                toast(getString(R.string.content_tip))
            } else {
                todoPresenterImpl.addTodo(button1Dialog!!.title_edit.text.toString(),
                        button1Dialog!!.content_edit.text.toString(), getNowData(), type, priority)
            }
        })
        button1Dialog!!.todo_time.setText(getNowData())
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
                    toast(getString(R.string.last_one_type))
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
                    toast(getString(R.string.last_one_type))
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
                    toast(getString(R.string.last_one_type))
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
                    toast(getString(R.string.last_ont_p))
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
                    toast(getString(R.string.last_ont_p))
                }
            }
        }
    }

    /**
     * 获取当天时间
     * 格式 yyyy-MM-dd
     */
    private fun getNowData(): String {
        var data : Date = Date()
        var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var sim : String = dateFormat.format(data)
        return sim
    }

}