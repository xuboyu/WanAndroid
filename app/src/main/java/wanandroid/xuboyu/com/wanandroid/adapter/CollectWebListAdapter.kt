package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.WebData

/**
 * use：收藏网址列表适配器
 * author: XuBoYu
 * time: 2019/8/1
 **/
class CollectWebListAdapter(val context: Context, datas: MutableList<WebData>) :
        BaseQuickAdapter<WebData, BaseViewHolder>(R.layout.web_list_item, datas) {
    override fun convert(helper: BaseViewHolder?, item: WebData?) {
        item ?: return
        if (helper != null) {
            @Suppress("DEPRECATION")
            helper.setText(R.id.webName, item.name)
//                    .addOnClickListener(R.id.webItemArrow)
        }
    }
}