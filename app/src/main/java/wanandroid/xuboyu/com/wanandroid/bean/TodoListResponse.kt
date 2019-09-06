package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：todo列表
 * author: XuBoYu
 * time: 2019/8/28
 **/
data class TodoListResponse(
    var errorCode: Int,
    var errorMsg: String?,
    var data: Data
){
    data class Data(
        var offset: Int,
        var size: Int,
        var total: Int,
        var pageCount: Int,
        var curPage: Int,
        var over: Boolean,
        var datas: List<Datas>?
    ) {
        data class Datas(
                var title: String,
                var status: Int,
                var priority: Int,
                var dateStr: String,
                var content: String,
                var date: String,
                var userId: Int,
                var type: Int,
                var id: Int
        )
    }
}