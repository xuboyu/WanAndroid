package wanandroid.xuboyu.com.wanandroid.model

import wanandroid.xuboyu.com.wanandroid.presenter.ProjectPresenter

/**
 * use：项目列表获取抽离接口
 * author: XuBoYu
 * time: 2019/8/9
 **/
interface ProjectListModel {

    /**
     * 获取项目分类
     */
    fun getProjectKind(projectListPresenter: ProjectPresenter.ProjectKindPresenter)

    /**
     * 获取特定项目列表
     */
    fun getProjectTypeList(projectListPresenter: ProjectPresenter.ProjectCidListPresenter, page: Int, cid: Int)

    /**
     * 获取项目列表
     */
    fun getProjectList(projectListPresenter: ProjectPresenter.ProjectListPresenter, page: Int)

    /**
     * 取消请求
     */
    fun cancelPLRequest()

}