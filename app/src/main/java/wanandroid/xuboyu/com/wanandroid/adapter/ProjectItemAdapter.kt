package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.ProjectResponse

/**
 * use：项目列表适配器
 * author: XuBoYu
 * time: 2019/8/9
 **/
class ProjectItemAdapter(val context: Context, datas: MutableList<ProjectResponse.Data.CDatas>) :
        BaseQuickAdapter<ProjectResponse.Data.CDatas, BaseViewHolder>(R.layout.project_item, datas) {
    override fun convert(helper: BaseViewHolder, item: ProjectResponse.Data.CDatas?) {
        item ?: return
        helper.setText(R.id.pTitle, item.title.trim())
                .setText(R.id.pContent, item.desc)
                .setText(R.id.pKind, item.chapterName)
                .setTextColor(R.id.pKind, context.resources.getColor(R.color.colorPrimary))
                .setText(R.id.pAuthor, item.author)
                .addOnClickListener(R.id.pKind)
        val imageView = helper.getView<ImageView>(R.id.pImage)
        Glide.with(context).load(item.envelopePic).placeholder(R.drawable.logo).into(imageView)
    }
}