package com.brentvatne.exoplayer;

import android.content.Context;

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;

public class DefaultReactExoplayerConfig implements ReactExoplayerConfig {

    private final DefaultBandwidthMeter bandwidthMeter;
    private final Context applicationContext;

    public DefaultReactExoplayerConfig(Context context) {
        this.bandwidthMeter = new DefaultBandwidthMeter.Builder(context).build();
        this.applicationContext = context;
    }

    @Override
    public LoadErrorHandlingPolicy buildLoadErrorHandlingPolicy(int minLoadRetryCount) {
        return new DefaultLoadErrorHandlingPolicy(minLoadRetryCount);
    }

    @Override
    public DefaultBandwidthMeter getBandwidthMeter() {
        return bandwidthMeter;
    }

    public Context getReactApplicationContext() {
        return applicationContext;
    }
}
