package com.lwkandroid.lib.core.utils.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.lwkandroid.lib.core.utils.common.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;


/**
 * @description: 多样性压缩实现
 * @author: LWK
 * @date: 2020/6/17 14:53
 */
public final class AdvanceCompressEngine
{
    private AdvanceCompressOptions mOptions;

    AdvanceCompressEngine(AdvanceCompressOptions options)
    {
        this.mOptions = options;
    }

    public List<File> compress() throws IOException
    {
        checkOptionNonNull();
        List<File> fileList = mOptions.getFileList();
        List<File> resultList = new LinkedList<>();
        for (File file : fileList)
        {
            resultList.add(compressStep1(file));
        }
        return resultList;
    }

    public Observable<List<File>> compressByRxJava()
    {
        checkOptionNonNull();
        List<File> fileList = mOptions.getFileList();
        return Observable.fromIterable(fileList)
                .map(this::compressStep1)
                .collect((Supplier<List<File>>) LinkedList::new, List::add)
                .flatMapObservable((Function<List<File>, ObservableSource<List<File>>>) Observable::just);
    }

    private File compressStep1(File file) throws IOException
    {
        if (file.length() <= mOptions.getIgnoreBytesSize())
        {
            return file;
        }

        String filePath = file.getAbsolutePath();
        String targetPath = Utils.createOutputFilePath(filePath, mOptions.getCacheFolderPath(),
                mOptions.getRenameImpl(), mOptions.getCompressFormat());

        int[] imgSize = ImageUtils.getImageSize(filePath);
        int width = imgSize[0];
        int height = imgSize[1];
        int degree = ImageUtils.getRotateDegree(filePath);

        long targetSize = mOptions.getMaxBytesSize() > 0 ?
                Math.min(file.length(), mOptions.getMaxBytesSize()) : file.length();

        if (mOptions.getMaxBytesSize() > 0 && mOptions.getMaxBytesSize() < file.length())
        {
            float scale = (float) Math.sqrt(file.length() / mOptions.getMaxBytesSize());
            width = (int) (width / scale);
            height = (int) (height / scale);
        }
        if (mOptions.getMaxWidth() > 0)
        {
            width = Math.min(width, mOptions.getMaxWidth());
        }
        if (mOptions.getMaxHeight() > 0)
        {
            height = Math.min(height, mOptions.getMaxHeight());
        }
        float scale = Math.min((float) width / imgSize[0], (float) height / imgSize[1]);
        width = (int) (imgSize[0] * scale);
        height = (int) (imgSize[1] * scale);

        //不压缩
        if (mOptions.getMaxBytesSize() >= file.length() && scale == 1)
        {
            return file;
        }

        return compressStep2(filePath, targetPath, width, height, degree, targetSize);
    }

    private File compressStep2(String filePath, String targetPath,
                               int targetWidth, int targetHeight,
                               int degree, double targetSize) throws IOException
    {
        Bitmap thumbBitmap = compressStep3(filePath, targetWidth, targetHeight);
        thumbBitmap = rotatingImage(thumbBitmap, degree);
        return saveImage(targetPath, thumbBitmap, targetSize);
    }

    private Bitmap compressStep3(String filePath, int targetWidth, int targetHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int outH = options.outHeight;
        int outW = options.outWidth;
        int inSampleSize = 1;

        while (outH / inSampleSize > targetHeight || outW / inSampleSize > targetWidth)
        {
            inSampleSize *= 2;
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    private File saveImage(String filePath, Bitmap bitmap, double targetSize) throws IOException
    {
        ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());

        int quality = 100;
        bitmap.compress(mOptions.getCompressFormat(), quality, mByteArrayOutputStream);

        while (mByteArrayOutputStream.size() / 1024 > targetSize && quality > 6)
        {
            mByteArrayOutputStream.reset();
            quality -= 6;
            bitmap.compress(mOptions.getCompressFormat(), quality, mByteArrayOutputStream);
        }
        bitmap.recycle();

        FileOutputStream fos = new FileOutputStream(filePath);
        mByteArrayOutputStream.writeTo(fos);
        fos.close();

        return new File(filePath);
    }

    private Bitmap rotatingImage(Bitmap bitmap, int degree)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void checkOptionNonNull()
    {
        if (mOptions == null)
        {
            throw new IllegalArgumentException("CompressOption can not be null.");
        }
    }
}
