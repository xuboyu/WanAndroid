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
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.getAgentWeb
import wanandroid.xuboyu.com.wanandroid.presenter.ContentPresenterImpl
import wanandroid.xuboyu.com.wanandroid.presenter.HomeFragmentImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.CollectArticleView

/**
 * use：webView 显示界面
 * 显示文章内容等等...
 * author: XuBoYu
 * time: 2019/7/29
 **/

class ContentActivity: BaseActivity(), CollectArticleView {

    //获取登录状态
    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    private val collectArticlePresenter: ContentPresenterImpl by lazy {
        ContentPresenterImpl(this)
    }

    private lateinit var agentWeb: AgentWeb
    private lateinit var shareTitle: String
    private lateinit var shareUrl: String
    private lateinit var author: String
    private var userId: Int = 2 // 2为官方userid -1为其他
    private var id: Int = 0
    private var collect: Boolean = false // 默认没收藏

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
            id = it.getInt(Constant.CONTENT_ID_KEY,0)
            author = it.getString(Constant.CONTENT_AUTHOR)
            shareTitle = it.getString(Constant.CONTENT_TITLE_KEY)
            shareUrl = it.getString(Constant.CONTENT_URL_KEY)
            userId = it.getInt(Constant.CONTENT_USER_ID, 2)
            collect = it.getBoolean(Constant.CONTENT_IS_COLLECT, false)
            agentWeb = shareUrl.getAgentWeb(
                    this,
                    webContent,
                    LinearLayout.LayoutParams(-1, -1),
                    receivedTitleCallback
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //返回 back
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
                                    shareTitle,
                                    shareUrl
                            )
                    )
                    type = Constant.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.share_title)))
                }
                return true
            }

            //收藏
            R.id.menuLike -> {
                if (isLogin) {
                    if (userId == 2) {
                        //站内文章
                        collectArticlePresenter.collectArticle(id, !collect, true, shareTitle, author, shareUrl)
                    } else {
                        //站外文章
                        collectArticlePresenter.collectArticle(id, !collect, false, shareTitle, author, shareUrl)
                    }
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

    override fun cancelRequest() {

    }

    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        toast(
                if (isAdd) getString(R.string.collect_success)
                else getString(R.string.unCollect_success)
        )
        collect = !collect
    }

    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        toast(
                if (isAdd) getString(R.string.collect_fail, errorMessage)
                else getString(R.string.unCollect_fail, errorMessage)
        )
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