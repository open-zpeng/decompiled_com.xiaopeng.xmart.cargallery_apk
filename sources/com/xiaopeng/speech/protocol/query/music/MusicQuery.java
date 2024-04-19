package com.xiaopeng.speech.protocol.query.music;

import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.xmart.cargallery.bean.BIConfig;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class MusicQuery extends SpeechQuery<IMusicQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String getPlayInfo(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayInfo();
    }

    protected String getPlaylistHistory(String event, String data) {
        int count = 1;
        try {
            JSONObject jsonObject = new JSONObject(data);
            count = jsonObject.optInt("count", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IMusicQueryCaller) this.mQueryCaller).getHistoryPlayInfo(count);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getInfoTite(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayTitle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getInfoArtist(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayArtist();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getInfoAlbum(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayAlbum();
    }

    protected String getInfoLyric(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayLyric();
    }

    protected String getInfoCategory(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayCategory();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPlayType(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isPlaying(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isPlaying();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasBluetoothMusicList(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).hasBluetoothMusicList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isHistoryEmpty(String event, String data) {
        int type = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            type = jsonObject.optInt(BIConfig.PROPERTY.DATA_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IMusicQueryCaller) this.mQueryCaller).isHistoryEmpty(type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isPlaySimilar(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isPlaySimilar();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCollectListEmpty(String event, String data) {
        int type = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            type = jsonObject.optInt(BIConfig.PROPERTY.DATA_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IMusicQueryCaller) this.mQueryCaller).isCollectListEmpty(type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCanCollected(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isCanCollected();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isBtConnected(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isBtConnected();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isKuGouAuthed(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isKuGouAuthed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getUsbState(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).getUsbState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isMusicAccountLogin(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isMusicAccountLogin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isQualityPageOpend(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isQualityPageOpend();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isXimalayaAccountLogin(String event, String data) {
        return ((IMusicQueryCaller) this.mQueryCaller).isXimalayaAccountLogin();
    }
}
