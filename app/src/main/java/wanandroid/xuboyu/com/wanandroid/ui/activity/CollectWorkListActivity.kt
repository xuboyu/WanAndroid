package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_collect_work.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.HomeAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.Datas
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.CollectWorkListPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.CollectWorkListView

/**
 * use：个人收藏文章列表界面
 * author: XuBoYu
 * time: 2019/8/5
 **/
class CollectWorkListActivity : BaseActivity(), CollectWorkListView {

    //文章 Data List
    private val datas = mutableListOf<Datas>()

    override fun setLayoutId(): Int  = R.layout.activity_collect_work

    private val collectWorkListPresenter: CollectWorkListPresenterImpl by lazy {
        CollectWorkListPresenterImpl(this)
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(this,datas)
    }

    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.run {
            title = "收藏文章"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        work_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        cw_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }

        homeAdapter.run {
            bindToRecyclerView(cw_rv)
            setOnLoadMoreListener({
                val page = homeAdapter.data.size / 20 + page
                collectWorkListPresenter.getCollectWorkList(page)
            }, cw_rv)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@CollectWorkListActivity.onItemClickListener
            onItemChildClickListener = this@CollectWorkListActivity.onItemChildClickListener
        }

        collectWorkListPresenter.getCollectWorkList(0)
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun cancelRequest() {
        collectWorkListPresenter.cancelRequest()
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
        homeAdapter.setEnableLoadMore(false)
        work_swipe.isRefreshing = true
        collectWorkListPresenter.getCollectWorkList(0)
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
                R.id.item_kind -> {
                    data.chapterName ?: let {
                        toast(getString(R.string.type_null))
                        return@OnItemChildClickListener
                    }
                    Intent(this, TypeActivity::class.java).run {
                        putExtra(Constant.CONTENT_TARGET_KEY, true)
                        putExtra(Constant.CONTENT_TITLE_KEY, data.chapterName)
                        putExtra(Constant.CONTENT_CID_KEY, data.chapterId)
                        startActivity(this)
                    }
                }
                R.id.item_collect -> {
                    val collect = data.collect
                    data.collect = !collect
                    homeAdapter.setData(position, data)
                    collectWorkListPresenter.deleteWork(data.id, data.originId)
                    homeAdapter.remove(position)
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

    override fun getCollectWorkListSuccess(result: HomeListResponse) {
        result.data.datas?.let {
            homeAdapter.run {
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

    override fun getCollectWorkListFailed(errorMessage: String?) {
        homeAdapter.setEnableLoadMore(false)
        homeAdapter.loadMoreFail()
        errorMessage?.let {
            toast(it)
        } ?: let {
            toast(getString(R.string.get_data_error) + "-" + errorMessage)
        }
        work_swipe.isRefreshing = false
    }

    override fun getCollectWorkListZero() {
        toast(getString(R.string.get_data_zero))
    }

    override fun getCollectWorkListSmall(result: HomeListResponse) {
        result.data.datas?.let {
            homeAdapter.run {
                replaceData(it)
                loadMoreEnd()
                loadMoreComplete()
                setEnableLoadMore(false)
            }
        }
        toast(getString(R.string.get_data_success))
        work_swipe.isRefreshing = false
    }

    override fun deleteWorkSuccess(result: HomeListResponse) {
        toast(getString(R.string.unCollect_success))
    }

    override fun deleteWorkFailed(errorMessage: String?) {
        toast(getString(R.string.unCollect_fail))
    }

}