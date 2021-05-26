package android.app;

import com.lwkandroid.lib.core.annotation.NotProguard;

/**
 * Description:利用Framework层的ActivityThread执行一些方法
 *
 * @author LWK
 * @date 2020/2/15
 */
@NotProguard
public final class ActivityThread
{
    public static Application currentApplication()
    {
        throw new RuntimeException("Stub!");
    }
}
