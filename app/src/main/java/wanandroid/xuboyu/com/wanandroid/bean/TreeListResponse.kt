package wanandroid.xuboyu.com.wanandroid.bean

import java.io.Serializable

/**
 * use：
 * author: XuBoYu
 * time: 2019/7/30
 **/
data class TreeListResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>
) {
    data class Data(
            var id: Int,
            var name: String,
            var courseId: Int,
            var parentChapterId: Int,
            var order: Int,
            var visible: Int,
            var children: List<Children>?
    ) : Serializable {
        data class Children(
                var id: Int,
                var name: String,
                var courseId: Int,
                var parentChapterId: Int,
                var order: Int,
                var visible: Int,
                var children: List<Children>?
        ) : Serializable
    }
}