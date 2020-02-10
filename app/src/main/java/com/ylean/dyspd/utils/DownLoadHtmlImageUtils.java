package com.ylean.dyspd.utils;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 项目名：xyjyyth
 * 包名：  com.tecsun.tsb.utils
 * 文件名：DownLoadHtmlImageUtils
 * 创建者：ZhuiMengXiaoLe
 * 日期：  2017/10/31 18:59
 * 描述：  下载Html标签里面的图片
 */
public class DownLoadHtmlImageUtils {
    public volatile boolean isCancle = false;
    // Init Hander
    EventHandler mHandler = new EventHandler(this);

    /**
     * DownLoad the file that must have a url
     *
     * @param url The http url
     * @param savePath The save path
     */
    public void download(final String url, final String savePath) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    sendMessage(FILE_DOWNLOAD_CONNECT);
                    URL sourceUrl = new URL(url);
                    URLConnection conn = sourceUrl.openConnection();
                    InputStream inputStream = conn.getInputStream();

                    int fileSize = conn.getContentLength();

                    File savefile = new File(savePath);
                    if (savefile.exists()) {
                        savefile.delete();
                    }
                    savefile.createNewFile();

                    FileOutputStream outputStream = new FileOutputStream(savePath);

                    byte[] buffer = new byte[1024];
                    int readCount = 0;
                    int readNum = 0;
                    int prevPercent = 0;
                    while ((readNum = inputStream.read(buffer)) != -1) {
                        if (readNum > -1) {
                            readCount = readCount + readNum;
                            outputStream.write(buffer, 0, readNum);

                            int percent = (readCount * 100 / fileSize);
                            if (percent > prevPercent) {
                                // send the progress
                                sendMessage(FILE_DOWNLOAD_UPDATE, percent, readCount);
                                prevPercent = percent;
                            }

                            if (isCancle) {
                                outputStream.close();
                                sendMessage(FILE_DOWNLOAD_ERROR, new Exception("Stop"));
                                break;
                            }
                        }
                    }

                    outputStream.close();
                    if (!isCancle) {
                        sendMessage(FILE_DOWNLOAD_COMPLETE, url);
                    }

                } catch (Exception e) {
                    sendMessage(FILE_DOWNLOAD_ERROR, e);
                    // Log.e("MyError", e.toString());
                }
            }
        }).start();
    }

    /**
     * send message to handler
     *
     * @param what handler what
     * @param obj handler obj
     */
    private void sendMessage(int what, Object obj) {
        // init the handler message
        Message msg = mHandler.obtainMessage(what, obj);
        // send message
        mHandler.sendMessage(msg);
    }

    private void sendMessage(int what) {
        Message msg = mHandler.obtainMessage(what);
        mHandler.sendMessage(msg);
    }

    private void sendMessage(int what, int arg1, int arg2) {
        Message msg = mHandler.obtainMessage(what, arg1, arg2);
        mHandler.sendMessage(msg);
    }

    public void setCancle() {
        this.isCancle = true;
    }

    private static final int FILE_DOWNLOAD_CONNECT = 0;
    private static final int FILE_DOWNLOAD_UPDATE = 1;
    private static final int FILE_DOWNLOAD_COMPLETE = 2;
    private static final int FILE_DOWNLOAD_ERROR = -1;

    // defined the handler
    private class EventHandler extends Handler {
        private final DownLoadHtmlImageUtils mManager;

        public EventHandler(DownLoadHtmlImageUtils manager) {
            mManager = manager;
        }

        // do the receive message
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case FILE_DOWNLOAD_CONNECT:
                    if (mOnDownloadListener != null)
                        mOnDownloadListener.onDownloadConnect(mManager);
                    break;
                case FILE_DOWNLOAD_UPDATE:
                    if (mOnDownloadListener != null)
                        mOnDownloadListener.onDownloadUpdate(mManager, msg.arg1);
                    break;
                case FILE_DOWNLOAD_COMPLETE:
                    if (mOnDownloadListener != null)
                        mOnDownloadListener.onDownloadComplete(mManager, msg.obj);
                    break;
                case FILE_DOWNLOAD_ERROR:
                    if (mOnDownloadListener != null)
                        mOnDownloadListener.onDownloadError(mManager, (Exception) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }

    // defined connection listener
    private OnDownloadListener mOnDownloadListener;

    public interface OnDownloadListener {
        void onDownloadConnect(DownLoadHtmlImageUtils manager);
        void onDownloadUpdate(DownLoadHtmlImageUtils manager, int percent);
        void onDownloadComplete(DownLoadHtmlImageUtils manager, Object result);
        void onDownloadError(DownLoadHtmlImageUtils manager, Exception e);
    }

    public void setOnDownloadListener(OnDownloadListener listener) {
        mOnDownloadListener = listener;
    }
}