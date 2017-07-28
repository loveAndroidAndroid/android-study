package com.rmyh.myquestiondemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/7/17.
 */
@SuppressLint("ValidFragment")
public class MutiQuestionFragment extends Fragment {
    private int[] images = {R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e,R.mipmap.f,R.mipmap.g,R.mipmap.h,R.mipmap.i,R.mipmap.j,
            R.mipmap.k,R.mipmap.l,R.mipmap.m,R.mipmap.n,R.mipmap.o,R.mipmap.p,R.mipmap.q,R.mipmap.r,R.mipmap.s,R.mipmap.t,R.mipmap.u,R.mipmap.v,
            R.mipmap.w,R.mipmap.x,R.mipmap.y,R.mipmap.z};

    List<Data> date = new ArrayList<>();


    public MutiQuestionFragment() {
        date.clear();
        date.add(new Data(1, "1",false));
        date.add(new Data(2, "2",false));
        date.add(new Data(2, "3",false));
        date.add(new Data(3, "4",false));
        date.add(new Data(3, "5",false));
        date.add(new Data(3, "6",false));
        date.add(new Data(3, "7",false));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_select, container, false);
        RecyclerView select_rv = (RecyclerView)inflate.findViewById(R.id.select_rv);
        select_rv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        SelectQuestionAdapter selectQuestionAdapter = new SelectQuestionAdapter();
        select_rv.setAdapter(selectQuestionAdapter);
        return inflate;
    }
    class SelectQuestionAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ques, parent, false);
            return new SelectQuestionAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).setPosition(position);
        }

        @Override
        public int getItemCount() {
            return date.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout select_ll;
            private int position;
            private final ImageView iv_select;
            private final TextView tv_quesDetail;

            public ViewHolder(View itemView) {
                super(itemView);
                iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
                select_ll = (LinearLayout) itemView.findViewById(R.id.select_ll);
                tv_quesDetail = (TextView) itemView.findViewById(R.id.tv_quesDetail);
            }

            public void setPosition(final int position) {
                this.position = position;
                iv_select.setImageResource(images[position]);
                if (date.get(position).isSelect()){
                    select_ll.setBackgroundResource(R.mipmap.rectangle_g);
                }else {
                    select_ll.setBackgroundResource(R.mipmap.rectangle_y);
                }
                select_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!date.get(position).isSelect()){
                            select_ll.setBackgroundResource(R.mipmap.rectangle_g);
                            date.get(position).setSelect(true);
                        }else {
                            select_ll.setBackgroundResource(R.mipmap.rectangle_y);
                            date.get(position).setSelect(false);
                        }
                    }
                });
            }
        }
    }
}
