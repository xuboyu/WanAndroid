package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_type_list.*
import kotlinx.android.synthetic.main.activity_type_list.toolbar
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.TypeAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.TypeFragmentPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.TypeFragmentView

/**
 * use：类别列表界面
 * author: XuBoYu
 * time: 2019/7/30
 **/
class TypeListActivity : BaseActivity(), TypeFragmentView{

    private val datas = mutableListOf<TreeListResponse.Data>()

    private val typeAdapter: TypeAdapter by lazy {
        TypeAdapter(this, datas)
    }

    private val typeFragmentPresenter: TypeFragmentPresenterImpl by lazy {
        TypeFragmentPresenterImpl(this)
    }

    override fun setLayoutId(): Int = R.layout.activity_type_list

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.run {
            title = getString(R.string.zstx)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        type_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }
        type_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = typeAdapter
        }
        typeAdapter.run {
            bindToRecyclerView(type_rv)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@TypeListActivity.onItemClickListener
        }
        typeFragmentPresenter.getTreeList()
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

    override fun getTypeListSuccess(result: TreeListResponse) {
        result.data.let {
            if (type_swipe.isRefreshing) {
                typeAdapter.replaceData(it)
            } else {
                typeAdapter.addData(it)
                toast(getString(R.string.get_data_success))
            }
        }
        type_swipe.isRefreshing = false
    }

    override fun getTypeListFailed(errorMessage: String?) {
        errorMessage?.let {
            toast(it)
        } ?: let {
            toast(getString(R.string.get_data_error))
        }
        type_swipe.isRefreshing = false
    }

    override fun getTypeListZero() {
        toast(getString(R.string.get_data_zero))
        type_swipe.isRefreshing = false
    }

    override fun cancelRequest() {
        typeFragmentPresenter.cancelRequest()
    }

    /**
     * RefreshListener 滑动监听
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    /**
     * refresh 数据刷新
     */
    fun refreshData() {
        type_swipe.isRefreshing = true
        typeFragmentPresenter.getTreeList()
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            Intent(this, TypeActivity::class.java).run {
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].name)
                putExtra(Constant.CONTENT_CHILDREN_DATA_KEY, datas[position])
                startActivity(this)
            }
        }
    }

}