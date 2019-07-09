package wanandroid.xuboyu.com.wanandroid.base

import android.app.Application

/**
 * use：BaseApplication
 * 全局初始化等等
 * author: XuBoYu
 * time: 2019/7/4
 **/
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Preference.setContext(applicationContext)
    }
}