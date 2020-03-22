package com.coolweather.ai_lamp.dynamic;

import android.content.Intent;

/**
 * 用于RecyclerView中item点击事件的回调
 */
public interface ButtonInterface {
    void onclick(Intent intent, int pos);
}
