package wanandroid.xuboyu.com.wanandroid.bean

/**
 * use：单个结果
 * author: XuBoYu
 * time: 2019/7/12
 **/
data class Datas (
    var id: Int,
    var userId: Int,
    var originId: Int,
    var title: String,
    var superChapterId: Int,
    var superChapterName: String?,
    var chapterId: Int,
    var chapterName: String?,
    var envelopePic: Any,
    var link: String,
    var author: String,
    var origin: Any,
    var publishTime: Long,
    var zan: Any,
    var desc: Any,
    var visible: Int,
    var niceDate: String,
    var courseId: Int,
    var collect: Boolean
)