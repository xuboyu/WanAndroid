package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_about_app.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.common.Constant

/**
 * use：关于界面
 * author: XuBoYu
 * time: 2019/9/9
 **/
class AboutActivity: BaseActivity() {

    private val HOME_URL = "http://www.wanandroid.com/index"
    private val OPEN_API = "http://www.wanandroid.com/blog/show/2"
    private val PROJECT_URL = "https://github.com/AxeChen/WanAndroid"
    private val DEVELOPER_BLOG = "https://blog.csdn.net/SSBBY"
    private val DEVELOPER_GITHUB = "https://github.com/xuboyu"

    override fun cancelRequest() {

    }

    override fun setLayoutId(): Int  = R.layout.activity_about_app

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.run {
            title = getString(R.string.about)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        initClickListener()

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

    private fun initClickListener() {
        tvHome.setOnClickListener {
            Intent(this, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, HOME_URL)
                putExtra(Constant.CONTENT_TITLE_KEY, "WanAndroid主页")
                putExtra(Constant.CONTENT_ID_KEY, "")
                putExtra(Constant.CONTENT_USER_ID, "")
                putExtra(Constant.CONTENT_IS_COLLECT, "")
                putExtra(Constant.CONTENT_AUTHOR, "")
                putExtra("banner", true)
                startActivity(this)
            }
        }
        tvOpenApi.setOnClickListener {
            Intent(this, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, OPEN_API)
                putExtra(Constant.CONTENT_TITLE_KEY, "WanAndroid开放API")
                putExtra(Constant.CONTENT_ID_KEY, "")
                putExtra(Constant.CONTENT_USER_ID, "")
                putExtra(Constant.CONTENT_IS_COLLECT, "")
                putExtra(Constant.CONTENT_AUTHOR, "")
                putExtra("banner", true)
                startActivity(this)
            }
        }
        tvSrc.setOnClickListener {
            Intent(this, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, PROJECT_URL)
                putExtra(Constant.CONTENT_TITLE_KEY, "APP源码地址")
                putExtra(Constant.CONTENT_ID_KEY, "")
                putExtra(Constant.CONTENT_USER_ID, "")
                putExtra(Constant.CONTENT_IS_COLLECT, "")
                putExtra(Constant.CONTENT_AUTHOR, "")
                putExtra("banner", true)
                startActivity(this)
            }
        }
        tvBlog.setOnClickListener {
            Intent(this, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, DEVELOPER_BLOG)
                putExtra(Constant.CONTENT_TITLE_KEY, "开发者CSDN地址")
                putExtra(Constant.CONTENT_ID_KEY, "")
                putExtra(Constant.CONTENT_USER_ID, "")
                putExtra(Constant.CONTENT_IS_COLLECT, "")
                putExtra(Constant.CONTENT_AUTHOR, "")
                putExtra("banner", true)
                startActivity(this)
            }
        }
        tvGitHub.setOnClickListener {
            Intent(this, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, DEVELOPER_GITHUB)
                putExtra(Constant.CONTENT_TITLE_KEY, "开发者GitHub主页")
                putExtra(Constant.CONTENT_ID_KEY, "")
                putExtra(Constant.CONTENT_USER_ID, "")
                putExtra(Constant.CONTENT_IS_COLLECT, "")
                putExtra(Constant.CONTENT_AUTHOR, "")
                putExtra("banner", true)
                startActivity(this)
            }
        }
    }
}