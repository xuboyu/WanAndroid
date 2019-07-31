package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.Datas

/**
 * use：知识体系下单个列表item适配器
 * author: XuBoYu
 * time: 2019/7/31
 **/
class TypeArticleAdapter(val context: Context, datas: MutableList<Datas>) :
        BaseQuickAdapter<Datas, BaseViewHolder>(R.layout.home_list_item, datas) {
    override fun convert(helper: BaseViewHolder, item: Datas?) {
        item ?: return
        @Suppress("DEPRECATION")
        helper.setText(R.id.item_title, item.title)
                .setText(R.id.user, item.author)
                .setText(R.id.item_kind, item.superChapterName+"/"+item.chapterName)
                .addOnClickListener(R.id.item_kind)
                .setTextColor(R.id.item_kind, context.resources.getColor(R.color.colorPrimary))
                .linkify(R.id.item_kind)
                .setText(R.id.item_time, item.niceDate)
                .setImageResource(
                        R.id.item_collect,
                        if (item.collect) R.drawable.like else R.drawable.no_like
                )
                .addOnClickListener(R.id.item_collect)
                .addOnClickListener(R.id.item_share)
    }
}