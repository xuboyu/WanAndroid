package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_collect_work.*
import kotlinx.android.synthetic.main.activity_collect_work.toolbar
import kotlinx.android.synthetic.main.activity_content.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.GzhAdapter
import wanandroid.xuboyu.com.wanandroid.adapter.GzhListAdapter
import wanandroid.xuboyu.com.wanandroid.adapter.HomeAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.bean.Datas
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.getAgentWeb
import wanandroid.xuboyu.com.wanandroid.presenter.GzhPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.GzhListView

/**
 * use：公众号文章列表界面
 * author: XuBoYu
 * time: 2019/8/8
 **/
class GzhListActivity : BaseActivity(), GzhListView {

    //文章 Data List
    private val datas = mutableListOf<Datas>()

    //获取登录状态
    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    private val gzhListPresenterImpl : GzhPresenterImpl by lazy {
        GzhPresenterImpl(null,this)
    }

    private val gzhAdapter: GzhListAdapter by lazy {
        GzhListAdapter(this,datas)
    }

    private var name: String = "公众号文章"

    private var page: Int = 0

    private var id: Int = 0

    override fun setLayoutId(): Int = R.layout.activity_gzhlist

    override fun cancelRequest() {
        gzhListPresenterImpl.cancelRequest()
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            name = it.getString(Constant.GZH_NAME,"公众号文章")
            id = it.getInt(Constant.GZH_ID,0)
        }

        toolbar.run {
            title = name
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        work_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        cw_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = gzhAdapter
        }

        gzhAdapter.run {
            bindToRecyclerView(cw_rv)
            setOnLoadMoreListener({
                val page = gzhAdapter.data.size / 20 + page
                gzhListPresenterImpl.getGzhList(id,page)
            }, cw_rv)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@GzhListActivity.onItemClickListener
            onItemChildClickListener = this@GzhListActivity.onItemChildClickListener
        }

        gzhListPresenterImpl.getGzhList(id,page)
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

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        gzhAdapter.setEnableLoadMore(false)
        work_swipe.isRefreshing = true
        gzhListPresenterImpl.getGzhList(id,page)
        return@OnRefreshListener
    }

    /**
     * 文章点击事件 ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            Intent(this, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, datas[position].link)
                putExtra(Constant.CONTENT_ID_KEY, datas[position].id)
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].title)
                putExtra(Constant.CONTENT_USER_ID, datas[position].userId)
                putExtra(Constant.CONTENT_IS_COLLECT, datas[position].collect)
                putExtra(Constant.CONTENT_AUTHOR, datas[position].author)
                putExtra("banner", false)
                startActivity(this)
            }
        }
    }

    /**
     * 文章子控件点击事件 ItemChildClickListener
     */
    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        if (datas.size != 0) {
            val data = datas[position]
            when (view.id) {
//                R.id.item_kind -> {
//                    data.chapterName ?: let {
//                        toast(getString(R.string.type_null))
//                        return@OnItemChildClickListener
//                    }
//                    Intent(this, TypeActivity::class.java).run {
//                        putExtra(Constant.CONTENT_TARGET_KEY, true)
//                        putExtra(Constant.CONTENT_TITLE_KEY, data.chapterName)
//                        putExtra(Constant.CONTENT_CID_KEY, data.chapterId)
//                        startActivity(this)
//                    }
//                }
                R.id.item_collect -> {
                    if (isLogin) {
                        val collect = data.collect
                        data.collect = !collect
                        gzhAdapter.setData(position, data)
                        gzhListPresenterImpl.collectArticle(data.id, !collect, false, data.title, data.author, data.link)
                    }else {
                        toast(getString(R.string.login_please_login))
                    }
                }
                R.id.item_share -> {
                    Intent().run {
                        action = Intent.ACTION_SEND
                        putExtra(
                                Intent.EXTRA_TEXT,
                                getString(
                                        R.string.share_article_url,
                                        getString(R.string.app_name),
                                        data.title,
                                        data.link
                                )
                        )
                        type = Constant.CONTENT_SHARE_TYPE
                        startActivity(Intent.createChooser(this, getString(R.string.share_title)))
                    }
                }
            }
        }
    }

    override fun getGzhListSuccess(result: HomeListResponse) {
        result.data.datas?.let {
            gzhAdapter.run {
                // 列表总数
                val total = result.data.total
                // 当前总数
                if (result.data.offset >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }
                if (work_swipe.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                toast(getString(R.string.get_data_success))
            }
        }
        work_swipe.isRefreshing = false
    }

    override fun getGzhListFailed(errorMessage: String?) {
        gzhAdapter.setEnableLoadMore(false)
        gzhAdapter.loadMoreFail()
        errorMessage?.let {
            toast(it)
        } ?: let {
            toast(getString(R.string.get_data_error) + "-" + errorMessage)
        }
        work_swipe.isRefreshing = false
    }

    override fun getGzhListZero() {
        toast(getString(R.string.get_data_zero))
    }

    override fun getGzhListSmall(result: HomeListResponse) {
        result.data.datas?.let {
            gzhAdapter.run {
                replaceData(it)
                loadMoreEnd()
                loadMoreComplete()
                setEnableLoadMore(false)
            }
        }
        toast(getString(R.string.get_data_success))
        work_swipe.isRefreshing = false
    }

    override fun docWorkSuccess(result: HomeListResponse,isAdd: Boolean) {
        toast(
                if (isAdd) getString(R.string.collect_success)
                else getString(R.string.unCollect_success)
        )
    }

    override fun docWorkFailed(errorMessage: String?,isAdd: Boolean) {
        toast(
                if (isAdd) getString(R.string.collect_fail)
                else getString(R.string.unCollect_fail)
        )
    }

}