package hack.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.HashMap;

import hack.test.adapter.BaseRecycleAdapter;
import hack.test.adapter.RecycleViewHolder;
import hack.test.dialog.BaseDialog;
import hack.test.dialog.CornerUtils;
import hack.test.dialog.Swing;
import hack.test.dialog.ZoomOutExit;
import hack.test.entity.ItemBean;
import hack.test.recycle.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity {


    private BaseDialog dialog;
    private LayoutAnimationController lac;
    private RecyclerView recyclerView;
    private BaseRecycleAdapter<ItemBean> adapter;
    private ArrayList<ItemBean> datas;
    private int btnNum=1;
    private HashMap<Integer,Integer>starNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog dialog = getDialog();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas = getDatas();
                BaseDialog dialog = getMultiGardeDialog();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

    }

    private BaseDialog getDialog() {
         dialog = new BaseDialog(this) {
            @Override
            public View onCreateView() {
                widthScale(0.85f);
                showAnim(new Swing());
                dismissAnim(new ZoomOutExit());
                View inflate = View.inflate(context, R.layout.dialog_garde_first_view, null);
                inflate.setBackgroundDrawable(
                        CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
                final Button btn = (Button) inflate.findViewById(R.id.btn);
                final LinearLayout top = (LinearLayout) inflate.findViewById(R.id.top);
                final LinearLayout center = (LinearLayout) inflate.findViewById(R.id.center);
                final LinearLayout bottom = (LinearLayout) inflate.findViewById(R.id.bottom);
                final LinearLayout gradeSecond = (LinearLayout) inflate.findViewById(R.id.grade_second);
                final TextView tv = (TextView) inflate.findViewById(R.id.et_pingjia);
                final TextView grade_submit = (TextView) inflate.findViewById(R.id.grade_submit);
                final EditText suggest = (EditText) inflate.findViewById(R.id.et_suggest);
                final EditText want = (EditText) inflate.findViewById(R.id.et_want);
                grade_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideSecond(center, top, bottom, dialog);
                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSecond(center, top, bottom, gradeSecond);
                    }
                });
                ProperRatingBar ratingBar = (ProperRatingBar) inflate.findViewById(R.id.teacher_score);
                ratingBar.setListener(new ProperRatingBar.RatingListener() {
                    @Override
                    public void onRatePicked(ProperRatingBar ratingBar) {
                        showButton(btn);
                    }
                });

                return inflate;
            }

            @Override
            public void setUiBeforShow() {

            }
        };
        return dialog;
    }

    private void hideSecond(LinearLayout center, LinearLayout top, LinearLayout bottom,BaseDialog dialog) {
        if(dialog!=null&&dialog.getCurrentFocus()!=null&&dialog.getCurrentFocus().getWindowToken()!=null)
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(800);
        animatorSet.playTogether(ObjectAnimator.ofFloat(top,"translationY",0),
                ObjectAnimator.ofFloat(bottom,"translationY",0),
                ObjectAnimator.ofFloat(center,"alpha",1)
        );
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
        center.setFocusable(true);
        center.setFocusableInTouchMode(true);
        center.requestFocus();
    }

    private void showSecond(LinearLayout center, LinearLayout top, LinearLayout bottom,LinearLayout grade) {
        grade.setFocusable(true);
        grade.setFocusableInTouchMode(true);
        grade.requestFocus();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(800);
        animatorSet.playTogether(ObjectAnimator.ofFloat(top,"translationY",-top.getHeight()),
                ObjectAnimator.ofFloat(bottom,"translationY",bottom.getHeight()),
                ObjectAnimator.ofFloat(center,"alpha",0)
                );
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    private void showButton(View v) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(250);
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int x = (int) ((width * 0.85f)/2+(v.getWidth() / 2));
        animatorSet.play(ObjectAnimator.ofFloat(v,"translationX",x));
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }


    private BaseDialog getMultiGardeDialog(){
        dialog=new BaseDialog(this) {
            @Override
            public View onCreateView() {
                widthScale(0.9f);
                heightScale(0.9f);
                /** LayoutAnimation */
                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF,
                        0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.setDuration(550);
                lac = new LayoutAnimationController(animation, 0.12f);
                lac.setInterpolator(new DecelerateInterpolator());
                View inflate = View.inflate(context, R.layout.dialog_garde_multi_view, null);
                inflate.setBackgroundDrawable(
                        CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
                recyclerView =(RecyclerView)inflate.findViewById(R.id.lv);
                recyclerView.setFadingEdgeLength(0);
                recyclerView.setVerticalScrollBarEnabled(false);
                return inflate;
            }

            @Override
            public void setUiBeforShow() {
                   adapter= new BaseRecycleAdapter<ItemBean>(MainActivity.this,datas,getItemLayouts()){

                        @Override
                        public void onBindViewHolder(final RecycleViewHolder holder, final int position) {
                            if(datas.get(holder.getLayoutPosition()).index==0) {
                                Integer star = starNum.get(holder.getLayoutPosition());
                                if(star!=null){
                                    ((ProperRatingBar) holder.getView(R.id.teacher_score)).setRating(star);
                                }else {
                                    ((ProperRatingBar) holder.getView(R.id.teacher_score)).setRating(0);
                                }
                                ((ProperRatingBar) holder.getView(R.id.teacher_score)).setListener(new ProperRatingBar.RatingListener() {
                                    @Override
                                    public void onRatePicked(ProperRatingBar ratingBar) {
                                        int index=0;
                                        if(holder.getLayoutPosition()+1<datas.size()){
                                            ItemBean itemBean = datas.get(holder.getLayoutPosition()+1);
                                             index = itemBean.index;
                                        }
                                        int rating = ratingBar.getRating();
                                        starNum.put(holder.getLayoutPosition(),rating);
                                        if(index==0) {
                                            if(rating>=3) {
                                                ItemBean itemBean = new ItemBean();
                                                itemBean.index=1;
                                                datas.add(holder.getLayoutPosition() + 1, itemBean);
                                                notifyItemInserted(holder.getLayoutPosition() + 1);
                                            }else {
                                                ItemBean itemBean2 = new ItemBean();
                                                itemBean2.index=2;
                                                datas.add(holder.getLayoutPosition()+1,itemBean2);
                                                ItemBean itemBean = new ItemBean();
                                                itemBean.index=1;
                                                datas.add(holder.getLayoutPosition() + 2, itemBean);
                                                notifyItemRangeInserted(holder.getLayoutPosition()+1,2);
                                            }
                                        }else if(index==1){
                                            if(rating<3){
                                                ItemBean itemBean2 = new ItemBean();
                                                itemBean2.index=2;
                                                datas.add(holder.getLayoutPosition()+1,itemBean2);
                                                notifyItemInserted(holder.getLayoutPosition() + 1);
                                            }

                                        }else if(index==2){
                                            if(rating>=3){
                                                datas.remove(holder.getLayoutPosition()+1);
                                                notifyItemRemoved(holder.getLayoutPosition()+1);
                                            }
                                        }
                                    }
                                });
                            }else if(datas.get(holder.getLayoutPosition()).index==1){
                                holder.setOnclick(R.id.tv_submit, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int index = datas.get(holder.getLayoutPosition() - 1).index;
                                        if(index==0) {
                                            datas.remove(holder.getLayoutPosition());
                                            datas.remove(holder.getLayoutPosition() - 1);
                                            starNum.put(holder.getLayoutPosition()-1,0);
                                            notifyItemRangeRemoved(holder.getLayoutPosition() - 1, 2);
                                        }else if(index ==2){
                                            datas.remove(holder.getLayoutPosition());
                                            datas.remove(holder.getLayoutPosition()-1);
                                            datas.remove(holder.getLayoutPosition()-2);
                                            starNum.put(holder.getLayoutPosition()-2,0);
                                            notifyItemRangeRemoved(holder.getLayoutPosition()-2,3);
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public int getItemViewType(int position) {
                            return datas.get(position).index;
                        }
                    };
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setItemAnimator(new SlideInLeftAnimator(new LinearInterpolator()));
                recyclerView.getItemAnimator().setAddDuration(300);
                recyclerView.getItemAnimator().setRemoveDuration(300);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutAnimation(lac);

            }
        };
        dialog.dismissAnim(new ZoomOutExit());
        return dialog;
    }
   private ArrayList<ItemBean> getDatas(){
       ArrayList<ItemBean> itemBeens = new ArrayList<>();
       for (int i=0;i<10;i++){
           itemBeens.add(new ItemBean());
       }
       starNum=new HashMap<>();
       return itemBeens;
   };
   private ArrayList<Integer> getItemLayouts(){
       ArrayList<Integer> layouts = new ArrayList<>();
       layouts.add(R.layout.item_dialog_multi_garde);
       layouts.add(R.layout.item_dialog_multi_btn);
       layouts.add(R.layout.item_dialog_multi_msg);
       return layouts;
   }
}
