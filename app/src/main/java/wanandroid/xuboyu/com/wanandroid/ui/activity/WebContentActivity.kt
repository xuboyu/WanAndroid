package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import kotlinx.android.synthetic.main.activity_content.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.getAgentWeb

/**
 * use：网址打开界面
 * author: XuBoYu
 * time: 2019/8/1
 **/
class WebContentActivity: BaseActivity() {

    //获取登录状态
    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    private lateinit var agentWeb: AgentWeb
    private lateinit var name: String
    private lateinit var link: String

    override fun cancelRequest() {

    }

    override fun setLayoutId(): Int = R.layout.activity_content

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.run {
            title = getString(R.string.loading)
            setSupportActionBar(this)
            //给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        intent.extras?.let {
            name = it.getString(Constant.WEB_NAME)
            link = it.getString(Constant.WEB_LINK)
            agentWeb = link.getAgentWeb(
                    this,
                    webContent,
                    LinearLayout.LayoutParams(-1,-1),
                    receivedTitleCallback
            )
        }
    }

    override fun onPause() {
        super.onPause()
        agentWeb.webLifeCycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        agentWeb.webLifeCycle.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        agentWeb.webLifeCycle.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    /**
     * 设置 Web 页面的 title 回调
     */
    private val receivedTitleCallback = ChromeClientCallbackManager.ReceivedTitleCallback {
        _, title -> title?.let {
            toolbar.title = it
        }
    }

}