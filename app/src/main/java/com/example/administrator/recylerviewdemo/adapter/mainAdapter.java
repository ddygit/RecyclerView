package com.example.administrator.recylerviewdemo.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.recylerviewdemo.R;
import com.example.administrator.recylerviewdemo.entity.Netease;
import com.example.administrator.recylerviewdemo.utils.XImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24.
 */

public class mainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ONEIMAGE = 269;
    private static final int LONGIMAGE = 743;
    private static final int VIEWPAGER = 745;
    private static final int THREEIMAGE = 276;
    private static final int FOOTER = 480;

    public mainAdapter(List<Netease> neteaseList) {
        this.neteaseList = neteaseList;
    }

    public List<Netease> getNeteaseList() {
        return neteaseList;
    }

    public void setNeteaseList(List<Netease> neteaseList) {
        this.neteaseList = neteaseList;
    }

    List<Netease> neteaseList = new ArrayList<>();

    public void addDataList(List<Netease> list) {
        if (list == null) {
            Log.d("addDataList", "新增集合不能为空！");
            return;
        }
        neteaseList.addAll(list);

    }

    public void addData(Netease n) {

        neteaseList.add(n);

    }
    public void addData(int position,Netease n) {

        neteaseList.add(position,n);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {

            case ONEIMAGE:
                v = View.inflate(parent.getContext(), R.layout.layout_item_one_img, null);
                holder = new OneImageHolder(v);
                break;
            case THREEIMAGE:
                v = View.inflate(parent.getContext(), R.layout.layout_item_three_img, null);
                holder = new ThreeImageHolder(v);
                break;
            case VIEWPAGER:
                v = View.inflate(parent.getContext(), R.layout.layout_item_vp, null);
                holder = new ViewPagerHolder(v);
                break;
            case LONGIMAGE:
                v = View.inflate(parent.getContext(), R.layout.layout_item_one_head, null);
                holder = new LongImageHolder(v);
                break;
            case FOOTER:
                v = View.inflate(parent.getContext(), R.layout.footer, null);
                holder = new FooterHolder(v);
                break;
            default:
                break;
        }
        return holder;

    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return neteaseList.get(position).getAds() == null ? LONGIMAGE : VIEWPAGER;
        } else if (position < neteaseList.size()) {
            return neteaseList.get(position).getImgextra() == null ? ONEIMAGE : THREEIMAGE;
        } else {
            return FOOTER;
        }
    }
    //分状态去初始化：
    public static final int FOOTER_PULLING = 483;
    public static final int FOOTER_PULL_FINISHED = 306;
    public static final int FOOTER_PULL_NO_DATA = 147;
private int currentState;
    public void setCurrentState(int currentState){
        this.currentState=currentState;
    }
    private void initFooter(FooterHolder holder) {
        switch (currentState) {
            case FOOTER_PULL_FINISHED:
                holder.progressBar1.setVisibility(View.INVISIBLE);
                break;
            case FOOTER_PULL_NO_DATA:
                holder.textView1.setText("没有更多数据了");
                holder.progressBar1.setVisibility(View.INVISIBLE);
                break;
            case FOOTER_PULLING:
                holder.textView1.setText("正在加载，请稍候...");
                holder.progressBar1.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initLongImage(int position, LongImageHolder h) {
        XImageUtil.display(h.imgHead, neteaseList.get(position).getImgsrc());
        h.tvTitle.setText(neteaseList.get(position).getTitle());
    }

    private void initViewPager(int position, final ViewPagerHolder h) {

        Netease netease = neteaseList.get(position);
        final List<Netease.Ads> adses = netease.ads;
        ViewPAdapter vpAdapter = new ViewPAdapter(adses);
        h.vpager.setAdapter(vpAdapter);
        //设置几个点

        if (h.llLayout.getChildCount() == 0) {
            for (int i = 0; i < adses.size(); i++) {
                ImageView img = new ImageView(h.llLayout.getContext());
                img.setImageResource(R.drawable.adware_style_default);
                h.llLayout.addView(img);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img.getLayoutParams();
                layoutParams.leftMargin = 5;
                layoutParams.rightMargin = 5;
            }
        }
        ((ImageView) h.llLayout.getChildAt(0)).setImageResource(R.drawable.adware_style_selected);
        h.vpager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % adses.size()));
        //        设置动画效果
        h.vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < h.llLayout.getChildCount(); i++) {
                    ImageView img = (ImageView) h.llLayout.getChildAt(i);
                    img.setImageResource(R.drawable.adware_style_default);
                }
                ImageView img = (ImageView) h.llLayout.getChildAt(position % adses.size());
                img.setImageResource(R.drawable.adware_style_selected);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void initThreeImage(int position, ThreeImageHolder h) {
        Netease netease = neteaseList.get(position);
        XImageUtil.display(h.img1, netease.getImgsrc());
        XImageUtil.display(h.img2, netease.imgextra.get(0).getImgsrc());
        XImageUtil.display(h.img3, netease.imgextra.get(1).getImgsrc());
        h.tvFollow.setText(netease.getReplyCount() + "");
        h.tvTitle.setText(netease.getTitle());
    }

    private void initOneImage(int position, OneImageHolder h) {
        Netease netease = neteaseList.get(position);
        XImageUtil.display(h.imgLeft, netease.getImgsrc());
        h.tvFollow.setText(netease.getReplyCount() + "");
        h.tvTitle.setText(netease.getTitle());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OneImageHolder) {
            OneImageHolder h = (OneImageHolder) holder;
            initOneImage(position, h);
        } else if (holder instanceof ThreeImageHolder) {
            ThreeImageHolder h = (ThreeImageHolder) holder;
            initThreeImage(position, h);
        } else if (holder instanceof ViewPagerHolder) {
            ViewPagerHolder h = (ViewPagerHolder) holder;
            initViewPager(position, h);
        } else if (holder instanceof LongImageHolder) {
            LongImageHolder h = (LongImageHolder) holder;
            initLongImage(position, h);
        } else if (holder instanceof FooterHolder) {
            FooterHolder h = (FooterHolder) holder;
            initFooter(h);
        }

        //点击事件传递给activity来处理
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(position);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    //点击事件：
    public interface OnItemClickListener {
        void onClick(int position);
    }

    @Override
    public int getItemCount() {
        return neteaseList == null ? 0 : neteaseList.size();
    }


    public static class OneImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_left)
        ImageView imgLeft;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_follow)
        TextView tvFollow;

        public OneImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ThreeImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.tv_follow)
        TextView tvFollow;

        public ThreeImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ViewPagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vpager)
        ViewPager vpager;
        @BindView(R.id.ll_layout)
        LinearLayout llLayout;

        public ViewPagerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class LongImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public LongImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar1)
        ProgressBar progressBar1;
        @BindView(R.id.textView1)
        TextView textView1;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
