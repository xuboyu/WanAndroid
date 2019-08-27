package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.ProjectResponse
import wanandroid.xuboyu.com.wanandroid.bean.ProjectTypeResponse

/**
 * use：项目列表中间层抽离接口
 * author: XuBoYu
 * time: 2019/8/9
 **/
interface ProjectPresenter {

    interface ProjectKindPresenter {
        /**
         * 获取项目分类
         */
        fun getProjectKind()

        /**
         * 获取成功
         * @param result ProjectTypeResponse
         */
        fun getProjectKindSuccess(result: ProjectTypeResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getProjectKindFailed(errorMessage: String?)
    }

    interface ProjectListPresenter {
        /**
         * 获取项目列表
         */
        fun getProjectList(page: Int)

        /**
         * 获取成功
         * @param result CollectWebListResponse
         */
        fun getProjectListSuccess(result: ProjectResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getProjectListFailed(errorMessage: String?)
    }

    interface ProjectCidListPresenter {
        /**
         * 获取特定项目列表
         */
        fun getProjectTypeList(page: Int, cid: Int)

        /**
         * 获取成功
         * @param result CollectWebListResponse
         */
        fun getProjectTypeListSuccess(result: ProjectResponse)

        /**
         * 获取失败
         * @param errorMessage 失败信息
         */
        fun getProjectTypeListFailed(errorMessage: String?)
    }

}