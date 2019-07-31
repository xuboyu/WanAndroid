package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse

/**
 * use：知识体系列表适配器
 * author: XuBoYu
 * time: 2019/7/31
 **/
class TypeAdapter(val context: Context, datas: MutableList<TreeListResponse.Data>) :
        BaseQuickAdapter<TreeListResponse.Data, BaseViewHolder>(R.layout.type_list_item, datas) {
    override fun convert(helper: BaseViewHolder, item: TreeListResponse.Data?) {
        item ?: return
        helper.setText(R.id.typeItemFirst, item.name)
        item.children?.let { children ->
            helper.setText(
                    R.id.typeItemSecond,
                    children.joinToString("     ", transform = { child ->
                        child.name
                    })
            )
        }
    }
}