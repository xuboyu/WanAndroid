package wanandroid.xuboyu.com.wanandroid.bean

import java.io.Serializable

/**
 * use：最新项目response
 * author: XuBoYu
 * time: 2019/8/9
 **/
data class ProjectResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: Data?
) {
    data class Data(
            var id: Int,
            var name: String,
            var link: Any,
            var visible: Int,
            var order: Int,
            var offset: Int,
            var size: Int,
            var total: Int,
            var pageCount: Int,
            var datas: List<CDatas>?
    ) {
        data class CDatas (
                var apkLink: String?,
                var author: String,
                var chapterId: Int,
                var chapterName: String,
                var collect: String,
                var courseId: Int,
                var desc: String,
                var envelopePic: String,
                var fresh: String,
                var id: Int,
                var link: String,
                var niceDate: String,
                var origin: String?,
                var prefix: String?,
                var projectLink: String,
                var publishTime: String,
                var superChapterId: Int,
                var superChapterName: String,
                var title: String,
                var type: Int,
                var userId: Int,
                var visible: Int,
                var zan: Int
        )
    }
}