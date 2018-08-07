package com.baking.bakingapp.ui.baking_detail.step_detail;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.StepWS;
import com.baking.bakingapp.util.UnitUtil;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailPagerFragment extends Fragment {
    public static String ARG_STEP = "ARG_STEP";
    private StepWS stepWS;
    // 1. Declare a field SimpleExoPlayer
    private SimpleExoPlayer simpleExoPlayer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.player_view)
    PlayerView playerView;

    @BindView(R.id.tv_recipe_step_instruction)
    AppCompatTextView tvRecipeStepInstruction;

    @BindView(R.id.iv_recipe_instruction)
    AppCompatImageView ivRecipeInstruction;

    @BindView(R.id.group_step_detail)
    Group groupStepDetailExcludePlayer;

    @BindView(R.id.constraint_layout_container)
    ConstraintLayout constraintLayoutContainer;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailPagerFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StepDetailPagerFragment newInstance(StepWS stepWS) {
        StepDetailPagerFragment fragment = new StepDetailPagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, stepWS);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            stepWS = getArguments().getParcelable(ARG_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stepdetailpager_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (stepWS != null) {
            if (!TextUtils.isEmpty(stepWS.videoURL)) {
                // In onCreate
                // 2. Create a default TrackSelector
                TrackSelector trackSelector = new DefaultTrackSelector();
                // 3. Create a SimpleExoPlayer instance
                simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
                // 5. Bind the player to the view.
                playerView.setPlayer(simpleExoPlayer);

                // 6. Create a DataSource.Factory instance through which media data is loaded.
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                        Util.getUserAgent(getActivity(), "simpleAudioApp"));

                // 6. Create a MediaSource representing the media to be played.
                // Note that I have my file in main/assets
                Uri audioSourceUri = Uri.parse(stepWS.videoURL);

                MediaSource audioSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(audioSourceUri);
                // Prepare the player with the source.
                simpleExoPlayer.prepare(audioSource);
                playerView.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(stepWS.thumbnailURL)) {
                ivRecipeInstruction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                ivRecipeInstruction.setVisibility(View.VISIBLE);
            } else {
                playerView.setVisibility(View.GONE);
                ivRecipeInstruction.setVisibility(View.GONE);
            }

            tvRecipeStepInstruction.setText(stepWS.description);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Fragment parentFragment = getParentFragment();
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //First Hide other objects (listview or recyclerview), better hide them using Gone.
            groupStepDetailExcludePlayer.setVisibility(View.GONE);
            playerView.setLayoutParams(
                    new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
            hideSystemUI();

            if (parentFragment instanceof StepDetailFragment) {
                ((StepDetailFragment) parentFragment).disableViewPagerAndIndicator();
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Unhide your objects here.
            groupStepDetailExcludePlayer.setVisibility(View.VISIBLE);
            playerView.setLayoutParams(
                    new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT, UnitUtil.dpToPx(200, getActivity())));

            int margin = UnitUtil.dpToPx(16, getActivity());
            //Created Constraint Set.
            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayoutContainer);

            set.connect(playerView.getId(), ConstraintSet.TOP, toolbar.getId(), ConstraintSet.BOTTOM, margin);

            set.connect(playerView.getId(), ConstraintSet.START, constraintLayoutContainer.getId(), ConstraintSet.START, margin);

            set.connect(playerView.getId(), ConstraintSet.END, constraintLayoutContainer.getId(), ConstraintSet.END, margin);

            set.applyTo(constraintLayoutContainer);

            showSystemUI();
            if (parentFragment instanceof StepDetailFragment) {
                ((StepDetailFragment) parentFragment).enableViewPagerAndIndicator();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
    }

    private void pausePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getActivity().getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
