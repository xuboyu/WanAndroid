package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.BannerAdapter
import wanandroid.xuboyu.com.wanandroid.adapter.HomeAdapter
import wanandroid.xuboyu.com.wanandroid.adapter.HorizontalRecyclerView
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.Datas
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.inflater
import wanandroid.xuboyu.com.wanandroid.presenter.HomeFragmentImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.ui.activity.ContentActivity
import wanandroid.xuboyu.com.wanandroid.ui.activity.LoginActivity
import wanandroid.xuboyu.com.wanandroid.ui.activity.TypeActivity
import wanandroid.xuboyu.com.wanandroid.view.CollectArticleView
import wanandroid.xuboyu.com.wanandroid.view.HomeFragmentView

/**
 * use：fragment基类
 * author: XuBoYu
 * time: 2019/4/19
 **/
class HomeFragment : BaseFragment(), HomeFragmentView, CollectArticleView {

    companion object {
        private const val BANNER_TIME = 5000L
    }

    //mainView
    private var mainView: View? = null

    //文章 Data List
    private val datas = mutableListOf<Datas>()

    //presenter
    private val homeFragmentImpl: HomeFragmentImpl by lazy {
        HomeFragmentImpl(this, this)
    }

    //轮播 Banner data
    private val bannerDatas = mutableListOf<BannerResponse.Data>()

    //轮播 Banner RecyclerView adapter
    private val bannerAdapter: BannerAdapter by lazy {
        BannerAdapter(activity, bannerDatas)
    }

    //轮播 Banner PagerSnapHelper
    private val bannerPagerSnap: PagerSnapHelper by lazy {
        PagerSnapHelper()
    }

