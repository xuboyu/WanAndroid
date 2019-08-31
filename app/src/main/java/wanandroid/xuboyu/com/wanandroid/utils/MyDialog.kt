package wanandroid.xuboyu.com.wanandroid.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import wanandroid.xuboyu.com.wanandroid.R

/**
 * use：自定义dialog
 * 解决含义edittext的dialog dismiss后键盘不消失的问题
 * author: XuBoYu
 * time: 2019/8/28
 **/
class MyDialog : Dialog {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        setCanceledOnTouchOutside(true)
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.BottomDialogAnimation)
    }

    override fun dismiss() {
        val view : View? = currentFocus
        if(view is TextView){
            val mInputMethodManager : InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss()
    }
}