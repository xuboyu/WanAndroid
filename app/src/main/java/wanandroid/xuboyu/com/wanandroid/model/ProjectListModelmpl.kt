package wanandroid.xuboyu.com.wanandroid.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import wanandroid.xuboyu.com.wanandroid.bean.ProjectResponse
import wanandroid.xuboyu.com.wanandroid.bean.ProjectTypeResponse
import wanandroid.xuboyu.com.wanandroid.cancelByActivite
import wanandroid.xuboyu.com.wanandroid.common.Constant
import wanandroid.xuboyu.com.wanandroid.presenter.ProjectPresenter
import wanandroid.xuboyu.com.wanandroid.retrofit.RetrofitHelper
import wanandroid.xuboyu.com.wanandroid.tryCatch

/**
 * use：项目列表获取实现类
 * author: XuBoYu
 * time: 2019/8/9
 **/
class ProjectListModelmpl: ProjectListModel {

    private var kAsync: Deferred<ProjectTypeResponse>? = null

    private var pCAsync: Deferred<ProjectResponse>? = null

    private var pAsync: Deferred<ProjectResponse>? = null

    override fun getProjectKind(projectListPresenter: ProjectPresenter.ProjectKindPresenter) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                projectListPresenter.getProjectKindFailed(it.toString())
            }) {
                kAsync?.cancelByActivite()
                kAsync = RetrofitHelper.retrofitService.getProjectTypeList()
                val result = kAsync?.await()
                result ?: let {
                    projectListPresenter.getProjectKindFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { projectListPresenter.getProjectKindSuccess(result) }
            }
        }
    }

    override fun getProjectTypeList(projectListPresenter: ProjectPresenter.ProjectCidListPresenter, page: Int, cid: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                projectListPresenter.getProjectTypeListFailed(it.toString())
            }) {
                pCAsync?.cancelByActivite()
                pCAsync = RetrofitHelper.retrofitService.getTypeProjectList(page, cid)
                val result = pCAsync?.await()
                result ?: let {
                    projectListPresenter.getProjectTypeListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { projectListPresenter.getProjectTypeListSuccess(result) }
            }
        }
    }

    override fun getProjectList(projectListPresenter: ProjectPresenter.ProjectListPresenter, page: Int) {
        async(UI) {
            tryCatch({
                it.printStackTrace()
                projectListPresenter.getProjectListFailed(it.toString())
            }) {
                pAsync?.cancelByActivite()
                pAsync = RetrofitHelper.retrofitService.getNewProjectList(page)
                val result = pAsync?.await()
                result ?: let {
                    projectListPresenter.getProjectListFailed(Constant.RESULT_NULL)
                    return@async
                }
                result?.let { projectListPresenter.getProjectListSuccess(result) }
            }
        }
    }

    override fun cancelPLRequest() {
        kAsync?.cancelByActivite()
        pCAsync?.cancelByActivite()
        pAsync?.cancelByActivite()
    }

}