package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.ProjectTypeResponse

/**
 * use：项目分类抽屉适配器
 * author: XuBoYu
 * time: 2019/8/26
 **/
class ProjectKindAdapter(val context: Context, datas: MutableList<ProjectTypeResponse.Data> ) :
        BaseQuickAdapter<ProjectTypeResponse.Data, BaseViewHolder>(R.layout.project_kind_item, datas) {

    private var selectProject: ProjectTypeResponse.Data? = null

    override fun convert(helper: BaseViewHolder, item: ProjectTypeResponse.Data?) {
        item ?: return
        helper.setText(R.id.p_k, item.name)
        if (selectProject?.id == item.id) {
            helper.setChecked(R.id.s_k, true)
        } else {
            helper.setChecked(R.id.s_k, false)
        }
    }

    fun setSelect(selectBean: ProjectTypeResponse.Data) {
        this.selectProject = selectBean
    }



}