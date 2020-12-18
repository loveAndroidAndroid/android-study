package com.moudle.xiecheng.bean

/**
 * author：zhangxiaowen1
 * Date:2019-11-07
 * Description:
 */
data class UserInfo(
    val collectIds: List<Int>,
    val email: String?,
    val icon: String?,
    val id: Int,
    val password: String?,
    val type: Int,
    val username: String?
)