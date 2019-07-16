package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.app.Fragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*

import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.base.Preference
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.toast
import wanandroid.xuboyu.com.wanandroid.ui.fragment.HomeFragment
import wanandroid.xuboyu.com.wanandroid.ui.fragment.MyFragment

class MainActivity : BaseActivity() {

    private var lastTime: Long = 0
    private var homeFragment: HomeFragment? = null
    private var myFragment: MyFragment? = null
    private var currentIndex = 0

    override fun setLayoutId(): Int = R.layout.activity_main

    override fun cancelRequest() {
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    /**
     * 退出
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

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when(fragment) {
            is HomeFragment -> homeFragment ?: let { homeFragment = fragment }
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
//            typeFragment ?: let {
//                TypeFragment().let {
//                    typeFragment = it
//                    add(R.id.content, it)
//                }
//            }
//            commonUseFragment ?: let {
//                CommonUseFragment().let {
//                    commonUseFragment = it
//                    add(R.id.content, it)
//                }
//            }
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
//                    toolbar.title = getString(R.string.title_dashboard)
//                    typeFragment?.let {
//                        this.show(it)
//                    }
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
    private fun hideFragment(transaction: android.app.FragmentTransaction) {
        homeFragment?.let {
            transaction.hide(it)
        }
//        typeFragment?.let {
//            transaction.hide(it)
//        }
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
//                            homeFragment?.smoothScrollToPosition()
                        }
                        currentIndex = R.id.navigation_home
                        true
                    }
                    R.id.navigation_type -> {
//                        if (currentIndex == R.id.navigation_type) {
//                            typeFragment?.smoothScrollToPosition()
//                        }
//                        currentIndex = R.id.navigation_type
                        true
                    }
                    R.id.navigation_my -> {
                        if(currentIndex == R.id.navigation_my) {
//                            myFragment?.smoothScrollToPosition()
                        }
                        currentIndex = R.id.navigation_my
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

}
