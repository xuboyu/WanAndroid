package wanandroid.xuboyu.com.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar

/**
 * use：Activity基类
 * author: XuBoYu
 * time: 2019/4/12
 **/

abstract class BaseActivity : AppCompatActivity() {

    //返回键点击时间间隙
    var exitTime : Long = 0

    protected lateinit var immersionBar: ImmersionBar

    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())//绑定界面
        initImmersionBar()//初始化bar
    }

    open protected fun initImmersionBar() {
        //ImmersionBar初始化
        immersionBar = ImmersionBar.with(this)
        immersionBar.init()
    }

    protected abstract fun setLayoutId(): Int

    /**
     * 取消请求
     */
    protected abstract fun cancelRequest()

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        cancelRequest()
    }

    override fun finish() {
        // if not finish
        if (!isFinishing) {
            super.finish()
            hideSoftKeyBoard()
        }
    }

    private fun hideSoftKeyBoard() {
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 2)
        }
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     */
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
//            if (System.currentTimeMillis() - exitTime > 2000) {
//                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
//                exitTime = System.currentTimeMillis()
//            } else {
//                finish()
//                System.exit(0)
//            }
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

}