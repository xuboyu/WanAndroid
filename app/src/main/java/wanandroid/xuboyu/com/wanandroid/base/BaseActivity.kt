package wanandroid.xuboyu.com.wanandroid.base

import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Toast

/**
 * use：Activity基类
 * author: XuBoYu
 * time: 2019/4/12
 **/

abstract class BaseActivity : AppCompatActivity() {

    //返回键点击时间间隙
    var exitTime : Long = 0

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}