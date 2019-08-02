package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import kotlinx.android.synthetic.main.activity_content.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.getAgentWeb
import wanandroid.xuboyu.com.wanandroid.presenter.CollectWebListPresenterImpl
import wanandroid.xuboyu.com.wanandroid.presenter.UseWebListPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.CollectWebView

/**
 * use：网址打开界面
 * author: XuBoYu
 * time: 2019/8/1
 **/
class WebContentActivity: BaseActivity(), CollectWebView {

    //获取登录状态
    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    private val useWebListPresenterImpl: UseWebListPresenterImpl by lazy {
        UseWebListPresenterImpl(null,this)
    }
    private lateinit var agentWeb: AgentWeb
    private lateinit var name: String
    private lateinit var link: String

    override fun cancelRequest() {
        useWebListPresenterImpl.cancelRequest()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            //分享
            R.id.menuShare -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(
                            Intent.EXTRA_TEXT,
                            getString(
                                    R.string.share_article_url,
                                    getString(R.string.app_name),
                                    name,
                                    link
                            )
                    )
                    type = Constant.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.share_title)))
                }
                return true
            }

            //收藏网址
            R.id.menuLike -> {
                if (isLogin) {
                    useWebListPresenterImpl.collectWeb(name,link)
                } else {
                    toast(getString(R.string.login_please_login))
                }
            }
        }

        return super.onOptionsItemSelected(item)
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

    override fun getCollectWebSuccess(result: CollectWebListResponse) {
        toast(getString(R.string.collect_success))
    }

    override fun getCollectWebFailed(errorMessage: String?) {
        toast(getString(R.string.collect_fail))
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