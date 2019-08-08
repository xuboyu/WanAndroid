package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：公众号json
 * author: XuBoYu
 * time: 2019/8/8
 **/
class GzhResponse (
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>?
) {
    data class Data(
//            var children: Int,
            var courseId: Int,
            var id: Int,
            var name: String,
            var order: Int,
            var parentChapterId: Int,
            var userControlSetTop: String,
            var visible: Int
    )
}