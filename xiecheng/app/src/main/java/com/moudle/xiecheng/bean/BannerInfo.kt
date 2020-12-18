package com.moudle.xiecheng.bean

/**
 * author：zhangxiaowen1
 * Date:2019-11-07
 * Description:
 */
data class BannerInfo(
    val desc: String?,
    val id: Int,
    val imagePath: String?,
    val isVisible: Int,
    val order: Int,
    val title: String?,
    val type: Int,
    val url: String?
)