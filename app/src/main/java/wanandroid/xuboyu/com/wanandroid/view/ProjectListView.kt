package wanandroid.xuboyu.com.wanandroid.view

import wanandroid.xuboyu.com.wanandroid.bean.ProjectResponse
import wanandroid.xuboyu.com.wanandroid.bean.ProjectTypeResponse

/**
 * use：项目列表界面UI抽离接口
 * author: XuBoYu
 * time: 2019/8/9
 **/
interface ProjectListView {

    /**
     * 成功回调
     * @param result 请求bean
     */
    fun getProjectKindSuccess(result: ProjectTypeResponse)

    /**
     * 失败回调
     * @param errorMessage 失败信息
     */
    fun getProjectKindFailed(errorMessage: String?)

    /**
     * 获取列表长度为0
     */
    fun getProjectKindZero()

    /**
     * 成功回调
     * @param result 请求bean
     */
    fun getProjectListSuccess(result: ProjectResponse)

    /**
     * 失败回调
     * @param errorMessage 失败信息
     */
    fun getProjectListFailed(errorMessage: String?)

    /**
     * 获取列表长度为0
     */
    fun getProjectListZero()
}