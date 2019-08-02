package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：收藏网址列表返回bean
 * author: XuBoYu
 * time: 2019/8/1
 **/
data class CollectWebListResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<WebData>?
)