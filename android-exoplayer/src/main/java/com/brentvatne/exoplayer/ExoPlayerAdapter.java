package com.brentvatne.exoplayer;

import android.content.Context;
import android.view.WindowManager;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;

import de.spring.mobile.StreamAdapter;

public class ExoPlayerAdapter implements StreamAdapter {
    private SimpleExoPlayer player;
    private Context context;
    private String version;

    public ExoPlayerAdapter(SimpleExoPlayer player, Context context, String appReleaseVersion) {
        if (player == null) {
            throw new NullPointerException("player may be null");
        }
        this.player = player;
        this.context = context;
        this.version = appReleaseVersion;
    }

    @Override
    public Meta getMeta() {
        return new Meta() {
            @Override
            public String getPlayerName() {
                return player.getClass().toString();
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
            return (int) Math.round(player.getCurrentPosition()/1000.0);
        } catch(Exception e) {
            return 0;
        }
    }

    @Override
    public int getDuration() {
        try {
            return (int) Math.round(player.getDuration()/1000.0);
        } catch(Exception e) {
            return 0;
        }
    }

    @Override
    public int getWidth() {
        try {
            Format f =  player.getVideoFormat();
            if (f != null && f.width > 0) {
                return f.width;
            } else {
                return 0;
            }
        } catch(Exception e) {
            return 0;
        }
    }

    @Override
    public int getHeight() {
        try {
            Format f =  player.getVideoFormat();
            if (f != null && f.width > 0) {
                return f.height;
            } else {
                return 0;
            }
        } catch(Exception e) {
            return 0;
        }
    }

    @Override
    public boolean isCasting() {
        return false;
    }
}

