package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_use_weblist.*
import kotlinx.android.synthetic.main.web_list_item.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.CollectWebListAdapter
import wanandroid.xuboyu.com.wanandroid.adapter.UseWebListAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.CollectWebListResponse
import wanandroid.xuboyu.com.wanandroid.bean.WebData
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.UseWebListPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.UseWebListView

/**
 * use：常用网址列表界面
 * author: XuBoYu
 * time: 2019/8/1
 **/
class UseWebListActivity : BaseActivity(), UseWebListView {

    private val datas = mutableListOf<WebData>()

    private val useWebListPresenter : UseWebListPresenterImpl by lazy {
        UseWebListPresenterImpl(this,null)
    }

    private val useWebListAdapter: UseWebListAdapter by lazy {
        UseWebListAdapter(this,datas)
    }

    override fun setLayoutId(): Int = R.layout.activity_use_weblist

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.run {
            title = getString(R.string.cywz)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        web_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        cv_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = useWebListAdapter
        }

        useWebListAdapter.run {
            bindToRecyclerView(cv_rv)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@UseWebListActivity.onItemClickListener
        }

        useWebListPresenter.getUseWebList()
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
     * refresh 数据刷新
     */
    fun refreshData() {
        web_swipe.isRefreshing = true
        useWebListPresenter.getUseWebList()
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

    override fun cancelRequest() {
        useWebListPresenter.cancelRequest()
    }

    override fun getUseWebListSuccess(result: CollectWebListResponse) {
        result.data?.let {
            useWebListAdapter.run {
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

    override fun getUseWebListFailed(errorMessage: String?) {
        useWebListAdapter.setEnableLoadMore(false)
        useWebListAdapter.loadMoreFail()
        errorMessage?.let {
            toast(it)
        } ?: let {
            toast(getString(R.string.get_data_error) + "-" + errorMessage)
        }
        web_swipe.isRefreshing = false
    }

    override fun getUseWebListZero() {
        toast(getString(R.string.get_data_zero))
    }

}