package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.ui.fragment.HomeFragment
import wanandroid.xuboyu.com.wanandroid.ui.fragment.MyFragment
import wanandroid.xuboyu.com.wanandroid.ui.fragment.OtherFragment

class MainActivity : BaseActivity() {

    private var lastTime: Long = 0
    private var homeFragment: HomeFragment? = null
    private var myFragment: MyFragment? = null
    private var otherFragment: OtherFragment? = null
    private var currentIndex = 0

    private val fragmentManager by lazy {
        supportFragmentManager
    }

    override fun setLayoutId(): Int = R.layout.activity_main

    override fun cancelRequest() {

    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    /**
     * 返回键-退出
     */
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime < 2 * 1000) {
            super.onBackPressed()
            finish()
        } else {
            toast(getString(R.string.double_click_exit))
            lastTime = currentTime
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.navigation_home
        }
        toolbar.run {
            title = getString(R.string.title_home)
            setSupportActionBar(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //收藏网址
            R.id.menuSearch -> {
                Intent(this, SearchActivity::class.java).run {
                    this.putExtra(Constant.SEARCH_KEY, "")
                    this.putExtra(Constant.IS_START_SEARCH, false)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when(fragment) {
            is HomeFragment -> homeFragment ?: let { homeFragment = fragment }
            is OtherFragment -> otherFragment ?: let { otherFragment = fragment}
            is MyFragment -> myFragment ?: let { myFragment = fragment }
        }
    }

    /**
     * 显示对应Fragment
     */
    private fun setFragment(index: Int) {
        fragmentManager.beginTransaction().apply {
            homeFragment ?: let {
                HomeFragment().let {
                    homeFragment = it
                    add(R.id.content, it)
                }
            }
            otherFragment ?: let {
                OtherFragment().let {
                    otherFragment = it
                    add(R.id.content, it)
                }
            }
            myFragment ?: let {
                MyFragment().let {
                    myFragment = it
                    add(R.id.content, it)
                }
            }
            hideFragment(this)
            when (index) {
                R.id.navigation_home -> {
                    toolbar.title = getString(R.string.title_home)
                    homeFragment?.let {
                        this.show(it)
                    }
                }
                R.id.navigation_type -> {
                    toolbar.title = getString(R.string.title_system)
                    otherFragment?.let {
                        this.show(it)
                    }
                }
                R.id.navigation_my -> {
                    toolbar.title = getString(R.string.title_my)
                    myFragment?.let {
                        this.show(it)
                    }
                }
            }
        }.commit()
    }

    /**
     * 隐藏所有fragment
     */
    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment?.let {
            transaction.hide(it)
        }
        otherFragment?.let {
            transaction.hide(it)
        }
        myFragment?.let {
            transaction.hide(it)
        }
    }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                setFragment(item.itemId)
                return@OnNavigationItemSelectedListener when (item.itemId) {
                    R.id.navigation_home -> {
                        if (currentIndex == R.id.navigation_home) {
                            homeFragment?.smoothScrollToPosition()
                        }
                        currentIndex = R.id.navigation_home
                        true
                    }
                    R.id.navigation_type -> {
                        currentIndex = R.id.navigation_type
                        true
                    }
                    R.id.navigation_my -> {
                        currentIndex = R.id.navigation_my
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

}
