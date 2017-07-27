package com.smcb.smstock.utils;

import android.content.Context;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.kennyc.view.MultiStateView;
import com.mcxtzhang.commonadapter.databinding.rv.BaseBindingAdapter;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;

import java.util.List;

/**
 * Created by zqh on 2017/7/27.
 */

public class CommonPage implements PullToRefreshBase.OnRefreshListener2 {

    private int pageSize = 20;
    private int pageNo = 1;
    private Context context;
    private PullToRefreshBase pullToRefreshBase;
    private OnRefreshListener onRefreshListener;
    private MultiStateView stateView;
    private IAdapter  adapter;

    private CommonPage(Context context, PullToRefreshBase pullToRefreshBase, MultiStateView stateView) {
        this.context = context;
        this.pullToRefreshBase = pullToRefreshBase;
        this.stateView = stateView;
        this.pullToRefreshBase.setOnRefreshListener(this);
    }

    private CommonPage(PullToRefreshBase pullToRefreshBase, MultiStateView stateView) {
        this.pullToRefreshBase = pullToRefreshBase;
        this.stateView = stateView;
        this.pullToRefreshBase.setOnRefreshListener(this);
    }

    public static CommonPage getCommonPage(Context context, PullToRefreshBase pullToRefreshBase, MultiStateView stateView) {
        return new CommonPage(context,pullToRefreshBase,stateView);
    }

    public static CommonPage getCommonPage(PullToRefreshBase pullToRefreshBase, MultiStateView stateView) {
        return new CommonPage(pullToRefreshBase,stateView);
    }

    public CommonPage setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        return this;
    }

    public void refreshDatas(List list) {
        this.pageNo = setListViewDatas(context, pullToRefreshBase, stateView,getAdapter(),list,pageNo,pageSize);
    }

    public static int setListViewDatas(Context context, PullToRefreshBase view, MultiStateView stateView, IAdapter  adapter, List list, int currentPageNo, int pageSize) {
        if (currentPageNo == 1) {
            if (list != null && list.size() > 0) {
                adapter.getData().clear();
                view.setMode(PullToRefreshBase.Mode.BOTH);
                adapter.getData().addAll(list);
                stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            } else {
                stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        } else {
            if (list != null && list.size() > 0) {
                adapter.getData().addAll(list);
                if (list.size()<pageSize) {
                    view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    if (context!=null) {
                        Toast.makeText(context, "已全部加载", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                if (context!=null) {
                    Toast.makeText(context, "已全部加载", Toast.LENGTH_SHORT).show();
                }
            }
        }
        adapter.notifyDataSetChanged();
        return currentPageNo + 1;
    }

    public CommonPage reset() {
        this.pageNo = 1;
        return this;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        reset();
        if (onRefreshListener!=null) {
            onRefreshListener.onPullDownToRefresh(pullToRefreshBase,this);
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        if (onRefreshListener!=null) {
            onRefreshListener.onPullUpToRefresh(pullToRefreshBase,this);
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public CommonPage setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public CommonPage setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IAdapter getAdapter() {
        if (adapter==null) {
            throw new RuntimeException("please set IAdapter !!");
        }
        return adapter;
    }

    public CommonPage setAdapter(IAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public interface OnRefreshListener {
        void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase,CommonPage commonPage);
        void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase,CommonPage commonPage);
    }

    public interface IAdapter<T> {

        List getData();
        void notifyDataSetChanged();
        T getAdapter();
    }

    public static class RvAdapter implements IAdapter {

        private CommonAdapter adapter;

        public RvAdapter(CommonAdapter baseAdapter) {
            this.adapter = baseAdapter;
        }

        public static IAdapter getBaseDapter(CommonAdapter baseAdapter) {
            return new RvAdapter(baseAdapter);
        }

        @Override
        public List getData() {
            return adapter.getDatas();
        }

        @Override
        public void notifyDataSetChanged() {
            adapter.notifyDataSetChanged();
        }

        @Override
        public CommonAdapter getAdapter() {
            return adapter;
        }
    }
    public static class RvBindAdapter implements IAdapter {

        private BaseBindingAdapter adapter;

        public RvBindAdapter(BaseBindingAdapter baseAdapter) {
            this.adapter = baseAdapter;
        }

        public static IAdapter getBaseDapter(BaseBindingAdapter baseAdapter) {
            return new RvBindAdapter(baseAdapter);
        }

        @Override
        public List getData() {
            return adapter.getDatas();
        }

        @Override
        public void notifyDataSetChanged() {
            adapter.notifyDataSetChanged();
        }

        @Override
        public BaseBindingAdapter getAdapter() {
            return adapter;
        }
    }
}
