package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_my.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.ui.activity.CollectWebListActivity
import wanandroid.xuboyu.com.wanandroid.ui.activity.CollectWorkListActivity
import wanandroid.xuboyu.com.wanandroid.ui.activity.LoginActivity

/**
 * use：用户界面
 * author: XuBoYu
 * time: 2019/7/5
 **/
class MyFragment: BaseFragment() {

    //获取登录状态
    private var isLogin: Boolean by Preference(Constant.LOGIN_KEY,false)

    //获取账号
    private var userName: String by Preference(Constant.USERNAME_KEY,"")

    private var mainView: View? = null

    override fun cancelRequest() {

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainView ?: let {
            mainView = inflater.inflate(R.layout.fragment_my, container, false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isLogin) {
            logout_r.visibility = View.VISIBLE
            nickName.text = userName
        } else {
            logout_r.visibility = View.GONE
        }

        nickName.setOnClickListener(onClickListener)
        todo_r.setOnClickListener(onClickListener)
        c_web_r.setOnClickListener(onClickListener)
        c_work_r.setOnClickListener(onClickListener)
        logout_r.setOnClickListener(onClickListener)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            Constant.MY_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    nickName.text = data?.getStringExtra(Constant.NICK_NAME_TEXT)
                    logout_r.visibility = View.VISIBLE
                }
            }
        }
    }

    private val onClickListener = View.OnClickListener { view ->
        when(view.id) {

            R.id.nickName -> {
                Intent(this.activity,LoginActivity::class.java).run {
                    startActivityForResult(this,Constant.MY_REQUEST_CODE)
                }
            }

            R.id.todo_r -> {
                if (isLogin) {
                    showToast("开发中~")
                } else {
                    showToast("请先登录")
                }
            }

            R.id.c_web_r -> {
                if (isLogin) {
                    Intent(this.activity,CollectWebListActivity::class.java).run {
                        startActivity(this)
                    }
                } else {
                    showToast("请先登录")
                }
            }

            R.id.c_work_r -> {
                if (isLogin) {
                    Intent(this.activity,CollectWorkListActivity::class.java).run {
                        startActivity(this)
                    }
                } else {
                    showToast("请先登录")
                }
            }

            R.id.logout_r -> {
                AlertDialog.Builder(activity)
                        .setMessage("注销登录会清除本地相关数据，是否注销退出？")
                        .setTitle("退出")
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                            Preference.clear()
                            nickName.text = getString(R.string.not_login)
                            logout_r.visibility = View.GONE
                            showToast("退出成功")
                        })
                        .setNeutralButton("取消", null)
                        .create()
                        .show()
            }
        }
    }

    /**
     * toast fragment
     */
    private fun showToast(content: String) {
        Toast.makeText(this.activity, content, Toast.LENGTH_SHORT).apply {
            Constant.showToast = this
        }.show()
    }
}