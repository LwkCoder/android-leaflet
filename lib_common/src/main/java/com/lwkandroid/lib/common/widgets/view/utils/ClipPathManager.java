package com.lwkandroid.lib.common.widgets.view.utils;

import android.graphics.Path;

/**
 * @description: 裁剪管理类
 * @author: LWK
 * @date: 2020/8/19 15:00
 */
public class ClipPathManager
{
    protected final Path path = new Path();

    public ClipPathManager()
    {
    }

    /**
     * 设置裁剪布局
     *
     * @param width
     * @param height
     */
    public void setupClipLayout(int width, int height)
    {
        path.reset();
        Path clipPath = null;
        if (createClipPath != null)
        {
            clipPath = createClipPath.createClipPath(width, height);
        }
        if (clipPath != null)
        {
            path.set(clipPath);
        }
    }

    /**
     * 设置裁剪路径
     *
     * @return
     */
    public Path getClipPath()
    {
        return path;
    }

    /**
     * 创建Path接口
     */
    private ClipPathCreator createClipPath = null;

    public void setClipPathCreator(ClipPathCreator createClipPath)
    {
        this.createClipPath = createClipPath;
    }

    public interface ClipPathCreator
    {
        Path createClipPath(int width, int height);
    }
}
