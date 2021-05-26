package com.lwkandroid.lib.common.widgets.ptr;

/**
 * {@link PTRLayout}的默认RefreshView偏移量计算器：
 * 偏移范围限定在[refreshInitOffset, refreshEndOffset]
 *
 * @author LWK
 */
public class PRTDefaultRefreshOffsetCalculator implements PTRLayout.RefreshOffsetCalculator
{
    @Override
    public int calculateRefreshOffset(int refreshInitOffset, int refreshEndOffset, int refreshViewHeight, int targetCurrentOffset, int targetInitOffset, int targetRefreshOffset)
    {
        int refreshOffset;
        if (targetCurrentOffset >= targetRefreshOffset)
        {
            refreshOffset = refreshEndOffset;
        } else if (targetCurrentOffset <= targetInitOffset)
        {
            refreshOffset = refreshInitOffset;
        } else
        {
            float percent = (targetCurrentOffset - targetInitOffset) * 1.0f / (targetRefreshOffset - targetInitOffset);
            refreshOffset = (int) (refreshInitOffset + percent * (refreshEndOffset - refreshInitOffset));
        }
        return refreshOffset;
    }
}
