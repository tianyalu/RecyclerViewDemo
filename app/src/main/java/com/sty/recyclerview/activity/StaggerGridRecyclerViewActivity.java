package com.sty.recyclerview.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sty.recyclerview.R;
import com.sty.recyclerview.adapter.MyRecyclerViewAdapter;
import com.sty.recyclerview.adapter.StaggerGridRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shity on 2017/9/27/0027.
 */

public class StaggerGridRecyclerViewActivity extends AppCompatActivity {
    private Context mContext;
    private List<String> mDatas;
    private RecyclerView mRecyclerView;
    private StaggerGridRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mContext = this;

        initData();
        initViews();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_staggered, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_action_add:
                mAdapter.addData(1);
                break;
            case R.id.id_action_delete:
                mAdapter.deleteData(1);
                break;
            default:
                break;
        }
        return true;
    }

    protected void initData(){
        mDatas = new ArrayList<>();
        for(int i = 'A'; i < 'z'; i++){
            mDatas.add("" + (char) i);
        }
    }

    protected void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //装饰，画分隔线
        //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mAdapter = new StaggerGridRecyclerViewAdapter(mContext, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void setListeners(){
        mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, position + " click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(mContext, position + " long click", Toast.LENGTH_SHORT).show();
                mAdapter.deleteData(position);
            }
        });
    }
}
