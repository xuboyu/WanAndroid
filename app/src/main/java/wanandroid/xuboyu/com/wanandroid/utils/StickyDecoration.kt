package wanandroid.xuboyu.com.wanandroid.utils

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import android.view.View
import wanandroid.xuboyu.com.wanandroid.R
import kotlin.math.max

/**
 * use：列表分类间隔绘制
 * author: XuBoYu
 * time: 2019/9/3
 **/
class StickyDecoration(context: Context, decorationCallback: DecorationCallback) : RecyclerView.ItemDecoration() {

    private var callback: DecorationCallback? = decorationCallback
    private var textPaint: TextPaint? = null
    private var paint: Paint? = null
    private var topHead: Int = 0
    private var topHeadD: Int = 0

    init {
        paint = Paint()
        paint!!.color = ContextCompat.getColor(context, R.color.bg_header)
        textPaint = TextPaint()
        textPaint!!.typeface = Typeface.DEFAULT
        textPaint!!.isFakeBoldText = false
        textPaint!!.isAntiAlias = true
        textPaint!!.textSize = 35f
        textPaint!!.color = ContextCompat.getColor(context, R.color.colorPrimary)
        textPaint!!.textAlign = Paint.Align.LEFT
        topHead = context.resources.getDimensionPixelSize(R.dimen.head_top)
        topHeadD = context.resources.getDimensionPixelSize(R.dimen.head_top_d)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent!!.getChildAdapterPosition(view)
        val data : String = callback!!.getData(position)
        if (TextUtils.isEmpty(data) || TextUtils.equals(data,"")) {
            return
        }
        //同组的第一个添加日期padding
        if (position == 0 || isHeader(position)) {
            outRect!!.top = topHead
        } else {
            outRect!!.top = topHeadD
        }
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
        //获取当前可见的item的数量
        val childCount = parent!!.childCount
        //获取所有的的item个数
//        val itemCount = state!!.itemCount
        val itemCount = parent.adapter.itemCount
        val left : Int = parent.left + parent.paddingLeft
        val right : Int = parent.right + parent.paddingRight
        var preData : String?
        var currentDate : String? = null
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            val textLine = callback!!.getData(position)
            preData = currentDate
            currentDate = callback!!.getData(position)
            if (TextUtils.isEmpty(currentDate) || TextUtils.equals(currentDate, preData)) {
                continue
            }
            if (TextUtils.isEmpty(textLine)) {
                continue
            }
            val viewBottom = view.bottom
            var textY = max(topHead, view.top).toFloat()
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                val nextData = callback!!.getData(position + 1)
                if (currentDate != nextData && viewBottom < textY) {
                    //组内最后一个view进入了header
                    textY = viewBottom.toFloat()
                }
            }
            val rect = Rect(left, textY.toInt() - topHead, right, textY.toInt())
            c!!.drawRect(rect, paint!!)
            //绘制文字基线，文字的的绘制是从绘制的矩形底部开始的
            val fontMetrics = textPaint!!.fontMetrics
            val baseline = ((rect.bottom + rect.top).toFloat() - fontMetrics.bottom - fontMetrics.top) / 2
            textPaint!!.textAlign = Paint.Align.CENTER//文字居中
            //绘制文本
            c.drawText(textLine, 100F, baseline, textPaint!!)
        }
    }

    //判断是否为头部
    private fun isHeader(pos: Int): Boolean {
        return if (pos == 0) {
            true
        } else {
            val preData = callback!!.getData(pos - 1)
            val data = callback!!.getData(pos)
            preData != data
        }
    }

    interface DecorationCallback {
        fun getData(position: Int): String
    }
}