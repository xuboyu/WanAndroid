package wanandroid.xuboyu.com.wanandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse
import wanandroid.xuboyu.com.wanandroid.ui.fragment.TypeArticleFragment


/**
 * use：分类适配器
 * author: XuBoYu
 * time: 2019/7/30
 **/

class TypeArticlePagerAdapter(val list: List<TreeListResponse.Data.Children>, fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
    private val listFragment = mutableListOf<Fragment>()

    init {
        list.forEach { listFragment.add(TypeArticleFragment.newInstance(it.id)) }
    }

    override fun getItem(position: Int): Fragment = listFragment[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence = list[position].name

    override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE
}