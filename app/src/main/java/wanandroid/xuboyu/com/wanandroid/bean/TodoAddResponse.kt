package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：新增todo Response
 * author: XuBoYu
 * time: 2019/8/28
 **/
data class TodoAddResponse(
    var errorCode: Int,
    var errorMsg: String?,
    var data: Data
) {
    data class Data(
            var title: String,
            var status: Int,
            var priority: Int,
            var dateStr: String,
            var content: String,
            var date: String,
            var userId: Int
    )
}
