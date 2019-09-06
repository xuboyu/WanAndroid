package wanandroid.xuboyu.com.wanandroid.retrofit

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.*
import wanandroid.xuboyu.com.wanandroid.bean.*
import wanandroid.xuboyu.com.wanandroid.common.Constant

/**
 * use：Retrofit请求api配置
 * author: XuBoYu
 * time: 2019/4/18
 */
interface RetrofitService {

    /**
     * 登录
     * @param username username
     * @param password password
     * @return Deferred<LoginResponse>
     */
    @POST(Constant.LOGIN)
    @FormUrlEncoded
    fun loginWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String
    ): Deferred<LoginResponse>

    /**
     * 注册
     * @param username username
     * @param password password
     * @param repassword repassword
     * @return Deferred<LoginResponse>
     */
    @POST(Constant.REGISTER)
    @FormUrlEncoded
    fun registerWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassword: String
    ): Deferred<LoginResponse>

    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */
    @GET(Constant.HOMELIST)
    fun getHomeList(
            @Path("page") page: Int
    ): Deferred<HomeListResponse>

    /**
     * 首页Banner
     * @return BannerResponse
     */
    @GET(Constant.HOMEBANNER)
    fun getBanner(): Deferred<BannerResponse>

    /**
     * 收藏站内文章
     * @param id 收藏文章id
     */
    @POST(Constant.ADDCOLLECTIN)
    fun addColltectIn(
            @Path("id") id: Int
    ): Deferred<HomeListResponse>

    /**
     * 收藏站外文章
     * @param title 文章标题
     * @param author 文章作者
     * @param link 文章链接
     */
    @POST(Constant.ADDCOLLECTOUT)
    @FormUrlEncoded
    fun addCollectOut(
            @Field("title") title: String,
            @Field("author") author: String,
            @Field("link") link: String
    ): Deferred<HomeListResponse>

    /**
     * 取消收藏-首页文章列表
     * @param id 获取文章id
     * @param originId 文章本身id，传-1
     */
    @POST(Constant.REMOVE_COLLECT)
    @FormUrlEncoded
    fun removeCollect(
            @Path("id") id: Int,
            @Field("originId") originId: Int = -1
    ): Deferred<HomeListResponse>

    /**
     * 获取自己收藏的文章列表
     * @param page page
     * @return Deferred<HomeListResponse>
     */
    @GET(Constant.GET_MY_COLLECT)
    fun getLikeList(
            @Path("page") page: Int
    ): Deferred<HomeListResponse>

    /**
     * 取消收藏-个人收藏列表
     */
    @POST(Constant.REMOVE_MY_COLLECT)
    @FormUrlEncoded
    fun removeMyCollect(
            @Path("id") id: Int,
            @Field("originId") originId: Int
    ): Deferred<HomeListResponse>

    /**
     * 知识体系
     */
    @GET(Constant.TREE_LIST)
    fun getTreeList(): Deferred<TreeListResponse>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page page
     * @param cid cid
     */
    @GET(Constant.TREE_TYPE_LIST)
    fun getArticleList(
            @Path("page") page: Int,
            @Query("cid") cid: Int
    ): Deferred<ArticleListResponse>

    /**
     * 收藏网址列表
     */
    @GET(Constant.COLLECT_WEB_LIST)
    fun getCollectWebList(): Deferred<CollectWebListResponse>

    /**
     * 常用网址
     */
    @GET(Constant.WEB_LIST)
    fun getUseWebList(): Deferred<CollectWebListResponse>

    /**
     * 收藏网址
     * @param name 网址名
     * @param link 网址链接
     */
    @POST(Constant.COLLECT_WEB)
    @FormUrlEncoded
    fun collectWeb(
            @Field("name") name: String,
            @Field("link") link: String
    ): Deferred<CollectWebListResponse>

    /**
     * 删除收藏网址
     * @param id 网址id
     */
    @POST(Constant.DELETE_WEB)
    @FormUrlEncoded
    fun deleteWeb(
            @Field("id") id: Int
    ): Deferred<ArticleListResponse>

    /**
     * 热搜词汇
     */
    @GET(Constant.SEARCH_HOT)
    fun getHotSearch(): Deferred<HotSearchResponse>

    /**
     * 搜索
     * @param page 页码
     * @param k 搜索关键词
     */
    @POST(Constant.SEARCH)
    @FormUrlEncoded
    fun getSearchList(
            @Path("page") page: Int,
            @Field("k") k: String
    ): Deferred<HomeListResponse>

    /**
     * 公众号列表
     */
    @GET(Constant.GZH)
    fun getGzh(): Deferred<GzhResponse>

    /**
     * 获取某个公众号的文章
     */
    @GET(Constant.GZHLIST)
    fun getGzhList(
            @Path("id") id: Int,
            @Path("page") page: Int = 0
    ): Deferred<HomeListResponse>

    /**
     * 获取项目分类
     */
    @GET(Constant.PROJECT_TYPE)
    fun getProjectTypeList(): Deferred<ProjectTypeResponse>

    /**
     * 获取最新项目列表
     */
    @GET(Constant.NEW_PROJECT_LIST)
    fun getNewProjectList(
            @Path("page") page: Int = 0
    ): Deferred<ProjectResponse>

    /**
     * 获取某个分类的项目列表
     */
    @GET(Constant.SOME_PROJECT_LIST)
    fun getTypeProjectList(
            @Path("page") page: Int = 0,
            @Query("cid") cid: Int
    ): Deferred<ProjectResponse>

    /**
     * 获取TODO列表
     * 全部条件调用
     */
    @GET(Constant.TODO_LIST)
    fun getTodoList(
            @Path("page") page: Int = 1,
            @Query("status") status: Int,
            @Query("type") type: Int,
            @Query("priority") priority: Int,
            @Query("orderby") orderby: Int
    ): Deferred<TodoListResponse>

    /**
     * 获取TODO列表
     * 全部条件调用
     */
    @GET(Constant.TODO_LIST)
    fun getTodoListDefalut(
            @Path("page") page: Int = 1,
            @Query("status") status: Int
    ): Deferred<TodoListResponse>


    /**
     * 新增一条TODO
     */
    @POST(Constant.ADD_TODO)
    @FormUrlEncoded
    fun addTodo(
            @Field("title") title: String,
            @Field("content") content: String,
            @Field("date") date: String,
            @Field("type") type: Int,
            @Field("priority") priority: Int
    ): Deferred<TodoAddResponse>

    /**
     * 更新一条TODO
     */
    @POST(Constant.UPDATE_TODO)
    @FormUrlEncoded
    fun updateTodo(
            @Path("id") id: Int,
            @Field("title") title: String,
            @Field("content") content: String,
            @Field("date") date: String,
            @Field("type") type: Int,
            @Field("priority") priority: Int
    ): Deferred<BaseTodoResponse>

    /**
     * 仅更新TODO状态
     * 只会变更status，未完成->已经完成 or 已经完成->未完成
     */
    @POST(Constant.UPDATE_STATUE_TODO)
    @FormUrlEncoded
    fun updateStatusTodo(
            @Path("id") id: Int,
            @Field("status") status: Int
    ): Deferred<BaseTodoResponse>

    /**
     * 删除一条TODO
     */
    @POST(Constant.DELETE_TODO)
    fun deleteTodo(
            @Path("id") id: Int
    ): Deferred<BaseTodoResponse>


}
