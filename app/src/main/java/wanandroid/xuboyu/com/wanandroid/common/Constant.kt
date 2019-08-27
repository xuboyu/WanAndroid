package wanandroid.xuboyu.com.wanandroid.common

import android.widget.Toast

/**
 * use：公共参数定义
 * author: XuBoYu
 * time: 2019/4/12
 **/

object Constant {

    /* ================================== SharedPreferences 部分 ==================================*/

    /**
     * Share preferences name
     */
    const val SHARED_NAME = "_preferences"
    const val LOGIN_KEY = "login"
    const val USERNAME_KEY = "username"
    const val PASSWORD_KEY = "password"

    const val CID = "cid"

    /* ================================== END ==================================*/

    /* ================================== TIP ==================================*/

    /**
     * result null
     */
    const val RESULT_NULL = "result null!"

    /**
     * nickname
     */
    const val NICK_NAME_TEXT = "nickname"

    /**
     * password
     */
    const val PASSWORD_TEXT = "password"

    /**
     * Toast
     */
    @JvmField
    var showToast: Toast? = null

    /* ================================== END ==================================*/

    /* ================================== 接口部分 ==================================*/

    /**
     * baseUrl，支持https
     */
    const val REQUEST_BASE_URL = "https://wanandroid.com/"

    /**
     * 登录接口
     */
    const val LOGIN = "user/login"

    /**
     * 注册接口
     */
    const val REGISTER = "user/register"

    /**
     * 登出接口
     */
    const val LOGOUT = "logout/json"

    /**
     * 首页文章列表
     */
    const val HOMELIST = "/article/list/{page}/json"

    /**
     * 首页banner轮播图
     */
    const val HOMEBANNER = "/banner/json"

    /**
     * 收藏站内文章
     */
    const val ADDCOLLECTIN = "/lg/collect/{id}/json"

    /**
     * 收藏站外文章
     */
    const val ADDCOLLECTOUT = "/lg/collect/add/json"

    /**
     * 取消收藏文章
     * 首页文章列表调用
     */
    const val REMOVE_COLLECT = "/lg/uncollect_originId/{id}/json"

    /**
     * 取消收藏文章
     * 个人收藏列表调用
     */
    const val REMOVE_MY_COLLECT = "/lg/uncollect/{id}/json"

    /**
     * 获取个人收藏文章列表
     */
    const val GET_MY_COLLECT = "/lg/collect/list/{page}/json"

    /**
     * 知识体系
     */
    const val TREE_LIST = "/tree/json"

    /**
     * 知识体系下单个类型文章列表
     */
    const val TREE_TYPE_LIST = "/article/list/{page}/json"

    /**
     * 收藏网址列表
     */
    const val COLLECT_WEB_LIST = "/lg/collect/usertools/json"

    /**
     * 常用网址列表
     */
    const val WEB_LIST = "/friend/json"

    /**
     * 收藏网址
     */
    const val COLLECT_WEB = "/lg/collect/addtool/json"

    /**
     * 删除网址
     */
    const val DELETE_WEB = "/lg/collect/deletetool/json"

    /**
     * 搜索
     */
    const val SEARCH = "/article/query/{page}/json"

    /**
     * 搜索热词
     */
    const val SEARCH_HOT = "/hotkey/json"

    /**
     * 公众号列表
     */
    const val GZH = "/wxarticle/chapters/json"

    /**
     * 某个公众号文章列表
     */
    const val GZHLIST = "/wxarticle/list/{id}/{page}/json"

    /**
     * 项目分类
     */
    const val PROJECT_TYPE = "/project/tree/json"

    /**
     * 最新项目列表
     */
    const val NEW_PROJECT_LIST = "/article/listproject/{page}/json"

    /**
     * 某个分类下的项目列表
     */
    const val SOME_PROJECT_LIST = "/project/list/{page}/json"

    /* ================================== END ==================================*/

    /* ================================== 开关属性设置 ==================================*/

    /**
     * Debug
     */
    const val INTERCEPTOR_ENABLE = true

    /* ================================== END ==================================*/

    /* ================================== OTHER CODE ==================================*/

    /**
     * 一些界面跳转参数设置
     */
    const val MY_REQUEST_CODE = 100
    const val REGISTER_REQUEST_CODE = 100

    /**
     * 分享相关设置
     */
    const val CONTENT_SHARE_TYPE = "text/plain"
    //纯文本的形式，浏览器在获取到这种文件时并不会对其进行处理
    //text/html的意思是将文件的content-type设置为text/html的形式，浏览器在获取到这种文件时会自动调用html的解析器对文件进行相应的处理。

    /**
     * 跳转 url
     */
    const val CONTENT_URL_KEY = "url"
    /**
     * 跳转 title
     */
    const val CONTENT_TITLE_KEY = "title"
    /**
     * cid key
     */
    const val CONTENT_CID_KEY = "cid"
    /**
     * 文章 id
     */
    const val CONTENT_ID_KEY = "id"
    /**
     * 文章 userid
     */
    const val CONTENT_USER_ID = "userId"
    /**
     * 文章 收藏状态
     */
    const val CONTENT_IS_COLLECT = "collect"
    /**
     * 文章 作者
     */
    const val CONTENT_AUTHOR = "author"
    /**
     * 是否为home跳转
     */
    const val CONTENT_TARGET_KEY = "target"
    /**
     * childrenData key
     */
    const val CONTENT_CHILDREN_DATA_KEY = "childrenData"
    /**
     * 网址名
     */
    const val WEB_NAME = "name"
    /**
     * 网址链接
     */
    const val WEB_LINK = "link"
    /**
     * 搜索关键词
     */
    const val SEARCH_KEY = "searchKey"
    /**
     * 是否开始搜索
     */
    const val IS_START_SEARCH = "isStartSearch"
    /**
     * 公众号作者名
     */
    const val GZH_NAME = "name"
    /**
     * 公众号id
     */
    const val GZH_ID = "id"

    /* ================================== END ==================================*/
}