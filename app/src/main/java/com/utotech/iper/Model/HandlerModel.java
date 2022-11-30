package com.utotech.iper.Model;

import android.os.Handler;

public class HandlerModel {
    private Handler handler;
    private Runnable runnable;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
