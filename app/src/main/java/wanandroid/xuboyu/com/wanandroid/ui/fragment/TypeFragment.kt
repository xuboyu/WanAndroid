package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_type.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.TypeAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.TypeFragmentPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.ui.activity.TypeActivity
import wanandroid.xuboyu.com.wanandroid.view.TypeFragmentView

/**
 * use：类别列表界面
 * author: XuBoYu
 * time: 2019/7/30
 **/
class TypeFragment : BaseFragment(), TypeFragmentView{

    //mainView
    private var mainView: View? = null

    private val datas = mutableListOf<TreeListResponse.Data>()

    private val typeAdapter: TypeAdapter by lazy {
        TypeAdapter(activity, datas)
    }

    private val typeFragmentPresenter: TypeFragmentPresenterImpl by lazy {
        TypeFragmentPresenterImpl(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainView ?: let {
            mainView = inflater.inflate(R.layout.fragment_type, container, false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        type_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }
        type_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = typeAdapter
        }
        typeAdapter.run {
            bindToRecyclerView(type_rv)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@TypeFragment.onItemClickListener
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isFirst) {
            typeFragmentPresenter.getTreeList()
            isFirst = false
        }
    }

    override fun getTypeListSuccess(result: TreeListResponse) {
        result.data.let {
            if (type_swipe.isRefreshing) {
                typeAdapter.replaceData(it)
            } else {
                typeAdapter.addData(it)
                activity.toast(getString(R.string.get_data_success))
            }
        }
        type_swipe.isRefreshing = false
    }

    override fun getTypeListFailed(errorMessage: String?) {
        errorMessage?.let {
            activity.toast(it)
        } ?: let {
            activity.toast(getString(R.string.get_data_error))
        }
        type_swipe.isRefreshing = false
    }

    override fun getTypeListZero() {
        activity.toast(getString(R.string.get_data_zero))
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
     * scroll to top
     */
    fun smoothScrollToPosition() = type_rv.scrollToPosition(0)

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
            Intent(activity, TypeActivity::class.java).run {
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].name)
                putExtra(Constant.CONTENT_CHILDREN_DATA_KEY, datas[position])
                startActivity(this)
            }
        }
    }

}