package com.lwkandroid.lib.common.widgets.pop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 垂直方向权重
 *
 * @author LWK
 */
@IntDef({
        YGravity.CENTER,
        YGravity.ABOVE,
        YGravity.BELOW,
        YGravity.ALIGN_TOP,
        YGravity.ALIGN_BOTTOM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface YGravity
{
    int CENTER = 0;
    int ABOVE = 1;
    int BELOW = 2;
    int ALIGN_TOP = 3;
    int ALIGN_BOTTOM = 4;
}
