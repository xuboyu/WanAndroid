package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_gzh.toolbar
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.project_kind_item.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.ProjectItemAdapter
import wanandroid.xuboyu.com.wanandroid.adapter.ProjectKindAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.bean.ProjectResponse
import wanandroid.xuboyu.com.wanandroid.bean.ProjectTypeResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.ProjectListPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.ProjectListView

/**
 * use：项目推荐列表界面
 * author: XuBoYu
 * time: 2019/8/8
 **/
class ProjectListActivity : BaseActivity(), ProjectListView {

    private val datas = mutableListOf<ProjectResponse.Data.CDatas>()

    private var kinds = mutableListOf<ProjectTypeResponse.Data>()

    private var selectProject: ProjectTypeResponse.Data? = null

    private val projectListPresenter : ProjectListPresenterImpl by lazy {
        ProjectListPresenterImpl(this)
    }

    private val projectItemAdapter: ProjectItemAdapter by lazy {
        ProjectItemAdapter(this,datas)
    }

    private val projectKindAdapter: ProjectKindAdapter by lazy {
        ProjectKindAdapter(this, kinds)
    }

    private var page: Int = 0

    private var cid: Int by Preference(Constant.CID,0)

    override fun setLayoutId(): Int = R.layout.activity_project

    override fun cancelRequest() {
        projectListPresenter.cancelPLRequest()
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.run {
            title = getString(R.string.xmtj)
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
                if(cid != 0) {
                    projectListPresenter.getProjectTypeList(page+1,cid)
                } else {
                    projectListPresenter.getProjectList(page)
                }
            }, p_rv)
            onItemClickListener = this@ProjectListActivity.onItemClickListener
        }

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.setScrimColor(Color.TRANSPARENT)

        projectListPresenter.getProjectList(page)

        more_type.setOnClickListener(onClickListener)
        projectListPresenter.getProjectKind()

        n_p.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                cid = 0
                toolbar.title = getString(R.string.xmtj)
                refreshData()
                projectListPresenter.getProjectKind()
                projectKindAdapter.notifyDataSetChanged()
            }
        }
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
        projectItemAdapter.setEnableLoadMore(false)
        p_swipe.isRefreshing = true
        if (cid != 0) {
            projectListPresenter.getProjectTypeList(page+1,cid)
        } else {
            projectListPresenter.getProjectList(page)
        }
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
                cid = 0
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.more_type -> {
                changeRightPage()
            }

        }
    }

    private fun changeRightPage() {
        if (drawerLayout.isDrawerOpen(flRight)) {
            drawerLayout.closeDrawer(flRight)
        } else {
            drawerLayout.openDrawer(flRight)
        }
    }

    private fun initKindsAdapter() {
        rvKinds.layoutManager = LinearLayoutManager(this)
        rvKinds.adapter = projectKindAdapter
    }

    override fun getProjectKindSuccess(result: ProjectTypeResponse) {
        kinds = result.data as MutableList<ProjectTypeResponse.Data>
        selectProject = kinds[0]
        initKindsAdapter()
        projectKindAdapter.setOnItemClickListener { adapter, view, position ->
            // 关闭侧滑。请求数据
            drawerLayout.closeDrawer(flRight)
            n_p.isChecked = false
            selectProject = kinds[position]
            projectItemAdapter.loadMoreEnd(true)
            projectKindAdapter.setSelect(selectProject!!)
            toolbar.title = selectProject!!.name
            cid = selectProject!!.id
            projectListPresenter.getProjectTypeList(page+1,selectProject!!.id)
            projectKindAdapter.notifyDataSetChanged()
            refreshData()
            //刷新主列表下拉监听
            projectItemAdapter.run {
                setOnLoadMoreListener({
                    val page = projectItemAdapter.data.size / 15 + page
                    if(cid != 0) {
                        projectListPresenter.getProjectTypeList(page+1,cid)
                    } else {
                        projectListPresenter.getProjectList(page)
                    }
                }, p_rv)
                onItemClickListener = this@ProjectListActivity.onItemClickListener
            }
        }
    }

    override fun getProjectKindFailed(errorMessage: String?) {
        toast(getString(R.string.get_data_error))
    }

    override fun getProjectKindZero() {
        toast(getString(R.string.get_data_zero))
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