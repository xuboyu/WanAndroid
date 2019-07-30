package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：收藏站外文章返回bean
 * author: XuBoYu
 * time: 2019/7/29
 **/
data class CollectOutResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: Datas
)