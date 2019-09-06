package wanandroid.xuboyu.com.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import wanandroid.xuboyu.com.wanandroid.R
import wanandroid.xuboyu.com.wanandroid.bean.TodoListResponse

/**
 * use：doneTodo列表适配器
 * author: XuBoYu
 * time: 2019/9/6
 **/
class DoneTodoListAdapter (val context: Context, datas: MutableList<TodoListResponse.Data.Datas>) :
        BaseQuickAdapter<TodoListResponse.Data.Datas, BaseViewHolder>(R.layout.done_todo_item, datas) {

    private var d : MutableList<TodoListResponse.Data.Datas> = datas

    override fun getItemCount(): Int {
        return d.size
    }

    override fun convert(helper: BaseViewHolder?, item: TodoListResponse.Data.Datas?) {
        item ?: return
        var p : String = "一般"
        when {
            item.priority == 1 -> p = "重要"
            item.priority == 2 -> p = "一般"
        }
        if (helper != null) {
            @Suppress("DEPRECATION")
            helper.setText(R.id.item_title, item.title)
                    .setText(R.id.item_content, item.content)
                    .setText(R.id.todo_p, "优先级：$p")
                    .addOnClickListener(R.id.delete_todo)
            when {
                item.type == 1 -> helper.setImageResource(R.id.item_type, R.drawable.work)
                item.type == 2 -> helper.setImageResource(R.id.item_type, R.drawable.sport)
                item.type == 3 -> helper.setImageResource(R.id.item_type, R.drawable.play)
            }

        }
    }

}