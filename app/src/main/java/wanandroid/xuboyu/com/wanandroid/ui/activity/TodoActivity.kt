package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.MenuItem
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_todo.bottomNavigation
import kotlinx.android.synthetic.main.activity_todo.toolbar
import wanandroid.xuboyu.com.wanandroid.ui.fragment.DoneTodoFragment
import wanandroid.xuboyu.com.wanandroid.ui.fragment.MyFragment
import wanandroid.xuboyu.com.wanandroid.ui.fragment.UndoneTodoFragment

/**
 * use：TODO主界面
 * author: XuBoYu
 * time: 2019/9/6
 **/
class TodoActivity : BaseActivity() {

    private var undoneTodoFragment: UndoneTodoFragment? = null
    private var doneTodoFragment: DoneTodoFragment? = null
    private var currentIndex = 0

    private val fragmentManager by lazy {
        supportFragmentManager
    }

    override fun setLayoutId(): Int = R.layout.activity_todo

    override fun cancelRequest() {

    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.undone_todo
        }
        toolbar.run {
            title = getString(R.string.todo_text)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
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

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when(fragment) {
            is UndoneTodoFragment -> undoneTodoFragment ?: let { undoneTodoFragment = fragment }
            is DoneTodoFragment -> doneTodoFragment ?: let { doneTodoFragment = fragment }
        }
    }

    /**
     * 显示对应Fragment
     */
    private fun setFragment(index: Int) {
        fragmentManager.beginTransaction().apply {
            undoneTodoFragment ?: let {
                UndoneTodoFragment().let {
                    undoneTodoFragment = it
                    add(R.id.content, it)
                }
            }
            doneTodoFragment ?: let {
                DoneTodoFragment().let {
                    doneTodoFragment = it
                    add(R.id.content, it)
                }
            }
            hideFragment(this)
            when (index) {
                R.id.undone_todo -> {
                    undoneTodoFragment?.let {
                        this.show(it)
                    }
                }
                R.id.done_todo -> {
                    doneTodoFragment?.let {
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
        undoneTodoFragment?.let {
            transaction.hide(it)
        }
        doneTodoFragment?.let {
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
                    R.id.undone_todo -> {
//                        if (currentIndex == R.id.undone_todo) {
//                            undoneTodoFragment?.smoothScrollToPosition()
//                        }
                        currentIndex = R.id.undone_todo
                        true
                    }
                    R.id.done_todo -> {
//                        if (currentIndex == R.id.done_todo) {
//                            doneTodoFragment?.smoothScrollToPosition()
//                        }
                        currentIndex = R.id.undone_todo
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

}