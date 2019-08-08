package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.GzhResponse

/**
 * use：公众号列表适配器
 * author: XuBoYu
 * time: 2019/8/8
 **/
class GzhAdapter (val context: Context, datas: MutableList<GzhResponse.Data>) :
        BaseQuickAdapter<GzhResponse.Data, BaseViewHolder>(R.layout.gzh_item, datas) {
    override fun convert(helper: BaseViewHolder?, item: GzhResponse.Data?) {
        item ?: return
        if (helper != null) {
            @Suppress("DEPRECATION")
            helper.setText(R.id.gzhName, item.name)
        }
    }
}