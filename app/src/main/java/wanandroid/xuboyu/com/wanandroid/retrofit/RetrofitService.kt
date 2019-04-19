package wanandroid.xuboyu.com.wanandroid.retrofit

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import wanandroid.xuboyu.com.wanandroid.bean.LoginBean
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
     * @return Call<LoginBean>
     */
    @POST(Constant.LOGIN)
    fun getGameList(
            @Query("username") username: String,
            @Query("password") password: String
    ): Call<LoginBean>

}
