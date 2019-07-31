package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_type.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.adapter.TypeArticlePagerAdapter
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse.Data.Children
import wanandroid.xuboyu.com.wanandroid.common.Constant

/**
 * use：分类界面
 * author: XuBoYu
 * time: 2019/7/30
 **/
class TypeActivity : BaseActivity() {

    /**
     * toolbar 标题
     */
    private lateinit var firstTitle: String

    /**
     * 子标题数组
     */
    private val list = mutableListOf<Children>()

    /**
     * 判断跳转，true为home
     */
    private var target: Boolean = false

    /**
     * Adapter
     */
    private val typeArticlePagerAdapter: TypeArticlePagerAdapter by lazy {
        TypeArticlePagerAdapter(list, supportFragmentManager)
    }


    override fun cancelRequest() {

    }

    override fun setLayoutId(): Int = R.layout.activity_type

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.typeToolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        typeToolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        intent.extras?.let { extras ->
            target = extras.getBoolean(Constant.CONTENT_TARGET_KEY, false)
            extras.getString(Constant.CONTENT_TITLE_KEY)?.let {
                firstTitle = it
                typeToolbar.title = it
            }
            if (target) {
                list.add(
                        Children(
                                extras.getInt(Constant.CONTENT_CID_KEY, 0),
                                firstTitle, 0, 0, 0, 0, null
                        )
                )
            } else {
                extras.getSerializable(Constant.CONTENT_CHILDREN_DATA_KEY)?.let {
                    val data = it as TreeListResponse.Data
                    data.children?.let { children ->
                        list.addAll(children)
                    }
                }
            }
        }
        typeSecondViewPager.run {
            adapter = typeArticlePagerAdapter
        }
        typeTabs.run {
            setupWithViewPager(typeSecondViewPager)
        }
        typeSecondViewPager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(
                        typeTabs
                )
        )
        typeTabs.addOnTabSelectedListener(
                TabLayout.ViewPagerOnTabSelectedListener(
                        typeSecondViewPager
                )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
//            R.id.menuSearch -> {
//                Intent(this, SearchActivity::class.java).run {
//                    startActivity(this)
//                }
//                return true
//            }
//            R.id.menuShare -> {
//                Intent().run {
//                    action = Intent.ACTION_SEND
//                    putExtra(
//                            Intent.EXTRA_TEXT,
//                            getString(
//                                    R.string.share_type_url,
//                                    getString(R.string.app_name),
//                                    list[typeSecondTabs.selectedTabPosition].name,
//                                    list[typeSecondTabs.selectedTabPosition].id
//                            )
//                    )
//                    type = Constant.CONTENT_SHARE_TYPE
//                    startActivity(Intent.createChooser(this, getString(R.string.share_title)))
//                }
//                return true
//            }
        }

        return super.onOptionsItemSelected(item)
    }

}