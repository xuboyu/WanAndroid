package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.HotSearchResponse

/**
 * use：
 * author: XuBoYu
 * time: 2019/8/6
 **/

class CommonAdapter(val context: Context, datas: MutableList<HotSearchResponse.Data>) :
        BaseQuickAdapter<HotSearchResponse.Data, BaseViewHolder>(R.layout.hot_search_item, datas) {
    override fun convert(helper: BaseViewHolder, item: HotSearchResponse.Data?) {
        item ?: return
        helper.setText(R.id.commonItemTitle, item.name.trim())
    }
}