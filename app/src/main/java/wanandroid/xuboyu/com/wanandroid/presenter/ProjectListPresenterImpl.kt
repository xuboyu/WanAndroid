package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.ProjectResponse
import wanandroid.xuboyu.com.wanandroid.bean.ProjectTypeResponse
import wanandroid.xuboyu.com.wanandroid.model.ProjectListModel
import wanandroid.xuboyu.com.wanandroid.model.ProjectListModelmpl
import wanandroid.xuboyu.com.wanandroid.view.ProjectListView

/**
 * use：项目列表中间层实现类
 * author: XuBoYu
 * time: 2019/8/9
 **/
class ProjectListPresenterImpl (private val projectListView: ProjectListView)
    : ProjectPresenter.ProjectListPresenter, ProjectPresenter.ProjectCidListPresenter,
        ProjectPresenter.ProjectKindPresenter{

    private val projectListModel: ProjectListModel = ProjectListModelmpl()

    override fun getProjectKind() {
        projectListModel.getProjectKind(this)
    }

    override fun getProjectKindSuccess(result: ProjectTypeResponse) {
        if (result.errorCode != 0) {
            projectListView.getProjectKindFailed(result.errorMsg)
        }
        result.data ?: let {
            projectListView.getProjectKindZero()
            return
        }
        projectListView.getProjectKindSuccess(result)
    }

    override fun getProjectKindFailed(errorMessage: String?) {
        projectListView.getProjectKindFailed(errorMessage)
    }

    override fun getProjectList(page: Int) {
        projectListModel.getProjectList(this,page)
    }

    override fun getProjectListSuccess(result: ProjectResponse) {
        if (result.errorCode != 0) {
            projectListView.getProjectListFailed(result.errorMsg)
        }
        result.data?.datas ?: let {
            projectListView.getProjectListZero()
            return
        }
        projectListView.getProjectListSuccess(result)
    }

    override fun getProjectListFailed(errorMessage: String?) {
        projectListView.getProjectListFailed(errorMessage)
    }

    override fun getProjectTypeList(page: Int, cid: Int) {
        projectListModel.getProjectTypeList(this, page, cid)
    }

    override fun getProjectTypeListSuccess(result: ProjectResponse) {
        if (result.errorCode != 0) {
            projectListView.getProjectListFailed(result.errorMsg)
        }
        result.data?.datas ?: let {
            projectListView.getProjectListZero()
            return
        }
        projectListView.getProjectListSuccess(result)
    }

    override fun getProjectTypeListFailed(errorMessage: String?) {
        projectListView.getProjectListFailed(errorMessage)
    }

    fun cancelPLRequest() {
        projectListModel.cancelPLRequest()
    }

}