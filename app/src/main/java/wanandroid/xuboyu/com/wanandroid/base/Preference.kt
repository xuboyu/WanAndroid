package wanandroid.xuboyu.com.wanandroid.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import wanandroid.xuboyu.com.wanandroid.common.Constant
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * use：Preference操作类
 * author: XuBoYu
 * time: 2019/7/3
 **/
class Preference<T>(private val name: String,private val default: T) : ReadWriteProperty<Any?, T> {

    companion object {
        lateinit var preference: SharedPreferences
        /**
         * init Context
         */
        fun setContext(context: Context) {
            preference = context.getSharedPreferences(
                context.packageName + Constant.SHARED_NAME,
                Context.MODE_PRIVATE
            )
        }

        fun clear() {
            preference.edit().clear().apply()
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(name, default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putPreference(name, default)

    @Suppress("UNCHECKED_CAST")
    private fun <U> findPreference(name: String, default: U) : U = with(preference) {
        val res: Any = when (default) {
            is Long -> getLong(name,default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalAccessException("SharedPreferences无法使用该类型")
        }
        res as U
    }

    @SuppressLint("CommitPrefEdits")
    private fun <U> putPreference(name: String, value: U) = with(preference.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("SharedPreferences无法使用该类型")
        }.apply()
    }
}