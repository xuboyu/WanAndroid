package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：热搜response bean
 * author: XuBoYu
 * time: 2019/8/6
 **/
data class HotSearchResponse (
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>?
) {
    data class Data(
            var id: Int,
            var name: String,
            var link: Any,
            var visible: Int,
            var order: Int
    )
}