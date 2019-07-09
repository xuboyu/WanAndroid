package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.app.Fragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import wanandroid.xuboyu.com.wanandroid.ui.fragment.MyFragment

class MainActivity : BaseActivity() {

    private var myFragment: MyFragment? = null
    private var currentIndex = 0

    override fun setLayoutId(): Int = R.layout.activity_main

    override fun cancelRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.navigation_home
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when(fragment) {
            is MyFragment -> myFragment ?: let { myFragment = fragment }
        }
    }



    /**
     * 显示对应Fragment
     */
    private fun setFragment(index: Int) {
        fragmentManager.beginTransaction().apply {
//            homeFragment ?: let {
//                HomeFragment().let {
//                    homeFragment = it
//                    add(R.id.content, it)
//                }
//            }
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
//                    toolbar.title = getString(R.string.app_name)
//                    homeFragment?.let {
//                        this.show(it)
//                    }
                }
                R.id.navigation_type -> {
//                    toolbar.title = getString(R.string.title_dashboard)
//                    typeFragment?.let {
//                        this.show(it)
//                    }
                }
                R.id.navigation_my -> {
//                    toolbar.title = getString(R.string.hot_title)
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
//        homeFragment?.let {
//            transaction.hide(it)
//        }
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
//                        if (currentIndex == R.id.navigation_home) {
//                            homeFragment?.smoothScrollToPosition()
//                        }
//                        currentIndex = R.id.navigation_home
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
