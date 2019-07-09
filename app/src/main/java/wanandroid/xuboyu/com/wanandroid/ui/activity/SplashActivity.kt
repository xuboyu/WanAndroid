package wanandroid.xuboyu.com.wanandroid.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.base.BaseActivity

/**
 * use：闪屏界面
 * author: XuBoYu
 * time: 2019/4/12
 **/

class SplashActivity : AppCompatActivity() {

    var animalSet = AnimatorSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        //文字动画
        val objectAnimator : ObjectAnimator = ObjectAnimator.ofFloat(app_name, "rotationX", 0f, 360f)
        objectAnimator.duration = 2000
        objectAnimator.start()

        //图片动画
        val translationX: ObjectAnimator = ObjectAnimator.ofFloat(iv_splash, "translationX", 600f, 0f)
        val translationY = ObjectAnimator.ofFloat(iv_splash, "translationY", -100f, 90f, -80f, 70f, -60f, 50f)

        animalSet.apply {
            playTogether(translationX, translationY)
            duration = 2000
        }

        addListener()
    }

    private fun addListener() {
        animalSet.apply {
            start()
            addListener(object  : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    try {
                        Thread.sleep(500)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
        }
    }
}

