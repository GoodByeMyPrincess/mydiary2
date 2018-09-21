package com.example.hp.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.hp.mydiary.DiarySort.DiarySort;
import com.example.hp.mydiary.Interface.ItemTouchHelperAdapterCallback;
import com.example.hp.mydiary.Utils.DiaryItemTouchHelperAdapterCallback;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static android.view.View.*;

public class DiaryListFragment extends Fragment {
    private static final int REQUEST_DIARY = 1;
    private static boolean isEdit = false;

    private RecyclerView mRecyclerView;
    private TextView mNoDiaryTextView;
    private DiaryAdapter mDiaryAdapter;
    private FloatingActionButton mActionButton;
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private ItemTouchHelper mHelper;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDiarySortRecycleView;
    private DiarySortAdapter mDiarySortAdapter;
    private Button mAddDiarySortButton;
    private Button mManageDiarySortButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mDiarySortTextView;
    private ImageView mHeadImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diary_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.diary_recycle_view);
        mNoDiaryTextView = (TextView) v.findViewById(R.id.text_view);
        mActionButton = (FloatingActionButton) v.findViewById(R.id.fab_add_diary);
        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        mDiarySortRecycleView = (RecyclerView) v.findViewById(R.id.diary_sort_recycle);
        mAddDiarySortButton = (Button) v.findViewById(R.id.bt_add_diary_sort);
        mManageDiarySortButton = (Button) v.findViewById(R.id.btn_manage_diary_sort);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        mDiarySortTextView = (TextView) v.findViewById(R.id.tv_diary_sort_name);
        mHeadImageView = (ImageView) v.findViewById(R.id.slide_menu_head_image);


        mActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), DiaryActivity.class);
                startActivity(intent);
            }
        });


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDiarySortRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddDiarySortButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DiarySortAddActivity.class);
                startActivity(intent);
            }
        });


        mManageDiarySortButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isEdit = !isEdit;
                updateMenuUI();
            }
        });


        mToolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                isEdit = false;
                updateMenuUI();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.darkGray));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        mDiarySortTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                updateUI();
            }
        });

        mHeadImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInformationActivity.class);
                startActivity(intent);
            }
        });


        updateUI();

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_diary_list, menu);

        MenuItem searchItem = menu.findItem(R.id.search_diary);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint(getResources().getString(R.string.search_diary));

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        mSearchView.setMaxWidth(6000);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDrawerLayout.closeDrawers();
                if (newText.equals("")) {
                    updateUI();
                } else {
                    List<Diary> searchDiaries = DiaryLab.get(getActivity()).searchDiaries(newText);
                    mDiaryAdapter = new DiaryAdapter(searchDiaries);
                    mRecyclerView.setAdapter(mDiaryAdapter);
                }
                return true;
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_diary:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI() {
        DiaryLab diaryLab = DiaryLab.get(getActivity());
        List<Diary> diaries = diaryLab.getDiaries();


        if (mDiaryAdapter == null) {
            mDiaryAdapter = new DiaryAdapter(diaries);
            mRecyclerView.setAdapter(mDiaryAdapter);
        } else {
            mDiaryAdapter.setDiaries(diaries);
            mDiaryAdapter.notifyDataSetChanged();
        }

        haveDiary(diaries);

        updateMenuUI();

        ItemTouchHelper.Callback callback = new DiaryItemTouchHelperAdapterCallback(mDiaryAdapter);
        mHelper = new ItemTouchHelper(callback);
        mHelper.attachToRecyclerView(mRecyclerView);


    }

    private void haveDiary(List<Diary> diaries) {
        if (diaries == null || diaries.size() == 0) {
            mNoDiaryTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mNoDiaryTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }

    public void updateMenuUI() {
        List<DiarySort> diarySorts = DiaryLab.get(getActivity()).getDiarySorts();
        mDiarySortAdapter = new DiarySortAdapter(diarySorts);
        mDiarySortRecycleView.setAdapter(mDiarySortAdapter);
    }

    private class DiaryHolder extends ViewHolder implements OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mWeatherTextView;
        private ImageView mPhotoImageView;
        private Diary mDiary;

        public DiaryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_diary, parent, false));

            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.tv_date);
            mWeatherTextView = (TextView) itemView.findViewById(R.id.tv_weather);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = DiaryActivity.newInstance(getActivity(), mDiary.getId());

            startActivity(intent);

        }


        public void bind(Diary diary) {
            mDiary = diary;
            String mDate = (String) DateFormat.format("yyyy年 M月dd日 E", mDiary.getDate());
            mTitleTextView.setText(mDiary.getTitle());
            mDateTextView.setText(mDate);
            DiarySort diarySort = DiaryLab.get(getActivity()).getDiarySort(mDiary.getDiarySortId());
            mWeatherTextView.setText(diarySort.getDiarySortName());
            mPhotoImageView.setImageResource(selectWeather(mDiary));
        }


    }


    private class DiaryAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapterCallback {
        private List<Diary> mDiaries;

        public DiaryAdapter(List<Diary> diaries) {
            mDiaries = diaries;
        }


        public void setDiaries(List<Diary> diaries) {
            mDiaries = diaries;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DiaryHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Diary diary = mDiaries.get(position);
            ((DiaryHolder) holder).bind(diary);

        }

        @Override
        public int getItemCount() {
            return mDiaries == null ? 0 : mDiaries.size();
        }

        @Override
        public void onItemSwiped(int adapterPosition) {
            DiaryLab.get(getActivity()).delete(mDiaries.get(adapterPosition));
            mDiaries.remove(adapterPosition);
            Toast.makeText(getActivity(), "日记删除成功！", Toast.LENGTH_SHORT).show();
            notifyItemRemoved(adapterPosition);
            updateUI();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            Collections.swap(mDiaries, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }


    private class DiarySortHolder extends RecyclerView.ViewHolder {
        private TextView mDiarySortName;
        private ImageView mEditDiarySort;
        private DiarySort mDiarySort;

        public DiarySortHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_diary_sort, parent, false));

            mDiarySortName = (TextView) itemView.findViewById(R.id.tv_diary_sort_name);
            mEditDiarySort = (ImageView) itemView.findViewById(R.id.delete_diary_sort);

            mDiarySortName.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int sortId = DiaryLab.get(getActivity()).getRowId(mDiarySortName.getText().toString());
                    List<Diary> diaries = DiaryLab.get(getActivity()).getDiaries(sortId);
                    mDiaryAdapter.setDiaries(diaries);
                    mDiaryAdapter.notifyDataSetChanged();
                    haveDiary(diaries);
                    mDrawerLayout.closeDrawers();

                }
            });

            mEditDiarySort.setVisibility(isEdit == true ? VISIBLE : INVISIBLE);
            mEditDiarySort.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), DiarySortEditActivity.class);
                    intent.putExtra(DiarySortEditActivity.EXTRA_DIARY_SORT, mDiarySort.getDiarySortName());
                    startActivity(intent);
                }
            });
        }

        public void bind(DiarySort diarySort) {
            mDiarySort = diarySort;

            mDiarySortName.setText(mDiarySort.getDiarySortName());
        }

    }

    private class DiarySortAdapter extends RecyclerView.Adapter {
        private List<DiarySort> mDiarySorts;

        public DiarySortAdapter(List<DiarySort> diarySorts) {
            mDiarySorts = diarySorts;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new DiarySortHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            DiarySort DiarySort = mDiarySorts.get(position);
            ((DiarySortHolder) holder).bind(DiarySort);
        }

        @Override
        public int getItemCount() {
            return mDiarySorts.size();
        }

        public void setDiarySorts(List<DiarySort> DiarySorts) {
            mDiarySorts = DiarySorts;
        }
    }


    public int selectWeather(Diary diary) {
        String weather = diary.getWeather();
        if (weather == null) {
            return R.mipmap.weather_sun;
        } else if (weather == "雨") {
            return R.mipmap.weather_rain;
        } else if (weather == "雪") {
            return R.mipmap.weather_snow;
        } else if (weather == "阴") {
            return R.mipmap.weather_cloud;
        } else if (weather == "多云") {
            return R.mipmap.weather_overcast;
        } else
            return R.mipmap.weather_sun;
    }
}
