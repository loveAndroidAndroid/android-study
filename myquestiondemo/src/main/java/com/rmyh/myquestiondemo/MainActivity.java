package com.rmyh.myquestiondemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rmyh.myquestiondemo.view.CustomTypefaceSpan;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<Data> list = new ArrayList<>();
    private NoScrollViewPager viewpager;
    private int current = 0;

    ViewPager.OnPageChangeListener lister = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            current = position;
            here_title.setText((current + 1) + "/" + list.size());
            myAdapter.select = current;
            myAdapter.notifyDataSetChanged();
            if (current == list.size() - 1) {
                button1.setText("提交");
            } else {
                button1.setText("下一题");
            }
        }
    };
    private Button button1;
    private RecyclerView recyclerview;
    private MyAdapter myAdapter;
    private TextView tx_title;
    private ImageView ima_back;
    private TextView here_title;
    private Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.transparencyBar(this);
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/selftext.TTF");

        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
        button1 = (Button) findViewById(R.id.button1);
        tx_title = (TextView) findViewById(R.id.tv_title);
        here_title = (TextView) findViewById(R.id.here_title);
        ima_back = (ImageView) findViewById(R.id.ima_back);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        button1.setOnClickListener(this);
        ima_back.setOnClickListener(this);

        initData();

        // 应用字体
        here_title.setTypeface(typeFace);
        here_title.setText((current + 1) + "/" + list.size());
        button1.setTypeface(typeFace);
        button1.setText("下一题");

        //标题的设置
        SpannableStringBuilder spannableStringBefore = new SpannableStringBuilder("*");
        spannableStringBefore.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 0, "*".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder spannableStringMiddle = new SpannableStringBuilder("1.您最擅长的教研领域");
        spannableStringMiddle.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, "1.您最擅长的教研领域".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringMiddle.setSpan(new CustomTypefaceSpan("1.您最擅长的教研领域", typeFace), 0, "1.您最擅长的教研领域".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder spannableStringAfter = new SpannableStringBuilder("[单选]");
        spannableStringAfter.setSpan(new AbsoluteSizeSpan(14, true), 0, "[单选]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringAfter.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, "[单选]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBefore.append(spannableStringMiddle).append(spannableStringAfter);
        tx_title.setText(spannableStringBefore);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 9));
        myAdapter = new MyAdapter(list);
        recyclerview.setAdapter(myAdapter);

        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(mainAdapter);
        viewpager.setCurrentItem(current);
        viewpager.addOnPageChangeListener(lister);
    }

    private void initData() {
        list.clear();
        list.add(new Data(1, "1", false));
        list.add(new Data(2, "2", false));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", true));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", true));
        list.add(new Data(3, "4", true));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", false));
        list.add(new Data(3, "4", true));
        list.add(new Data(3, "4", false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (current == list.size() - 1) {
                    Toast.makeText(v.getContext(), "最后一题", Toast.LENGTH_SHORT).show();
                } else {
                    current = ++current;
                    viewpager.setCurrentItem((current));
                    here_title.setText((current + 1) + "/" + list.size());
                    myAdapter.select = current;
                    myAdapter.notifyDataSetChanged();
                }
                if (current == list.size() - 1) {
                    button1.setText("提交");
                } else {
                    button1.setText("下一题");
                }
                break;
            case R.id.ima_back:
                finish();
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter {
        List<Data> datas;
        public int select = 0;

        public MyAdapter(List<Data> list) {
            this.datas = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_here, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((ViewHolder) holder).setPosition(position);
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_here;
            private int position;

            public ViewHolder(View itemView) {

                super(itemView);
                tv_here = (TextView) itemView.findViewById(R.id.tv_here);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewpager.setCurrentItem(position, false);
                    }
                });
            }

            public void setPosition(int position) {
                this.position = position;
                tv_here.setText(position+1+"");
                if (datas.get(position).isSelect()){
                    tv_here.setBackgroundResource(R.drawable.bg_ovel_shape);
                }else {
                    tv_here.setBackgroundResource(R.drawable.bg_ovel_gral_shape);
                }
            }
        }
    }

    class MainAdapter extends FragmentPagerAdapter {

        List<Data> list;

        public MainAdapter(FragmentManager fm, List<Data> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            if (list.get(position).getType() == 1) {
                return new SelectQuestionFragment();
            } else if (list.get(position).getType() == 2) {
                return new MutiQuestionFragment();
            } else {
                return new EditQuestionFragment();
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
