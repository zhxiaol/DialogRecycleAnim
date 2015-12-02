package hack.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {

    private ArrayList<Integer> checkItems;
    private  int mItemLayoutId;
    private  Context mContext;
    private  LayoutInflater mInflater;
    private  List<T> mDatas;
    private  ArrayList<Integer> items;

    /**
     * 基本模式
     * @param context
     * @param mDatas
     * @param itemLayoutId
     */
    public BaseRecycleAdapter(Context context, List<T> mDatas, int itemLayoutId)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        if(items==null)items=new ArrayList<Integer>();
        items.add(itemLayoutId);
        this.mItemLayoutId = itemLayoutId;
    }
    /**
     * 多视图模式
     * @param context
     * @param mDatas
     * @param layouts 布局Id集合
     */
    public BaseRecycleAdapter(Context context, List<T> mDatas,ArrayList<Integer> items)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        if(this.items==null)this.items=new ArrayList<Integer>();
        this.items.addAll(items);
        this.mDatas = mDatas;
        this.mItemLayoutId=-1;
    }


    public void clearDatas() {
        int size = this.mDatas.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mDatas.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    public void addDatas(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        this.notifyItemRangeInserted(0, mDatas.size() - 1);
    }

    public void changeDatas(List<T>mDatas){
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        this.notifyItemChanged(0);
    }


    @Override
    public RecycleViewHolder onCreateViewHolder(final ViewGroup viewGroup, int index) {
        View v = LayoutInflater.from(mContext).inflate(items.get(index), viewGroup, false);
        return new RecycleViewHolder(v);
    }




    @Override
    public int getItemCount() {
        return mDatas==null?0: mDatas.size();
    }

    public int getCase(int layoutId){
        if(items==null){
            items=new ArrayList<Integer>();
        }
        return items.indexOf(layoutId);
    }
    /***
     * 返回视图的布局Id
     */
    @Override
    public int getItemViewType(int position) {
        if(mItemLayoutId!=-1){
            return getCase(mItemLayoutId);
        }else{
            return 0;
        }
    }
    /**设置条目被点击过*/
    public void setCheckedItem(int position){
        if(checkItems==null){
            checkItems=new ArrayList<Integer>();
        }
        if(!checkItems.contains(position)){
            checkItems.add(position);
        }
    }



}
