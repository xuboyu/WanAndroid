package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：单个类型列表bean
 * author: XuBoYu
 * time: 2019/7/31
 **/
data class ArticleListResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: Data
)