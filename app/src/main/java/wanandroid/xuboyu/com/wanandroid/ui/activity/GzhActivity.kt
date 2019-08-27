package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_gzh.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.GzhAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.GzhResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.GzhPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.GzhView

/**
 * use：公众号列表界面
 * author: XuBoYu
 * time: 2019/8/8
 **/
class GzhActivity : BaseActivity(), GzhView {

    private val datas = mutableListOf<GzhResponse.Data>()

    private val gzhListPresenterImpl : GzhPresenterImpl by lazy {
        GzhPresenterImpl(this,null)
    }

    private val gzhAdapter: GzhAdapter by lazy {
        GzhAdapter(this,datas)
    }

    override fun setLayoutId(): Int = R.layout.activity_gzh

    override fun cancelRequest() {
        gzhListPresenterImpl.cancelRequest()
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.run {
            title = getString(R.string.gzh)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        gzh_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        gzh_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = gzhAdapter
        }

        gzhAdapter.run {
            bindToRecyclerView(gzh_rv)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@GzhActivity.onItemClickListener
        }

        gzhListPresenterImpl.getGzh()
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
        gzh_swipe.isRefreshing = true
        gzhListPresenterImpl.getGzh()
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            Intent(this, GzhListActivity::class.java).run {
                this.putExtra(Constant.GZH_NAME, datas[position].name)
                this.putExtra(Constant.GZH_ID, datas[position].id)
                startActivity(this)
            }
        }
    }

    /**
     * RefreshListener 滑动监听
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    override fun getGzhSuccess(result: GzhResponse) {
        result.data?.let {
            gzhAdapter.run {
                if (gzh_swipe.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                toast(getString(R.string.get_data_success))
            }
        }
        gzh_swipe.isRefreshing = false
    }

    override fun getGzhFailed(errorMsg: String?) {
        gzhAdapter.setEnableLoadMore(false)
        gzhAdapter.loadMoreFail()
        errorMsg?.let {
            toast(it)
        } ?: let {
            toast(getString(R.string.get_data_error) + "-" + errorMsg)
        }
        gzh_swipe.isRefreshing = false
    }

}