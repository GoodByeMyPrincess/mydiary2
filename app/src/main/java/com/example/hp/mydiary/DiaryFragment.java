package com.example.hp.mydiary;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hp.mydiary.DiarySort.DiarySort;
import com.example.hp.mydiary.Interface.IBackInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DiaryFragment extends Fragment {
    private static final String ARG_DIARY_ID = "diary_id";
    public static final String EXTRA_DIARY_ID =
            "com.example.hp.mydiary.diary";
    public static final String EXTRA_DIARY_ID2 =
            "com.example.hp.mydiary.diary2";
    public static final String EXTRA_DIARY_ID3 =
            "com.example.hp.mydiary.diary3";

    private Diary mDiary;
    private TextView mDateTextView;
    private Spinner mWeatherSpinner;
    private Spinner mDiarySortSpinner;
    private EditText mDiaryTitleTextView;
    private TextInputEditText mDiaryTextInputEditText;
    private IBackInterface mIBackInterface;
    private Toolbar mToolbar;
    private boolean isAddNew;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        UUID diaryId = (UUID) getArguments()
                .getSerializable(ARG_DIARY_ID);

        if (diaryId == null) {
            isAddNew = true;
            mDiary = new Diary();
        } else {
            mDiary = DiaryLab.get(getActivity()).getDiary(diaryId);
            isAddNew = false;
        }

        mToolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_action_back);
            mToolbar.setTitle("");
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mDateTextView = (TextView) view.findViewById(R.id.date_text_view);
        mWeatherSpinner = (Spinner) view.findViewById(R.id.weather_spinner);
        mDiarySortSpinner = (Spinner) view.findViewById(R.id.diary_sort_spinner);
        mDiaryTitleTextView = (EditText) view.findViewById(R.id.ed_title);
        mDiaryTextInputEditText = (TextInputEditText) view.findViewById(R.id.diary_text_input_edit_text);

        mIBackInterface = (IBackInterface) getActivity();
        mIBackInterface.setSelectFragment(this);

        final String mDate = (String) DateFormat.format("yyyy年 M月dd日 E", mDiary.getDate());
        mDateTextView.setText(mDate);
        mDiaryTitleTextView.setText(mDiary.getTitle());
        mDiaryTextInputEditText.setText(mDiary.getDiaryText());


        List<String> mDiarySorts = new ArrayList<>();
        List<DiarySort> diarySorts = DiaryLab.get(getActivity()).getDiarySorts();

        for (DiarySort diarySort : diarySorts) {
            mDiarySorts.add(diarySort.getDiarySortName());
        }

        String[] diarySorts1 = new String[mDiarySorts.size()];
        mDiarySorts.toArray(diarySorts1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, diarySorts1);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DiarySort sort = DiaryLab.get(getActivity()).getDiarySort(mDiary.getDiarySortId());

        int position;
        if (sort == null) {
            position = 0;
        } else {
            position = mDiarySorts.indexOf(sort.getDiarySortName());
        }

        mDiarySortSpinner.setAdapter(adapter1);
        mDiarySortSpinner.setSelection(position);
        mDiarySortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String diarySortName = adapterView.getItemAtPosition(i).toString();
                int diarySortId = DiaryLab.get(getActivity()).getRowId(diarySortName);
                mDiary.setDiarySortId(diarySortId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] mItems = getResources().getStringArray(R.array.weather_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mWeatherSpinner.setAdapter(adapter);
        mWeatherSpinner.setSelection(selectWeather(mDiary));
        mWeatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                mDiary.setWeather(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mDiaryTitleTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDiary.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDiaryTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDiary.setDiaryText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!isAddNew) {
            DiaryLab.get(getActivity())
                    .updateDiaries(mDiary);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.diary_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_diary:
                DiaryLab.get(getActivity()).delete(mDiary);
                Toast.makeText(getActivity(), "日记删除成功!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                return true;
            case android.R.id.home:
                if (isAddNew) {
                    if (mDiary.getTitle() == null && mDiary.getDiaryText() == null) {
                        Toast.makeText(getActivity(), "日记未保存!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        return true;
                    }
                    if (mDiary.getDiaryText() != null && mDiary.getTitle() == null
                            || mDiary.getTitle() == "") {
                        mDiary.setTitle("无标题");
                        DiaryLab.get(getActivity()).addDiary(mDiary);
                        Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_SHORT).show();
                    } else {
                        DiaryLab.get(getActivity()).addDiary(mDiary);
                        Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    DiaryLab.get(getActivity()).updateDiaries(mDiary);
                }

                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static DiaryFragment newInstance(UUID diaryId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIARY_ID, diaryId);


        DiaryFragment diaryFragment = new DiaryFragment();
        diaryFragment.setArguments(args);
        return diaryFragment;
    }

    private int selectWeather(Diary diary) {
        String weather = diary.getWeather();
        if (weather == null) {
            return 0;
        }
        if (weather.equals("晴")) {
            return 0;
        } else if (weather.equals("雨")) {
            return 1;
        } else if (weather.equals("雪")) {
            return 2;
        } else if (weather.equals("阴")) {
            return 3;
        } else if (weather.equals("多云")) {
            return 4;
        } else
            return 0;
    }

    public Diary onBackPressed() {
        return mDiary;
    }
}
