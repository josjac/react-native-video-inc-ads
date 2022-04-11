package com.brentvatne.exoplayer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.util.Log;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;

import de.spring.mobile.StreamAdapter;

public class ExoPlayerAdapter implements StreamAdapter {
    private SimpleExoPlayer player;
    private String version;
    private boolean isLiveStream;
    private Context context;
    private Handler handler;
    private int position = 0;
    private int duration = 0;
    private int width = 0;
    private int height = 0;

    public ExoPlayerAdapter(SimpleExoPlayer player, String version, boolean isLiveStream, Context context) {
        if (player == null) {
            throw new NullPointerException("player argument cannot be null");
        }
        this.player = player;
        this.version = version;
        this.isLiveStream = isLiveStream;
        this.context = context;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public Meta getMeta() {
        return new Meta() {
            @Override
            public String getPlayerName() {
                return player.getClass().getCanonicalName();
            }

            @Override
            public String getPlayerVersion() {
                return version;
            }

            @Override
            public int getScreenWidth() {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                (((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()).getMetrics(displayMetrics);
                return displayMetrics.widthPixels;
            }

            @Override
            public int getScreenHeight() {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                (((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()).getMetrics(displayMetrics);
                return displayMetrics.heightPixels;
            }
        };
    }

    @Override
    public int getPosition() {
        try {
            setPosition();
        } catch (Exception e) {
            try {
                handler.post(() -> setPosition());
            } catch (Exception exception) {
                Log.d(ExoPlayerAdapter.class.getSimpleName(), e.getMessage(), exception);
            }
        }
        return position;
    }

    @Override
    public int getDuration() {
        if (!isLiveStream) {
            try {
                setDuration();
            } catch (Exception e) {
                try {
                    handler.post(() -> setDuration());
                } catch (Exception exception) {
                    Log.d(ExoPlayerAdapter.class.getSimpleName(), e.getMessage(), exception);
                }
            }
        }
        return duration;
    }

    @Override
    public int getWidth() {
        try {
            setWidth();
        } catch (Exception e) {
            try {
                handler.post(() -> setWidth());
            } catch (Exception exception) {
                Log.d(ExoPlayerAdapter.class.getSimpleName(), e.getMessage(), exception);
            }
        }
        return width;
    }

    @Override
    public int getHeight() {
        try {
            setHeight();
        } catch (Exception e) {
            try {
                handler.post(() -> setHeight());
            } catch (Exception exception) {
                Log.d(ExoPlayerAdapter.class.getSimpleName(), e.getMessage(), exception);
            }
        }
        return height;
    }

    @Override
    public boolean isCasting() {
        return false;
    }

    private void setPosition() {
        long currentPosition = player.getCurrentPosition();
        if (isLiveStream) {
            Timeline currentTimeline = player.getCurrentTimeline();
            if (!currentTimeline.isEmpty()) {
                currentPosition += Math.abs(currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period()).getPositionInWindowMs());
                position = (int) Math.round(currentPosition / 1000.0);
            }
        } else {
            position = (int) Math.round(currentPosition / 1000.0);
        }
    }

    private void setDuration() {
        duration = (int) Math.round(player.getDuration() / 1000.0);
    }

    private void setWidth() {
        Format format = player.getVideoFormat();
        if (format != null && format.width != Format.NO_VALUE) {
            width = format.width;
        }
    }

    private void setHeight() {
        Format format = player.getVideoFormat();
        if (format != null && format.height != Format.NO_VALUE) {
            height = format.height;
        }
    }
}

