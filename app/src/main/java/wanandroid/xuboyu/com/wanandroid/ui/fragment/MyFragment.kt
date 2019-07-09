package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_my.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.ui.activity.LoginActivity

/**
 * use：用户界面
 * author: XuBoYu
 * time: 2019/7/5
 **/
class MyFragment: BaseFragment() {

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
        nickName.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->
        when(view.id) {
            R.id.nickName -> {
                Intent(this.activity,LoginActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
    }
}