package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_type_content.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.TypeArticleAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.bean.ArticleListResponse
import wanandroid.xuboyu.com.wanandroid.bean.Datas
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.TypeArticlePresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.ui.activity.ContentActivity
import wanandroid.xuboyu.com.wanandroid.view.CollectArticleView
import wanandroid.xuboyu.com.wanandroid.view.TypeArticleFragmentView

/**
 * use：单个类型列表
 * author: XuBoYu
 * time: 2019/7/31
 **/
class TypeArticleFragment: BaseFragment(),CollectArticleView,TypeArticleFragmentView {
    /**
     * mainView
     */
    private var mainView: View? = null
    /**
     * Data List
     */
    private val datas = mutableListOf<Datas>()
    /**
     * presenter
     */
    private val typeArticlePresenter: TypeArticlePresenterImpl by lazy {
        TypeArticlePresenterImpl(this, this)
    }
    /**
     * adapter
     */
    private val typeArticleAdapter: TypeArticleAdapter by lazy {
        TypeArticleAdapter(activity, datas)
    }
    /**
     * type id
     */
    private var cid: Int = 0

    //检查登录状态
    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainView ?: let {
            mainView = inflater.inflate(R.layout.fragment_type_content, container, false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cid = arguments.getInt(Constant.CONTENT_CID_KEY)
        tabSwipeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }
        tabRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = typeArticleAdapter
        }
        typeArticleAdapter.run {
            setOnLoadMoreListener(onRequestLoadMoreListener, tabRecyclerView)
            onItemClickListener = this@TypeArticleFragment.onItemClickListener
            onItemChildClickListener = this@TypeArticleFragment.onItemChildClickListener
            setEmptyView(R.layout.fragment_home_empty)
        }
        typeArticlePresenter.getTypeArticleList(cid = cid)
    }

    override fun cancelRequest() {
        typeArticlePresenter.cancelRequest()
        typeArticleAdapter.loadMoreComplete()
        tabSwipeRefreshLayout.isRefreshing = false
    }

    /**
     * 获取列表成功
     * @param result ArticleListResponse
     */
    override fun getTypeArticleListSuccess(result: ArticleListResponse) {
        result.data.datas?.let {
            typeArticleAdapter.run {
                // 列表总数
                val total = result.data.total
                // 当前总数
                if (result.data.offset >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }
                if (tabSwipeRefreshLayout.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                activity.toast(getString(R.string.get_data_success))
            }
        }
        tabSwipeRefreshLayout.isRefreshing = false
    }

    /**
     * 获取列表失败
     * @param errorMessage error message
     */
    override fun getTypeArticleListFailed(errorMessage: String?) {
        typeArticleAdapter.setEnableLoadMore(false)
        typeArticleAdapter.loadMoreFail()
        errorMessage?.let {
            activity.toast(it)
        } ?: let {
            activity.toast(getString(R.string.get_data_error))
        }
        tabSwipeRefreshLayout.isRefreshing = false
    }

    /**
     * 获取空列表
     */
    override fun getTypeArticleListZero() {
        activity.toast(getString(R.string.get_data_zero))
        tabSwipeRefreshLayout.isRefreshing = false
    }

    /**
     * 列表长度小于20
     * @param result ArticleListResponse
     */
    override fun getTypeArticleListSmall(result: ArticleListResponse) {
        result.data.datas?.let {
            typeArticleAdapter.run {
                replaceData(it)
                loadMoreComplete()
                loadMoreEnd()
                setEnableLoadMore(false)
            }
        }
        tabSwipeRefreshLayout.isRefreshing = false
    }

    /**
     * 收藏成功
     * @param result HomeListResponse
     * @param isAdd true add, false remove
     */
    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        activity.toast(
                if (isAdd) activity.getString(R.string.collect_success)
                else activity.getString(R.string.unCollect_success)
        )
    }

    /**
     * 收藏失败
     * @param errorMessage error message
     * @param isAdd true add, false remove
     */
    @SuppressLint("StringFormatInvalid")
    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        activity.toast(
                if (isAdd) activity.getString(R.string.collect_fail, errorMessage)
                else activity.getString(R.string.unCollect_fail, errorMessage)
        )
    }

    /**
     * 刷新监听 RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        tabSwipeRefreshLayout.isRefreshing = true
        typeArticleAdapter.setEnableLoadMore(false)
        typeArticlePresenter.getTypeArticleList(cid = cid)
    }
    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            Intent(activity, ContentActivity::class.java).run {
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
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        val page = typeArticleAdapter.data.size / 20 + 1
        typeArticlePresenter.getTypeArticleList(page, cid)
    }
    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
                if (datas.size != 0) {
                    val data = datas[position]
                    when (view.id) {
                        R.id.item_collect -> {
                            if (isLogin) {
                                val collect = data.collect
                                data.collect = !collect
                                typeArticleAdapter.setData(position, data)
                                typeArticlePresenter.collectArticle(data.id, !collect, true,data.title, data.author, data.link)
                            } else {
                                activity.toast(getString(R.string.login_please_login))
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

    companion object {
        fun newInstance(cid: Int): TypeArticleFragment {
            val fragment = TypeArticleFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }
}