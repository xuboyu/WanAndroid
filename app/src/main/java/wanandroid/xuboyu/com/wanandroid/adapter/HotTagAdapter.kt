package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.HotSearchResponse
import wanandroid.xuboyu.com.wanandroid.getRandomColor

/**
 * use：热门标签适配器
 * author: XuBoYu
 * time: 2019/8/6
 **/
class HotTagAdapter(context: Context, datas: List<HotSearchResponse.Data>) :
        TagAdapter<HotSearchResponse.Data>(datas) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(parent: FlowLayout, position: Int, data: HotSearchResponse.Data): View {
        return (inflater.inflate(R.layout.hot_search_item, parent, false) as TextView).apply {
            text = data.name
            val parseColor = try {
                Color.parseColor(getRandomColor())
            } catch (_: Exception) {
                @Suppress("DEPRECATION")
                context.resources.getColor(R.color.colorAccent)
            }
            setTextColor(parseColor)
        }
    }
}