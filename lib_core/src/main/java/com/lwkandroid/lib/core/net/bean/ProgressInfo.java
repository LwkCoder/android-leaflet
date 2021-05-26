package com.lwkandroid.lib.core.net.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lwkandroid.lib.core.annotation.NotProguard;


/**
 * OkHttp加载过程数据
 *
 * @author LWK
 */
@NotProguard
public class ProgressInfo implements Parcelable
{
    /**
     * 如果同一个 Url 地址,上一次的上传或下载操作都还没结束,
     * 又开始了新的上传或下载操作(比如用户点击多次点击上传或下载同一个 Url 地址,当然你也可以在上层屏蔽掉用户的重复点击),
     * 此 id (请求开始时的时间)就变得尤为重要,用来区分正在执行的进度信息,因为是以请求开始时的时间作为 id ,所以值越大,说明该请求越新
     */
    private long id;
    /**
     * 当前已上传或下载的总长度
     */
    private long currentBytes;
    /**
     * 数据总长度
     */
    private long contentLength;
    /**
     * 本次调用距离上一次被调用所间隔的时间(毫秒)
     */
    private long intervalTime;
    /**
     * 本次调用距离上一次被调用的间隔时间内上传或下载的byte长度
     */
    private long eachBytes;
    /**
     * 进度是否完成
     */
    private boolean finish;

    public ProgressInfo(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getCurrentBytes()
    {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes)
    {
        this.currentBytes = currentBytes;
    }

    public long getContentLength()
    {
        return contentLength;
    }

    public void setContentLength(long contentLength)
    {
        this.contentLength = contentLength;
    }

    public long getIntervalTime()
    {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime)
    {
        this.intervalTime = intervalTime;
    }

    public long getEachBytes()
    {
        return eachBytes;
    }

    public void setEachBytes(long eachBytes)
    {
        this.eachBytes = eachBytes;
    }

    public boolean isFinish()
    {
        return finish;
    }

    public void setFinish(boolean finish)
    {
        this.finish = finish;
    }

    /**
     * 获取百分比,该计算舍去了小数点,如果你想得到更精确的值,请自行计算
     *
     * @return
     */
    public int getPercent()
    {
        if (getCurrentBytes() <= 0 || getContentLength() <= 0)
        {
            return 0;
        }
        return (int) ((100 * getCurrentBytes()) / getContentLength());
    }

    /**
     * 获取上传或下载网络速度,单位为byte/s,如果你想得到更精确的值,请自行计算
     *
     * @return
     */
    public long getSpeed()
    {
        if (getEachBytes() <= 0 || getIntervalTime() <= 0)
        {
            return 0;
        }
        return getEachBytes() * 1000 / getIntervalTime();
    }

    @Override
    public String toString()
    {
        return "ProgressInfo{" +
                "id=" + id +
                ", currentBytes=" + currentBytes +
                ", contentLength=" + contentLength +
                ", intervalTime=" + intervalTime +
                ", eachBytes=" + eachBytes +
                ", finish=" + finish +
                '}';
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(this.id);
        dest.writeLong(this.currentBytes);
        dest.writeLong(this.contentLength);
        dest.writeLong(this.intervalTime);
        dest.writeLong(this.eachBytes);
        dest.writeByte(this.finish ? (byte) 1 : (byte) 0);
    }

    public ProgressInfo()
    {
    }

    protected ProgressInfo(Parcel in)
    {
        this.id = in.readLong();
        this.currentBytes = in.readLong();
        this.contentLength = in.readLong();
        this.intervalTime = in.readLong();
        this.eachBytes = in.readLong();
        this.finish = in.readByte() != 0;
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>()
    {
        @Override
        public ProgressInfo createFromParcel(Parcel source)
        {
            return new ProgressInfo(source);
        }

        @Override
        public ProgressInfo[] newArray(int size)
        {
            return new ProgressInfo[size];
        }
    };
}
