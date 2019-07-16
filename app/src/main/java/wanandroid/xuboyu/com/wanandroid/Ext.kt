package wanandroid.xuboyu.com.wanandroid

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.JobCancellationException
import wanandroid.xuboyu.com.wanandroid.common.Constant
import kotlin.text.StringBuilder

/**
 * use：拓展方法
 * author: XuBoYu
 * time: 2019/7/2
 **/

/**
 * LayoutInflater.from(this).inflate
 * @param resource layoutId
 * @return View
 */
fun Context.inflater(@LayoutRes resource: Int): View =
        LayoutInflater.from(this).inflate(resource, null)

/**
 * Log
 */
fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: tag)
}

/**
 * toast
 */
fun Context.toast(content: String) {
    Constant.showToast?.apply {
        setText(content)
        show()
    } ?: run {
        Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT).apply {
            Constant.showToast = this
        }.show()
    }
}


/**
 * 异常捕获
 */
inline fun tryCatch(catchBlock: (Throwable) -> Unit = {}, tryBlock: () -> Unit) {
    try {
        tryBlock()
    } catch (_: JobCancellationException) {

    } catch (t: Throwable) {
        catchBlock(t)
    }
}

/**
 * 活动取消
 */
fun Deferred<Any>?.cancelByActivite() = this?.run {
    tryCatch {
        //判断job协程状态
        if (isActive) {
            //取消job执行
            cancel()
        }
    }
}

/**
 * 保存 cookie 操作
 */
fun encodeCookie(cookies: List<String>) : String {
    val sb = StringBuilder()
    val set = HashSet<String>()
    cookies.map{
                cookie -> cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

    val ite = set.iterator()
    while (ite.hasNext()) {
        val cookie = ite.next()
        sb.append(cookie).append(";")
    }

    val last = sb.lastIndexOf(";")
    if (sb.length - 1 == last) {
        sb.deleteCharAt(last)
    }

    return sb.toString()
}