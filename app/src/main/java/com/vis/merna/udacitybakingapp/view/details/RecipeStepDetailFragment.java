package com.vis.merna.udacitybakingapp.view.details;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeStepDetailFragment extends Fragment {

    public static final String STEP_KEY = "STEP_KEY";
    private static final String PLAY_WHEN_READY_KEY = "PLAY_WHEN_READY_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";

    private Step step;
    private Unbinder unbinder;
    private SimpleExoPlayer exoPlayer;
    private long currentPosition;
    private boolean playWhenReady;

    @BindView(R.id.simple_exo_player_view)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.step_long_desc_text_view)
    TextView stepLongDescTextView;
    @BindView(R.id.step_thumbnail_image)
    ImageView imageView;

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(step.getVideoURL()))
            initializePlayer(Uri.parse(step.getVideoURL()));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(POSITION_KEY, currentPosition);
        outState.putBoolean(PLAY_WHEN_READY_KEY, playWhenReady);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(STEP_KEY)) {
            step = getArguments().getParcelable(STEP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail_fragment, container, false);
        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION_KEY)) {
            currentPosition = savedInstanceState.getLong(POSITION_KEY);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
        }
        unbinder = ButterKnife.bind(this, rootView);

        stepLongDescTextView.setText(step.getDescription());

        if (step.getThumbnailURL() != null) {
            Picasso.get().load(step.getThumbnailURL()).into(imageView);
        }

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create a default TrackSelector
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            // Create the player
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);


            // Bind the player to the view.
            exoPlayerView.setPlayer(exoPlayer);
            // Measures bandwidth during playback. Can be null if not required.
            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
            // Prepare the player with the source.
            exoPlayer.prepare(videoSource);


            if (currentPosition != 0)
                exoPlayer.seekTo(currentPosition);

            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayerView.setVisibility(View.VISIBLE);
        }

    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            currentPosition = exoPlayer.getCurrentPosition();

            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
