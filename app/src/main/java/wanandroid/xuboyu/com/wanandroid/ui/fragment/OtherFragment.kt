package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.fragment_other.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.CommonAdapter
import wanandroid.xuboyu.com.wanandroid.adapter.HotTagAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.bean.HotSearchResponse
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.inflater
import wanandroid.xuboyu.com.wanandroid.presenter.OtherFragmentPresenterImpl
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.ui.activity.SearchActivity
import wanandroid.xuboyu.com.wanandroid.ui.activity.TypeListActivity
import wanandroid.xuboyu.com.wanandroid.ui.activity.UseWebListActivity
import wanandroid.xuboyu.com.wanandroid.view.OhterFragmentView

/**
 * use：其他模块
 * author: XuBoYu
 * time: 2019/8/1
 **/
class OtherFragment : BaseFragment(), OhterFragmentView {

    private var mainView: View? = null

    private val datas = mutableListOf<HotSearchResponse.Data>()

    private lateinit var hotTagFlowLayout: TagFlowLayout

    private lateinit var flowLayout: LinearLayout

    private val otherFragmentPresenter: OtherFragmentPresenterImpl by lazy {
        OtherFragmentPresenterImpl(this)
    }

    /**
     * 热门词汇
     */
    private val hotTagDatas = mutableListOf<HotSearchResponse.Data>()

    /**
     * 热词适配器
     */
    private val hotTagAdapter: HotTagAdapter by lazy {
        HotTagAdapter(activity, hotTagDatas)
    }

    /**
     * common adapter
     */
    private val commonAdapter: CommonAdapter by lazy {
        CommonAdapter(activity, datas)
    }

    override fun cancelRequest() {
        otherFragmentPresenter.cancelRequest()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainView ?: let {
            mainView = inflater.inflate(R.layout.fragment_other, container, false)
            flowLayout = activity.inflater(R.layout.common_hot) as LinearLayout
            hotTagFlowLayout = flowLayout.findViewById<TagFlowLayout>(R.id.hsFlowLayout)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        hotTagFlowLayout.run {
            adapter = hotTagAdapter
            setOnTagClickListener(onHotTagClickListener)
        }

        commonRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = commonAdapter
        }
        commonAdapter.run {
            bindToRecyclerView(commonRecyclerView)
            addHeaderView(flowLayout)
        }

        zstx_img.setOnClickListener(onClickListener)
        cywz_img.setOnClickListener(onClickListener)
        gzh_img.setOnClickListener(onClickListener)
        xm_img.setOnClickListener(onClickListener)
        refresh.setOnClickListener(onClickListener)

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isFirst) {
            otherFragmentPresenter.getHSList()
            isFirst = false
        }
    }

    private val onClickListener = View.OnClickListener { view ->
        when(view.id) {
            //知识体系按钮
            R.id.zstx_img -> {
                Intent(activity, TypeListActivity::class.java).run {
                    startActivity(this)
                }
            }
            //热门网址按钮
            R.id.cywz_img -> {
                Intent(activity, UseWebListActivity::class.java).run {
                    startActivity(this)
                }
            }
            //公众号推荐按钮
            R.id.gzh_img -> {

            }
            //项目推荐按钮
            R.id.xm_img -> {

            }

            R.id.refresh -> {
                otherFragmentPresenter.getHSList()
            }
        }
    }

    override fun getHSListSuccess(result: HotSearchResponse) {
        result.data?.let {
            hotTagDatas.clear()
            hotTagDatas.addAll(it)
            hotTagAdapter.notifyDataChanged()
        }
        activity.toast(getString(R.string.get_data_success))
    }

    override fun getHSListFailed(errorMessage: String?) {
        activity.toast(getString(R.string.get_data_error))
    }

    override fun getHSListZero() {
        activity.toast(getString(R.string.get_data_zero))
    }

    /**
     * hot onCommonUseTagClickListener
     */
    private val onHotTagClickListener = TagFlowLayout.OnTagClickListener { _, position, _ ->
        if (hotTagDatas.size != 0) {
            Intent(activity, SearchActivity::class.java).run {
                putExtra(Constant.SEARCH_KEY, hotTagDatas[position].name)
                putExtra(Constant.IS_START_SEARCH, true)
                startActivity(this)
            }
        }
        true
    }

}