    //adapter
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, datas)
    }

    //LinearLayoutManager
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    //轮播 Banner switch job
    private var bannerSwitchJob: Job? = null

    //轮播图下标
    private var currentIndex = 0

    //Banner view
    private lateinit var bannerRecyclerView: HorizontalRecyclerView

    //获取登录状态
    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainView ?: let {
            mainView = inflater.inflate(R.layout.fragment_home, container, false)
            bannerRecyclerView = activity.inflater(R.layout.fragment_home_banner) as HorizontalRecyclerView
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_swipe.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }
        home_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }
        bannerRecyclerView.run {
            layoutManager = linearLayoutManager
            bannerPagerSnap.attachToRecyclerView(this)
            requestDisallowInterceptTouchEvent(true)
            setOnTouchListener(onTouchListener)
            addOnScrollListener(onScrollListener)
        }
        bannerAdapter.run {
            bindToRecyclerView(bannerRecyclerView)
            onItemClickListener = this@HomeFragment.onBannerItemClickListener
        }
        homeAdapter.run {
            bindToRecyclerView(home_rv)
            setOnLoadMoreListener(onRequestLoadMoreListener, home_rv)
            onItemClickListener = this@HomeFragment.onItemClickListener
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            addHeaderView(bannerRecyclerView)
            setEmptyView(R.layout.fragment_home_empty)
        }
        homeFragmentImpl.getBanner()
        homeFragmentImpl.getHomeList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelSwitchJob()
    }

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    /**
     * scroll to top
     */
    fun smoothScrollToPosition() = home_rv.scrollToPosition(0)

    /**
     * refresh
     */
    fun refreshData() {
        home_swipe.isRefreshing = true
        homeAdapter.setEnableLoadMore(false)
        cancelSwitchJob()
        homeFragmentImpl.getBanner()
        homeFragmentImpl.getHomeList()
    }

    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        val page = homeAdapter.data.size / 20 + 1
        homeFragmentImpl.getHomeList(page)
    }

    /**
     * 文章点击事件 ItemClickListener
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
     * banner点击事件 onBannerItemClickListener
     */
    private val onBannerItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (bannerDatas.size != 0) {
            Intent(activity, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, bannerDatas[position].url)
                putExtra(Constant.CONTENT_TITLE_KEY, bannerDatas[position].title)
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
     * 文章子控件点击事件 ItemChildClickListener
     */
    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
                if (datas.size != 0) {
                    val data = datas[position]
                    when (view.id) {
                        R.id.item_kind -> {
                            data.chapterName ?: let {
                                activity.toast(getString(R.string.type_null))
                                return@OnItemChildClickListener
                            }
                            Intent(activity, TypeActivity::class.java).run {
                                putExtra(Constant.CONTENT_TARGET_KEY, true)
                                putExtra(Constant.CONTENT_TITLE_KEY, data.chapterName)
                                putExtra(Constant.CONTENT_CID_KEY, data.chapterId)
                                startActivity(this)
                            }
                        }
                        R.id.item_collect -> {
                            if (isLogin) {
                                val collect = data.collect
                                data.collect = !collect
                                homeAdapter.setData(position, data)
                                if(data.chapterName.equals("官方")) {
                                    //站内文章
                                    homeFragmentImpl.collectArticle(data.id, !collect, true,data.title, data.author, data.link)
                                } else {
                                    //站外文章
                                    homeFragmentImpl.collectArticle(data.id, !collect, false, data.title, data.author, data.link)
                                }
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

    /**
     * SCROLL_STATE_IDLE to start job
     * 活动结束开始获取
     */
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                    currentIndex = linearLayoutManager.findFirstVisibleItemPosition()
                    startSwitchJob()
                }
            }
        }
    }

    /**
     * ACTION_MOVE to cancel job
     * 开始滑动取消job
     */
    private val onTouchListener = View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                cancelSwitchJob()
            }
        }
        false
    }

    /**
     * 请求首页文章列表成功
     */
    override fun getHomeListSuccess(result: HomeListResponse) {
        result.data.datas?.let {
            homeAdapter.run {
                // 列表总数
                val total = result.data.total
                // 当前总数
                if (result.data.offset >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }
                if (home_swipe.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                activity.toast(getString(R.string.get_data_success))
            }
        }
        home_swipe.isRefreshing = false
    }

    /**
     * 请求首页文章失败
     */
    override fun getHomeListFailed(errorMessage: String?) {
        homeAdapter.setEnableLoadMore(false)
        homeAdapter.loadMoreFail()
        errorMessage?.let {
            activity.toast(it)
        } ?: let {
            activity.toast(getString(R.string.get_data_error) + "-" + errorMessage)
        }
        home_swipe.isRefreshing = false
    }

    /**
     * 首页文章列表为0
     */
    override fun getHomeListZero() {
        activity.toast(getString(R.string.get_data_zero))
    }

    /**
     * 首页文章获取小数组
     */
    override fun getHomeListSmall(result: HomeListResponse) {
        result.data.datas?.let {
            homeAdapter.run {
                replaceData(it)
                loadMoreComplete()
                loadMoreEnd()
                setEnableLoadMore(false)
            }
        }
        home_swipe.isRefreshing = false
    }

    /**
     * 取消请求
     */
    override fun cancelRequest() {
        homeFragmentImpl.cancelRequest()
    }

    /**
     * 获取首页轮播图成功
     */
    override fun getBannerSuccess(result: BannerResponse) {
        result.data?.let {
            bannerAdapter.replaceData(it)
            startSwitchJob()
        }
    }

    /**
     * 获取首页轮播图失败
     */
    override fun getBannerFailed(errorMessage: String?) {
        errorMessage?.let {
            activity.toast(it)
        } ?: let {
            activity.toast(getString(R.string.get_data_error) + "-" + errorMessage)
        }
    }

    /**
     * 获取首页轮播图为空
     */
    override fun getBannerZero() {
        activity.toast(getString(R.string.get_data_zero))
    }

    /**
     * 轮播中断
     */
    private fun startSwitchJob() = bannerSwitchJob?.run {
        if (!isActive) {
            bannerSwitchJob = getBannerSwitchJob().apply { start() }
        }
    } ?: let {
        bannerSwitchJob = getBannerSwitchJob().apply { start() }
    }

    /**
     * 轮播转换
     */
    private fun getBannerSwitchJob() = launch {
        repeat(Int.MAX_VALUE) {
            if (bannerDatas.size == 0) {
                return@launch
            }
            delay(BANNER_TIME)
            currentIndex++
            val index = currentIndex % bannerDatas.size
            bannerRecyclerView.smoothScrollToPosition(index)
            currentIndex = index
        }
    }

    /**
     * 取消轮播转换
     */
    private fun cancelSwitchJob() = bannerSwitchJob?.run {
        if (isActive) {
            cancel()
        }
    }

    /**
     * 收藏/取消收藏 成功
     */
    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        activity.toast(
                if (isAdd) activity.getString(R.string.collect_success)
                else activity.getString(R.string.unCollect_success)
        )
    }

    /**
     * 收藏/取消收藏 失败
     */
    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        activity.toast(
                if (isAdd) activity.getString(R.string.collect_fail, errorMessage)
                else activity.getString(R.string.unCollect_fail, errorMessage)
        )
    }

}