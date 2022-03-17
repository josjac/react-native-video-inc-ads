package com.brentvatne.exoplayer;

import android.content.Context;
import android.view.WindowManager;
import android.util.Log;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;

import de.spring.mobile.StreamAdapter;

public class ExoPlayerAdapter implements StreamAdapter {
    private Context context;
    private String version;
    private String playerName = "";
    private int currentPosition = 0;
    private int currentDuration = 0;
    private int width = 0;
    private int height = 0;
    private boolean isLive = false;

    public ExoPlayerAdapter(String playerName, boolean isLive, Context context, String appReleaseVersion) {
        this.context = context;
        this.version = appReleaseVersion;
        this.playerName = playerName;
        this.isLive = isLive;
    }

    @Override
    public Meta getMeta() {
        return new Meta() {
            @Override
            public String getPlayerName() {
                return playerName;
            }

            @Override
            public String getPlayerVersion() {
                return version;
            }

            @Override
            public int getScreenWidth() {
                return (((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()).getHeight();
            }

            @Override
            public int getScreenHeight() {
                return (((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()).getWidth();
            }
        };
    }

    @Override
    public int getPosition() {
        try {
            int p = (int) Math.round(this.currentPosition/1000.0);
            return p;
        } catch(Exception e) {
            Log.d("2cnt ExoPlayerAdapter.getPosition exception", e.toString());
            return 0;
        }
    }

    @Override
    public int getDuration() {
        try {
            int p = 0;

            if (this.isLive == false) {
                p = (int) Math.round(this.currentDuration/1000.0);
            }

            return p;
        } catch(Exception e) {
            Log.d("2cnt ExoPlayerAdapter.getDuration exception", e.toString());
            return 0;
        }
    }

    @Override
    public int getWidth() {
        try {
            return this.width;
        } catch(Exception e) {
            Log.d("2cnt ExoPlayerAdapter.getWidth exception", e.toString());
            return 0;
        }
    }

    @Override
    public int getHeight() {
        try {
            return this.height;
        } catch(Exception e) {
            Log.d("2cnt ExoPlayerAdapter.getHeight exception", e.toString());
            return 0;
        }
    }

    @Override
    public boolean isCasting() {
        return false;
    }

    public void setPosition(int payload) {
        this.currentPosition = payload;
    }

    public void setDuration(int payload) {
        this.currentDuration = payload;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

