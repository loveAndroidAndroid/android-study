package com.moudle.xiecheng

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moudle.xiecheng.bean.UserInfo
import com.moudle.xiecheng.net.RetrofitManager
import com.moudle.xiecheng.net.dataConvert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * author：zhangxiaowen1
 * Date:2019-11-07
 * Description:
 */
class MainViewModel : ViewModel() {
    var userInfoRegister = MutableLiveData<UserInfo>()
    var userInfoLogin = MutableLiveData<UserInfo>()

    fun register() {
        /*viewModelScope是一个绑定到当前viewModel的作用域  当ViewModel被清除时会自动取消该作用域，所以不用担心内存泄漏为问题*/
        viewModelScope.launch {
            try {
                /*withContext表示挂起块  配合Retrofit声明的suspend函数执行 该块会挂起直到里面的网络请求完成*/
                val data = withContext(Dispatchers.IO) {

                    /*dataConvert扩展函数 简单的解析出我们想要的数据 */
                    RetrofitManager.service.register("zhangwenwen2", "123456", "123456").dataConvert()
                }

                /*给LiveData赋值  ui会自动更新*/
                userInfoRegister.value = data
            } catch (e: Exception) {
                /*请求异常的话在这里处理*/
                e.printStackTrace()
                Log.i("请求失败", "${e.message}")
            }
        }
    }

    fun login() {
        /*viewModelScope是一个绑定到当前viewModel的作用域  当ViewModel被清除时会自动取消该作用域，所以不用担心内存泄漏为问题*/
        viewModelScope.launch {
            try {
                /*withContext表示挂起块  配合Retrofit声明的suspend函数执行 该块会挂起直到里面的网络请求完成 最一行就是返回值*/
                val data = withContext(Dispatchers.IO) {

                    /*dataConvert扩展函数 简单的解析出我们想要的数据 */
                    RetrofitManager.service.login("zhangwenwen1", "123456").dataConvert()
                }

                /*给LiveData赋值  ui会自动更新*/
                userInfoLogin.value = data

                Log.i("请求成功", userInfoLogin.value.toString())
            } catch (e: Exception) {
                /*请求异常的话在这里处理*/
                e.printStackTrace()
                Log.i("请求失败", "${e.message}")
            }
        }
    }

    fun getbanner() {
        /*viewModelScope是一个绑定到当前viewModel的作用域  当ViewModel被清除时会自动取消该作用域，所以不用担心内存泄漏为问题*/
        viewModelScope.launch {
            try {
                /*withContext表示挂起块  配合Retrofit声明的suspend函数执行 该块会挂起直到里面的网络请求完成 最一行就是返回值*/
                val data = withContext(Dispatchers.IO) {

                    /*dataConvert扩展函数 简单的解析出我们想要的数据 */
                    RetrofitManager.service.getBanner().dataConvert()
                }

                Log.i("请求成功", data.toString())
            } catch (e: Exception) {
                /*请求异常的话在这里处理*/
                e.printStackTrace()
                Log.i("请求失败", "${e.message}")
            }
        }
    }
}