package hack.test.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;





public class RecycleViewHolder extends RecyclerView.ViewHolder
{
    private final SparseArray<View> mViews;
    private int mPosition;
    private  View mConvertView;

    public RecycleViewHolder(View v)
    {
        super(v);
        this.mViews = new SparseArray<View>();
        mConvertView=v;

    }

    public  View getConvertView()
    {

        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    /**
     * 通过控件Id获取对于的控件
     * @param viewId
     * @return
     */
    public <T extends View>T getViewNoCache(int viewId){
        return (T) mConvertView.findViewById(viewId);
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecycleViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }
    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecycleViewHolder appendText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.append(text);
        return this;
    }

    /**
     * 为TextView 设置html文本
     * @param viewId
     * @param text
     * @return
     */
    public RecycleViewHolder setHtml(int viewId,String text){
        TextView view =getView(viewId);
        Spanned spanned = Html.fromHtml(text);
        view.setText(spanned);
        return this;
    }
    /**
     * 获得TextView和EditText的字符串
     * @param viewId
     * @param text
     * @return
     */
    public String getText(int viewId){
        TextView view=getView(viewId);
        return view.getText().toString();
    }
    /***
     * 设置点击效果
     * @param viewId
     * @param listener
     * @return
     */
    public RecycleViewHolder setOnclick(int viewId,View.OnClickListener listener){
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecycleViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }
    /**
     * 为view设置背景
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecycleViewHolder setBackgroundResource(int viewId, int drawableId)
    {
        View view = getView(viewId);
        view.setBackgroundResource(drawableId);

        return this;
    }
    /**
     * 设置view是否可按
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecycleViewHolder setViewEnabled(int viewId, boolean enable)
    {
        View view = getView(viewId);
        view.setEnabled(enable);

        return this;
    }


    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecycleViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecycleViewHolder setImageByUrl(int viewId, String url)
    {
        //ImageLoader.getInstance().displayImage(url, (ImageView)getView(viewId),getSimpleOptions());
        return this;
    }

    /**
     * 设置常用的设置项
     *
     * @return
     */

    /**设置textview字体颜色**/
    public void settextColor(int viewId, int colorId) {
        ((TextView)getView(viewId)).setTextColor(colorId);
    }
    /**为文本设置带删除线的文本*/
    public RecycleViewHolder setDeleteText(int viewId,String text){
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new StrikethroughSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView view =getView(viewId);
        view.setText(sp);
        return this;
    }

}
