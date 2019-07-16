package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：结果集合
 * author: XuBoYu
 * time: 2019/7/12
 **/
data class Data(
        var offset: Int,
        var size: Int,
        var total: Int,
        var pageCount: Int,
        var curPage: Int,
        var over: Boolean,
        var datas: List<Datas>?
)