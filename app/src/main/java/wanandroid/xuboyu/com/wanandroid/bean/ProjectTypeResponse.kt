package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：项目类别response
 * author: XuBoYu
 * time: 2019/8/12
 **/
data class ProjectTypeResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>?
) {
    data class Data(
//            var children: List<ProjectTypeResponse>? ,
            var courseId: Int,
            var id: Int,
            var name: String,
            var order: Int,
            var parentChapterId: Int,
            var userControlSetTop: String,
            var visible: Int
    )
}