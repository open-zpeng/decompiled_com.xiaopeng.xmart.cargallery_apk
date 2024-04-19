package com.xiaopeng.share.task;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.share.callback.ShareCallback;
import com.xiaopeng.share.share.ShareBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/* loaded from: classes.dex */
public class FilePreCheckProcess extends ShareProcess {
    private static final String TAG = "FilePreCheckProcess";
    String coverDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xpShare/cover/";

    @Override // com.xiaopeng.share.task.ShareProcess
    public boolean process(ShareBuilder shareBuilder) {
        LogUtils.d(TAG, "FilePreCheckProcess Process Start...");
        LogUtils.d(TAG, "Now the ShareBuilder Content: " + shareBuilder.toString());
        switch (shareBuilder.getShareType()) {
            case 0:
                return new LoginProcess().process(shareBuilder);
            case 1:
                return checkImageFile(shareBuilder);
            case 2:
                return checkVideoFile(shareBuilder);
            default:
                onError(21, ShareCallback.PRE_CHECK_TYPE_ERROR_MESSAGE, shareBuilder);
                return false;
        }
    }

    private boolean checkImageFile(ShareBuilder shareBuilder) {
        int imgLength;
        if (shareBuilder == null || shareBuilder.getImagesPath() == null || (imgLength = shareBuilder.getImagesPath().size()) == 0 || imgLength > shareBuilder.getLimitStrategy().imageMaxAmount()) {
            return false;
        }
        for (int i = 0; i < imgLength; i++) {
            File file = new File(shareBuilder.getImagesPath().get(i));
            if (!file.exists()) {
                return false;
            }
        }
        return new LoginProcess().process(shareBuilder);
    }

    private boolean checkVideoFile(ShareBuilder shareBuilder) {
        if (shareBuilder == null || shareBuilder.getVideoPath() == null) {
            return false;
        }
        File file = new File(shareBuilder.getVideoPath());
        if (!file.exists()) {
            onError(17, ShareCallback.PRE_CHECK_FILE_NOT_EXIT_MESSAGE, shareBuilder);
            return false;
        } else if (file.length() > shareBuilder.getLimitStrategy().videoMaxSize()) {
            onError(18, ShareCallback.PRE_CHECK_VIDEO_TOO_LARGE_MESSAGE, shareBuilder);
            return false;
        } else {
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
            metadataRetriever.setDataSource(shareBuilder.getVideoPath());
            String duration = metadataRetriever.extractMetadata(9);
            if (duration == null) {
                return false;
            }
            int du = Integer.parseInt(duration);
            if (du > shareBuilder.getLimitStrategy().videoMaxDuration()) {
                onError(19, ShareCallback.PRE_CHECK_DURATION_TOO_LARGE_MESSAGE, shareBuilder);
                return false;
            }
            String height = metadataRetriever.extractMetadata(19);
            String width = metadataRetriever.extractMetadata(18);
            int he = Integer.parseInt(height);
            int wi = Integer.parseInt(width);
            shareBuilder.setVideoWidth(wi);
            shareBuilder.setVideoHeight(he);
            Bitmap cover = metadataRetriever.getFrameAtTime();
            if (!saveVideoCover(cover, shareBuilder)) {
                onError(20, ShareCallback.PRE_CHECK_PARSE_FRAME_FAILURE_MESSAGE, shareBuilder);
                metadataRetriever.release();
                return false;
            }
            metadataRetriever.release();
            return new LoginProcess().process(shareBuilder);
        }
    }

    private boolean saveVideoCover(Bitmap coverBitmap, ShareBuilder shareBuilder) {
        File dir = new File(this.coverDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String coverPath = "cover_" + System.currentTimeMillis() + ".jpg";
        File coverFile = new File(this.coverDir + coverPath);
        try {
            coverFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream out = new FileOutputStream(coverFile);
            coverBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            shareBuilder.setVideoCoverPath(coverFile.getAbsolutePath());
            return true;
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            onThrowable(e2, shareBuilder);
            return false;
        } catch (IOException e3) {
            e3.printStackTrace();
            onThrowable(e3, shareBuilder);
            return false;
        }
    }
}
