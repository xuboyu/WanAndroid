package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_collect_web.*
import kotlinx.android.synthetic.main.web_list_item.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.CollectWebListAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.ArticleListResponse
import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse
import wanandroid.xuboyu.com.wanandroid.bean.WebData
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.CollectWebListPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.CollectWebListView

/**
 * use：收藏网址界面
 * author: XuBoYu
 * time: 2019/8/1
 **/
class CollectWebListActivity : BaseActivity(), CollectWebListView {

    private val datas = mutableListOf<WebData>()

    private val collectWebListPresenter: CollectWebListPresenterImpl by lazy {
        CollectWebListPresenterImpl(this)
    }

    private val collectWebListAdapter: CollectWebListAdapter by lazy {
        CollectWebListAdapter(this,datas)
    }

    override fun cancelRequest() {
        collectWebListPresenter.cancelRequest()
    }

    override fun setLayoutId(): Int = R.layout.activity_collect_web

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.run {
            title = getString(R.string.c_web_text)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        web_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        cv_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = collectWebListAdapter
        }

        collectWebListAdapter.run {
            bindToRecyclerView(cv_rv)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@CollectWebListActivity.onItemClickListener
            onItemChildClickListener = this@CollectWebListActivity.onItemChildClickListener
        }

        collectWebListPresenter.getCollectWebList()
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

    override fun getCollectWebListSuccess(result: CollectWebListResponse) {
        result.data?.let {
            collectWebListAdapter.run {
                if (web_swipe.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                toast(getString(R.string.get_data_success))
            }
        }
        web_swipe.isRefreshing = false
    }

    override fun getCollectWebListFailed(errorMessage: String?) {
        collectWebListAdapter.setEnableLoadMore(false)
        collectWebListAdapter.loadMoreFail()
        errorMessage?.let {
            toast(it)
        } ?: let {
            toast(getString(R.string.get_data_error) + "-" + errorMessage)
        }
        web_swipe.isRefreshing = false
    }

    override fun getCollectWebListZero() {
        toast(getString(R.string.get_data_zero))
    }

    override fun deleteWebSuccess(result: ArticleListResponse) {
        toast(getString(R.string.unCollect_success))
    }

    override fun deleteWebFailed(errorMessage: String?) {
        toast(getString(R.string.unCollect_fail))
    }

    /**
     * refresh 数据刷新
     */
    fun refreshData() {
        web_swipe.isRefreshing = true
        collectWebListPresenter.getCollectWebList()
    }

    /**
     * RefreshListener 滑动监听
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            Intent(this, WebContentActivity::class.java).run {
                this.putExtra(Constant.WEB_NAME,datas[position].name)
                this.putExtra(Constant.WEB_LINK,datas[position].link)
                startActivity(this)
            }
        }
    }

    /**
     * onItemChildClickListener
     */
    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        if (datas.size != 0) {
            val data = datas[position]
            when (view.id) {
                R.id.delete_web -> {
                    collectWebListPresenter.deleteWeb(data.id)
                    collectWebListAdapter.remove(position)
                }
            }
        }
    }

}