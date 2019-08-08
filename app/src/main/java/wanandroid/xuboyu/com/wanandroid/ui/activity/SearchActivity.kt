package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_my.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.HomeAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.bean.Datas
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.loge
import wanandroid.xuboyu.com.wanandroid.presenter.HomeFragmentImpl
import wanandroid.xuboyu.com.wanandroid.presenter.SearchPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.view.SearchListView
import kotlin.math.log

/**
 * use：搜索界面
 * author: XuBoYu
 * time: 2019/8/6
 **/
class SearchActivity : BaseActivity(), SearchListView {

    private val datas = mutableListOf<Datas>()

    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    private val searchPresenter: SearchPresenterImpl by lazy {
        SearchPresenterImpl(this)
    }

    private var searchKey: String? = null
    private var startSearch: Boolean = false

    /**
     * adapter
     */
    private val searchAdapter: HomeAdapter by lazy {
        HomeAdapter(this, datas)
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun setLayoutId(): Int = R.layout.activity_search

    override fun cancelRequest() {
        searchPresenter.cancelRequest()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            searchKey = it.getString(Constant.SEARCH_KEY, "")
            startSearch = it.getBoolean(Constant.IS_START_SEARCH, false)
        }
        search_swipe.run {
            setOnRefreshListener(onRefreshListener)
        }
        s_rv.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }
        searchAdapter.run {
            bindToRecyclerView(s_rv)
            setOnLoadMoreListener({
                val page = searchAdapter.data.size / 20 + 1
                searchKey?.let {
                    searchPresenter.getSearchList(page, it)
                }
            }, s_rv)
            onItemClickListener = this@SearchActivity.onItemClickListener
            onItemChildClickListener = this@SearchActivity.onItemChildClickListener
            setEmptyView(R.layout.fragment_home_empty)
        }
        searchKey?.let {
            sEdit.setText(searchKey)
            if (startSearch) {
                searchPresenter.getSearchList(0, searchKey!!)
            }
        }
        //控件设置监听
        sBack.setOnClickListener(onClickListener)
        sBtn.setOnClickListener(onClickListener)
        sEdit.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                if(sEdit.text.toString().equals("")) {
                    toast(getString(R.string.search_not_empty))
                } else {
                    searchPresenter.getSearchList(0, sEdit.text.toString())
                    search_swipe.isRefreshing = true
                    searchKey = sEdit.text.toString()
                    //隐藏键盘
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
                true
            } else {
                false
            }
        }
    }

    /**
     * 控件监听事件
     */
    private val onClickListener = View.OnClickListener { view ->
        when(view.id) {

            //返回按钮
            R.id.sBack -> {
                finish()
            }

            //搜索按钮
            R.id.sBtn -> {
                if(sEdit.text.toString().equals("")) {
                    toast(getString(R.string.search_not_empty))
                } else {
                    searchPresenter.getSearchList(0, sEdit.text.toString())
                    search_swipe.isRefreshing = true
                    searchKey = sEdit.text.toString()
                    //隐藏键盘
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
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
                    if (isLogin) {
                        val collect = data.collect
                        data.collect = !collect
                        searchAdapter.setData(position, data)
                        if(data.chapterName.equals("官方")) {
                            //站内文章
                            searchPresenter.collectArticle(data.id, !collect, true,data.title, data.author, data.link)
                        } else {
                            //站外文章
                            searchPresenter.collectArticle(data.id, !collect, false, data.title, data.author, data.link)
                        }
                    } else {
                        toast(getString(R.string.login_please_login))
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
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        searchKey?.let {
            searchAdapter.setEnableLoadMore(false)
            search_swipe.isRefreshing = true
            searchPresenter.getSearchList(k = it)
        } ?: let {
            search_swipe.isRefreshing = false
            toast(getString(R.string.search_not_empty))
        }
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

    override fun getSearchListSuccess(result: HomeListResponse) {
        result.data.datas?.let {
            searchAdapter.run {
                // 列表总数
                val total = result.data.total
                // 当前总数
                if (result.data.offset >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }
                if (search_swipe.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
                toast(getString(R.string.get_data_success))
            }
        }
        search_swipe.isRefreshing = false
    }

    override fun getSearchListFailed(errorMessage: String?) {
        searchAdapter.setEnableLoadMore(false)
        search_swipe.isRefreshing = false
        searchAdapter.loadMoreFail()
        toast(getString(R.string.get_data_error))
    }

    override fun getSearchListZero() {
        toast(getString(R.string.get_data_zero))
        search_swipe.isRefreshing = false
    }

    override fun getSearchListSmall(result: HomeListResponse) {
        result.data.datas?.let {
            searchAdapter.run {
                replaceData(it)
                loadMoreEnd()
                loadMoreComplete()
                setEnableLoadMore(false)
            }
        }
        search_swipe.isRefreshing = false
    }

    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        toast(
                if (isAdd) getString(R.string.collect_success)
                else getString(R.string.unCollect_success)
        )
    }

    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        toast(
                if (isAdd) getString(R.string.collect_fail)
                else getString(R.string.unCollect_fail)
        )
    }
}