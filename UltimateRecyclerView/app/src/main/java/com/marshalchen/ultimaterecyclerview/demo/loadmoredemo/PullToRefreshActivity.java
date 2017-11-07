package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.demo.BuildConfig;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.FastBinding;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionsMenu;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


public class PullToRefreshActivity extends BasicFunctions implements ActionMode.Callback {
    private static final String TAG = PullToRefreshActivity.class.getSimpleName();

    private static final int NUM_SAMPLE_ITEMS = 50;

    private CustomUltimateRecyclerview ultimateRecyclerView;
    private SampleDataAdapter sampleDataAdapter;
    private int numSampleItems = NUM_SAMPLE_ITEMS;
    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton starButton;

    @Override
    protected void onLoadmore() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onLoadMore");
        }
    }

    @Override
    protected void onFireRefresh() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPullToRefresh");
        }
        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
        changeHeaderHandler.sendEmptyMessageDelayed(0, 500);
    }

    @Override
    protected void addButtonTrigger() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "addButtonTrigger");
        }
    }

    @Override
    protected void removeButtonTrigger() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "removeButtonTrigger");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_refresh_activity);
        ultimateRecyclerView = findViewById(R.id.custom_ultimate_recycler_view);
        ultimateRecyclerView.setCustomSwipeToRefresh();

        sampleDataAdapter = new SampleDataAdapter();
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimateRecyclerView.setAdapter(sampleDataAdapter);

        floatingActionsMenu = findViewById(R.id.customfloatingActionMenu);
        starButton = findViewById(R.id.starButton);
        starButton.setOnClickListener(view -> {
            Log.d(TAG, "tapped star button");
            floatingActionsMenu.collapse();
            numSampleItems++;
            sampleDataAdapter.notifyDataSetChanged();
        });

        // refreshingMaterial();
        refreshingString();

    }

    void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(this);
        //   header.setPadding(0, 15, 0, 0);

        storeHouseHeader.initWithString("XCode Big Air");
        //  header.initWithStringArray(R.array.akta);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onFireRefresh();
                    }
                }, 1800);
            }
        });

    }

    void refreshingMaterial() {
        materialHeader = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 15, 0, 10);
        materialHeader.setPtrFrameLayout(ultimateRecyclerView.mPtrFrameLayout);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(materialHeader);

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                    }
                }, 1800);
            }
        });
    }

    Handler changeHeaderHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshingStringArray();
                    break;
                case 1:
                    //  refreshingMaterial();
                    refreshingString();
                    break;
                case 2:
                    // refreshingString();
                    break;
                case 3:
                    refreshingString();
                    break;
                case 4:
                    break;
            }
        }
    };
    private int mLoadTime = 0;
    StoreHouseHeader storeHouseHeader;
    MaterialHeader materialHeader;
    //  RentalsSunHeaderView rentalsSunHeaderView;

    private void refreshingStringArray() {
        storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, 15, 0, 0);

        // using string array from resource xml file
        storeHouseHeader.initWithStringArray(R.array.storehouse);
        ultimateRecyclerView.mPtrFrameLayout.setDurationToCloseHeader(1500);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);

        // change header after loaded
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(new PtrUIHandler() {


            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                if (mLoadTime % 2 == 0) {
                    storeHouseHeader.setScale(1);
                    storeHouseHeader.initWithStringArray(R.array.storehouse);
                } else {
                    storeHouseHeader.setScale(0.5f);
                    storeHouseHeader.initWithStringArray(R.array.akta);
                }
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        if (mLoadTime % 2 == 0)
                            changeHeaderHandler.sendEmptyMessageDelayed(1, 500);
                    }
                }, 2000);
            }
        });
    }

    public int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    @Override
    protected void doURV(UltimateRecyclerView urv) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        URLogs.d(String.format("actionmode---%s", (mode == null)));
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Called to refresh an action mode's action menu whenever it is invalidated.
     *
     * @param mode ActionMode being prepared
     * @param menu Menu used to populate action buttons
     * @return true if the menu or action mode was updated, false otherwise.
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        this.actionMode = mode;
        return false;
    }


    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.actionMode = null;
    }


    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FastBinding.startactivity(this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }

/*

    enum Type {
        FadeIn("FadeIn", new FadeInAnimator()),
        FadeInDown("FadeInDown", new FadeInDownAnimator()),
        FadeInUp("FadeInUp", new FadeInUpAnimator()),
        FadeInLeft("FadeInLeft", new FadeInLeftAnimator()),
        FadeInRight("FadeInRight", new FadeInRightAnimator()),
        Landing("Landing", new LandingAnimator()),
        ScaleIn("ScaleIn", new ScaleInAnimator()),
        ScaleInTop("ScaleInTop", new ScaleInTopAnimator()),
        ScaleInBottom("ScaleInBottom", new ScaleInBottomAnimator()),
        ScaleInLeft("ScaleInLeft", new ScaleInLeftAnimator()),
        ScaleInRight("ScaleInRight", new ScaleInRightAnimator()),
        FlipInTopX("FlipInTopX", new FlipInTopXAnimator()),
        FlipInBottomX("FlipInBottomX", new FlipInBottomXAnimator()),
        FlipInLeftY("FlipInLeftY", new FlipInLeftYAnimator()),
        FlipInRightY("FlipInRightY", new FlipInRightYAnimator()),
        SlideInLeft("SlideInLeft", new SlideInLeftAnimator()),
        SlideInRight("SlideInRight", new SlideInRightAnimator()),
        SlideInDown("SlideInDown", new SlideInDownAnimator()),
        SlideInUp("SlideInUp", new SlideInUpAnimator()),
        OvershootInRight("OvershootInRight", new OvershootInRightAnimator()),
        OvershootInLeft("OvershootInLeft", new OvershootInLeftAnimator());

        private String mTitle;
        private BaseItemAnimator mAnimator;

        Type(String title, BaseItemAnimator animator) {
            mTitle = title;
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }

        public String getTitle() {
            return mTitle;
        }
    }
*/

    protected class SampleDataAdapter extends UltimateViewAdapter<SampleViewHolder> {

        @Override
        public SampleViewHolder newFooterHolder(View view) {
            return null;
        }

        @Override
        public SampleViewHolder newHeaderHolder(View view) {
            return null;
        }

        @Override
        public SampleViewHolder onCreateViewHolder(ViewGroup parent) {
            return new SampleViewHolder(
                    LayoutInflater.from(PullToRefreshActivity.this)
                    .inflate(R.layout.simple_text_viewholder, parent, false)
            );
        }

        @Override
        public int getAdapterItemCount() {
            return numSampleItems;
        }

        @Override
        public long generateHeaderId(int position) {
            return 0;
        }

        @Override
        public void onBindViewHolder(SampleViewHolder holder, int position) {
            holder.setText(String.format("row %02d", position));
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            return null;
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        }
    }

    protected class SampleViewHolder extends UltimateRecyclerviewViewHolder {
        protected TextView textLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                Log.d(TAG, String.format("tapped on %s", textLabel.getText().toString()));
            });
            textLabel = (TextView)findViewByIdEfficient(R.id.textLabel);
        }

        public void setText(String text) {
            textLabel.setText(text);
        }
    }
}
