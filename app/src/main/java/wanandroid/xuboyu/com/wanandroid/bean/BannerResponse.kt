package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：轮播图
 * author: XuBoYu
 * time: 2019/7/15
 **/
class BannerResponse (
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>?
) {
    data class Data(
            var id: Int,
            var url: String,
            var imagePath: String,
            var title: String,
            var desc: String,
            var isVisible: Int,
            var order: Int,
            var `type`: Int
    )
}