package wanandroid.xuboyu.com.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_other.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseFragment
import wanandroid.xuboyu.com.wanandroid.ui.activity.TypeListActivity
import wanandroid.xuboyu.com.wanandroid.ui.activity.UseWebListActivity

/**
 * use：其他模块
 * author: XuBoYu
 * time: 2019/8/1
 **/
class OtherFragment : BaseFragment() {

    private var mainView: View? = null

    override fun cancelRequest() {

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainView ?: let {
            mainView = inflater.inflate(R.layout.fragment_other, container, false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        zstx_img.setOnClickListener(onClickListener)
        cywz_img.setOnClickListener(onClickListener)
        gzh_img.setOnClickListener(onClickListener)
        xm_img.setOnClickListener(onClickListener)

    }

    private val onClickListener = View.OnClickListener { view ->
        when(view.id) {

            R.id.zstx_img -> {
                Intent(activity, TypeListActivity::class.java).run {
                    startActivity(this)
                }
            }

            R.id.cywz_img -> {
                Intent(activity, UseWebListActivity::class.java).run {
                    startActivity(this)
                }
            }

            R.id.gzh_img -> {

            }

            R.id.xm_img -> {

            }
        }
    }

}