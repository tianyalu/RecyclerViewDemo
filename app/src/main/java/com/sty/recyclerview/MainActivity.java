package com.sty.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sty.recyclerview.activity.StaggerGridRecyclerViewActivity;
import com.sty.recyclerview.adapter.MyRecyclerViewAdapter;
import com.sty.recyclerview.divider.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码参考：http://blog.csdn.net/lmj623565791/article/details/45059587
 */
public class MainActivity extends AppCompatActivity{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mContext = this;

        initData();
        initViews();
        setListeners();
    }

    protected void initData(){
        mDatas = new ArrayList<>();
        for(int i = 'A'; i < 'z'; i++){
            mDatas.add("" + (char) i);
        }
    }

    protected void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycler_view);
        mAdapter = new MyRecyclerViewAdapter(mContext, mDatas);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        //装饰，画分隔线
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                //水平方向滚动
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//                mRecyclerView.addItemDecoration(new DividerLinearItemDecoration(this, DividerLinearItemDecoration.HORIZONTAL_LIST));
                break;
            case R.id.id_action_horizontalGridView:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;

            case R.id.id_action_staggeredgridview:
                Intent intent = new Intent(mContext, StaggerGridRecyclerViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
