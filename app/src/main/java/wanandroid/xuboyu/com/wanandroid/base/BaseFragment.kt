package wanandroid.xuboyu.com.wanandroid.base

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import com.gyf.barlibrary.ImmersionBar

/**
 * use：Fragment基类
 * author: XuBoYu
 * time: 2019/4/19
 **/

abstract class BaseFragment : Fragment() {

    protected var isFirst: Boolean = true

    protected lateinit var immersionBar: ImmersionBar

    private val imm: InputMethodManager by lazy {
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    open protected fun initImmersionBar() {
        //ImmersionBar初始化
        immersionBar = ImmersionBar.with(this)
        immersionBar.init()
    }

    /**
     * 取消请求
     */
    protected abstract fun cancelRequest()

    override fun onDestroyView() {
        super.onDestroyView()
        cancelRequest()
    }

}