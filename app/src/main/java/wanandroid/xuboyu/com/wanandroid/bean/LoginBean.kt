package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：登录结果bean
 * author: XuBoYu
 * time: 2019/4/18
 **/
data class LoginBean(
    var errorCode: Int,
    var errorMsg: String?,
    var data: Data
) {
    data class Data(
            var id: Int,
            var username: String,
            var password: String,
            var icon: String?,
            var type: Int,
            var collectIds: List<Int>?
    )
}