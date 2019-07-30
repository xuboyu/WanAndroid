package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.Datas
import kotlin.math.roundToInt

/**
 * use：首页列表适配器
 * author: XuBoYu
 * time: 2019/7/15
 **/
class HomeAdapter(val context: Context, datas: MutableList<Datas>) :
    BaseQuickAdapter<Datas, BaseViewHolder>(R.layout.home_list_item, datas){
    override fun convert(helper: BaseViewHolder?, item: Datas?) {
        var zan : String = "0"
        var zan2 : Double = 0.0
        item ?: return
        zan = item.zan.toString()
        zan2 = zan.toDouble()
        if (helper != null) {
            @Suppress("DEPRECATION")
            helper.setText(R.id.item_title, item.title)
                    .setText(R.id.user, item.author)
                    .setText(R.id.item_kind, item.superChapterName+"/"+item.chapterName)
                    .addOnClickListener(R.id.item_kind)
                    .setTextColor(R.id.item_kind, context.resources.getColor(R.color.colorPrimary))
                    .linkify(R.id.item_kind)
                    .setText(R.id.item_time, item.niceDate)
                    .setText(R.id.item_like, zan2.roundToInt().toString())
                    .setImageResource(
                            R.id.item_collect,
                            if (item.collect) R.drawable.like else R.drawable.no_like
                    )
                    .addOnClickListener(R.id.item_collect)
        }
    }

}