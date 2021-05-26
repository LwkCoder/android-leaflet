package com.lwkandroid.lib.core.net.cookie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by LWK
 * Cookie管理类
 *
 * @author LWK
 */
public class CookieManager implements ICookieJar
{
    private static PersistentCookieStore cookieStore;

    private void checkNotNull()
    {
        if (cookieStore == null)
        {
            cookieStore = new PersistentCookieStore();
        }
    }

    @Override
    public void add(List<Cookie> cookies)
    {
        checkNotNull();
        cookieStore.addCookies(cookies);
    }

    @Override
    public void remove(HttpUrl url, Cookie cookie)
    {
        checkNotNull();
        cookieStore.remove(url, cookie);
    }

    @Override
    public void clear()
    {
        checkNotNull();
        cookieStore.removeAll();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
    {
        checkNotNull();
        if (cookies != null && !cookies.isEmpty())
        {
            for (Cookie item : cookies)
            {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url)
    {
        checkNotNull();
        List<Cookie> cookies = cookieStore.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
