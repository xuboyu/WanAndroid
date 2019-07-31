package wanandroid.xuboyu.com.wanandroid.presenter

import wanandroid.xuboyu.com.wanandroid.bean.TreeListResponse
import wanandroid.xuboyu.com.wanandroid.model.HomeModel
import wanandroid.xuboyu.com.wanandroid.model.HomeModelImpl
import wanandroid.xuboyu.com.wanandroid.view.TypeFragmentView

/**
 * use：体系列表界面中介层
 * author: XuBoYu
 * time: 2019/7/31
 **/
class TypeFragmentPresenterImpl(private val typeFragmentView: TypeFragmentView) :
        HomePresenter.OnTreeListListener {

    private val homeModel: HomeModel = HomeModelImpl()

    override fun getTreeList() {
        homeModel.getTreeList(this)
    }

    override fun getTreeListSuccess(result: TreeListResponse) {
        if (result.errorCode != 0) {
            typeFragmentView.getTypeListFailed(result.errorMsg)
            return
        }
        if (result.data.isEmpty()) {
            typeFragmentView.getTypeListZero()
            return
        }
        typeFragmentView.getTypeListSuccess(result)
    }

    override fun getTreeListFailed(errorMessage: String?) {
        typeFragmentView.getTypeListFailed(errorMessage)
    }

    /**
     * 取消请求
     */
    fun cancelRequest() {
        homeModel.cancelTreeRequest()
    }

}