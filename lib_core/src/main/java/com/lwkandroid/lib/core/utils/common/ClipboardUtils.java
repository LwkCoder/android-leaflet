package com.lwkandroid.lib.core.utils.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lwkandroid.lib.core.context.AppContext;


/**
 * 剪贴板相关工具类
 *
 * @author LWK
 */
public final class ClipboardUtils
{

    private ClipboardUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    public static void copyText(final CharSequence text)
    {
        ClipboardManager clipboard = (ClipboardManager) AppContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText()
    {
        ClipboardManager clipboard = (ClipboardManager) AppContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0)
        {
            return clip.getItemAt(0).coerceToText(AppContext.get());
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     *
     * @param uri uri
     */
    public static void copyUri(final Uri uri)
    {
        ClipboardManager clipboard = (ClipboardManager) AppContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newUri(AppContext.get().getContentResolver(), "uri", uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    public static Uri getUri()
    {
        ClipboardManager clipboard = (ClipboardManager) AppContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0)
        {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    public static void copyIntent(final Intent intent)
    {
        ClipboardManager clipboard = (ClipboardManager) AppContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public static Intent getIntent()
    {
        ClipboardManager clipboard = (ClipboardManager) AppContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0)
        {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}
