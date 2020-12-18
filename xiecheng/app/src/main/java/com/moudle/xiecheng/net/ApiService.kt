package com.moudle.xiecheng.net

import com.moudle.xiecheng.bean.BannerInfo
import com.moudle.xiecheng.bean.UserInfo
import retrofit2.http.*


/**
 * author：zhangxiaowen1
 * Date:2019-11-07
 * Description:
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    //suspend 挂起函数，并不会阻塞线程  retrofit 2.6以后支持
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): BaseResponse<UserInfo>


    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): BaseResponse<UserInfo>


    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<BannerInfo>>
}