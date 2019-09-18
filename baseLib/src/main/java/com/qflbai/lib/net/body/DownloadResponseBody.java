package com.qflbai.lib.net.body;

import android.annotation.SuppressLint;

import com.qflbai.lib.net.listener.DownloadListener;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author WenXian Bai
 * @Date: 2018/3/29.
 * @Description: 下载body
 */

public class DownloadResponseBody extends ResponseBody {
    private ResponseBody responseBody;

    private DownloadListener downloadListener;

    // BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody, DownloadListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private DownloadInfo mDownloadInfo;
    private Long preProgress;

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                if (null != downloadListener) {
                    if (bytesRead != -1) {
                        if (mDownloadInfo == null) {
                            mDownloadInfo = new DownloadInfo();
                            preProgress = responseBody.contentLength();
                        }
                        long contentLength = responseBody.contentLength();
                        long progress = totalBytesRead * 100 / contentLength;
                        if (progress == preProgress) {
                            return bytesRead;
                        }
                        preProgress = progress;
                        mDownloadInfo.setTotalSize(contentLength);
                        mDownloadInfo.setProgress(progress);
                        mDownloadInfo.setCurrentSize(totalBytesRead);
                        updata(mDownloadInfo);
                    }
                }
                return bytesRead;
            }
        };
    }


    @SuppressLint("CheckResult")
    private void updata(final DownloadInfo downloadInfo) {
        Observable.just(downloadInfo)
                .distinct(new Function<DownloadInfo, Long>() {
                    @Override
                    public Long apply(DownloadInfo downloadInfo) throws Exception {
                        return downloadInfo.getProgress();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadInfo>() {
                    @Override
                    public void accept(DownloadInfo downloadInfo) throws Exception {
                        downloadListener.onLoading(downloadInfo);
                    }
                });

    }
}
