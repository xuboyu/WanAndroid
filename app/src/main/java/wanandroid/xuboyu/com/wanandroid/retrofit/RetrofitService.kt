package wanandroid.xuboyu.com.wanandroid.retrofit

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.*
import wanandroid.xuboyu.com.wanandroid.bean.BannerResponse
import wanandroid.xuboyu.com.wanandroid.bean.HomeListResponse
import wanandroid.xuboyu.com.wanandroid.bean.LoginResponse
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
    @GET(Constant.HomeList)
    fun getHomeList(
            @Path("page") page: Int
    ): Deferred<HomeListResponse>

    /**
     * 首页Banner
     * @return BannerResponse
     */
    @GET(Constant.homeBanner)
    fun getBanner(): Deferred<BannerResponse>

}
