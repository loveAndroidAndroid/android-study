package com.moudle.xiecheng.net

/**
 * author：zhangxiaowen1
 * Date:2019-11-07
 * Description:
 */

/*数据解析扩展函数*/
fun <T> BaseResponse<T>.dataConvert(): T {
    if (errorCode == 0) {
        return data
    } else {
        throw Exception(errorMsg)
    }
}

data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String?, val data: T)