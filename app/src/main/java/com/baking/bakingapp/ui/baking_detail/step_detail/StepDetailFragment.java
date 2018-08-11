package com.baking.bakingapp.ui.baking_detail.step_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.StepWS;
import com.baking.bakingapp.widgets.LockableViewPager;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment {

    public static String ARG_STEP_LIST = "ARG_STEP_LIST";
    public static String ARG_STEP_LIST_POSITION = "ARG_STEP_LIST_POSITION";

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    private StepDetailFragmentPagerAdapter stepDetailFragmentPagerAdapter;
    private List<StepWS> stepWSList;
    private int selectedListPosition = 0;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param stepWSList
     * @return A new instance of fragment StepDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepDetailFragment newInstance(List<StepWS> stepWSList, int selectedListPosition) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_STEP_LIST, (ArrayList<StepWS>) stepWSList);
        args.putInt(ARG_STEP_LIST_POSITION, selectedListPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepWSList = getArguments().getParcelableArrayList(ARG_STEP_LIST);
            selectedListPosition = getArguments().getInt(ARG_STEP_LIST_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupToolbar(toolbar, true);
        setupViewPager(viewPager);
    }

    private void setupViewPager(LockableViewPager viewPager) {
        stepDetailFragmentPagerAdapter = new StepDetailFragmentPagerAdapter(getChildFragmentManager());
        for (StepWS stepWS : stepWSList) {
            stepDetailFragmentPagerAdapter.addFragment(StepDetailPagerFragment.newInstance(stepWS), "Step");
        }

        viewPager.setAdapter(stepDetailFragmentPagerAdapter);
        viewPager.setCurrentItem(selectedListPosition);
        pageIndicatorView.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedListPosition = position;
                setupToolbar(toolbar, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setupToolbar(Toolbar toolbar, boolean setDisplayHomeAsUpEnabled) {
        if (toolbar != null && stepWSList != null && stepWSList.get(selectedListPosition) != null) {
            toolbar.setTitle(stepWSList.get(selectedListPosition).shortDescription);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(setDisplayHomeAsUpEnabled);
        }
    }

    public void disableViewPagerAndIndicator() {
        if (viewPager != null && pageIndicatorView != null && toolbar != null) {
            viewPager.setPagingEnabled(false);
            pageIndicatorView.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
        }
    }

    public void enableViewPagerAndIndicator() {
        if (viewPager != null && pageIndicatorView != null && toolbar != null) {
            viewPager.setPagingEnabled(true);
            pageIndicatorView.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
        }
    }
}
