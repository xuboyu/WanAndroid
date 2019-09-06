package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：更新TODO Response
 * author: XuBoYu
 * time: 2019/9/5
 **/
data class BaseTodoResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: Data
){
    data class Data(
            var title: String,
            var status: Int,
            var priority: Int,
            var dateStr: String,
            var content: String,
            var date: String,
            var userId: Int,
            var type: Int
    )
}
