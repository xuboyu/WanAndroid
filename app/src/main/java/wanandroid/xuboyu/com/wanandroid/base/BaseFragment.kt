package wanandroid.xuboyu.com.wanandroid.base

import android.app.Fragment

/**
 * use：Fragment基类
 * author: XuBoYu
 * time: 2019/4/19
 **/

abstract class BaseFragment : Fragment() {

    protected var isFirst: Boolean = true

    /**
     * 取消请求
     */
    protected abstract fun cancelRequest()

    override fun onDestroyView() {
        super.onDestroyView()
        cancelRequest()
    }

}