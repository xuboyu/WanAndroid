package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse

/**
 * use：轮播图适配器
 * author: XuBoYu
 * time: 2019/7/16
 **/
class BannerAdapter(val context: Context, datas: MutableList<BannerResponse.Data>) :
        BaseQuickAdapter<BannerResponse.Data, BaseViewHolder>(R.layout.fragment_home_banner_item, datas) {
    override fun convert(helper: BaseViewHolder, item: BannerResponse.Data?) {
        item ?: return
        helper.setText(R.id.bannerTitle, item.title.trim())
        val imageView = helper.getView<ImageView>(R.id.bannerImage)
        Glide.with(context).load(item.imagePath).placeholder(R.drawable.logo).into(imageView)
    }
}