package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_project.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.ProjectItemAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.ProjectResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.ProjectListPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.ProjectListView

/**
 * use：项目分类
 * author: XuBoYu
 * time: 2019/8/12
 **/
abstract class ProjectTypeListActivity : BaseActivity(), ProjectListView {

    private var Ttitle: String? = null

    private var cid: Int = 0

    private var page: Int = 1

    private val datas = mutableListOf<ProjectResponse.Data.CDatas>()

    private val projectListPresenter : ProjectListPresenterImpl by lazy {
        ProjectListPresenterImpl(this)
    }

    private val projectItemAdapter: ProjectItemAdapter by lazy {
        ProjectItemAdapter(this,datas)
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun setLayoutId(): Int = R.layout.activity_project

    override fun cancelRequest() {
        projectListPresenter.cancelPLRequest()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            Ttitle = it.getString(Constant.CONTENT_TITLE_KEY,"项目")
            cid = it.getInt(Constant.CONTENT_CID_KEY,0)
        }

        toolbar.run {
            title = Ttitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        p_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        p_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = projectItemAdapter
        }

        projectItemAdapter.run {
            bindToRecyclerView(p_rv)
            setEmptyView(R.layout.fragment_home_empty)
            setOnLoadMoreListener({
                val page = projectItemAdapter.data.size / 15 + page
                projectListPresenter.getProjectTypeList(page, cid)
            }, p_rv)
            onItemClickListener = this@ProjectTypeListActivity.onItemClickListener
        }

        projectListPresenter.getProjectTypeList(page, cid)
    }

    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            Intent(this, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, datas[position].link)
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].title)
                putExtra(Constant.CONTENT_ID_KEY, "")
                putExtra(Constant.CONTENT_USER_ID, "")
                putExtra(Constant.CONTENT_IS_COLLECT, "")
                putExtra(Constant.CONTENT_AUTHOR, "")
                putExtra("banner", true)
                startActivity(this)
            }
        }
    }


    /**
     * refresh 数据刷新
     */
    fun refreshData() {
        projectItemAdapter.setEnableLoadMore(true)
        p_swipe.isRefreshing = true
        projectListPresenter.getProjectTypeList(page,cid)
    }

    /**
     * RefreshListener 滑动监听
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
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

    override fun getProjectListSuccess(result: ProjectResponse) {
        result.data?.datas?.let {
            projectItemAdapter.run {
                if (p_swipe.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                // 列表总数
                val total = result.data?.total!!
                if (result.data?.offset!! >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }
                if (result.data?.datas?.size!! >= 15) {
                    setEnableLoadMore(true)
                } else if (result.data?.datas?.size!! < 15) {
                    setEnableLoadMore(false)
                }
                toast(getString(R.string.get_data_success))
            }
        }
        p_swipe.isRefreshing = false
    }

    override fun getProjectListFailed(errorMessage: String?) {
        projectItemAdapter.setEnableLoadMore(false)
        projectItemAdapter.loadMoreFail()
        errorMessage?.let {
            toast(it)
        } ?: let {
            toast(getString(R.string.get_data_error) + "-" + errorMessage)
        }
        p_swipe.isRefreshing = false
    }

    override fun getProjectListZero() {
        toast(getString(R.string.get_data_zero))
    }

}