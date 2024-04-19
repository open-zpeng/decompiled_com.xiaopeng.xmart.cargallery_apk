package com.xiaopeng.share.bean;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class ShareContentBean {
    private String article;
    private String content;
    private int height;
    private int jump;
    private int mode;
    private String subject;
    private long uid;
    private int width;
    private int type = 1;
    private int tab = 1;
    private int subClassId = 9;
    private int fclassId = 4;
    private List<Integer> topics = new ArrayList();
    private List<String> customTopics = new ArrayList();
    private List<String> useTopics = new ArrayList();
    private List<String> attachments = new ArrayList();
    private List<VideosBean> videos = new ArrayList();
    private List<Integer> polls = new ArrayList();

    private ShareContentBean() {
    }

    public static ShareContentBean buildTextShare(long uid, String content) {
        return buildImageShare(uid, content, new ArrayList());
    }

    public static ShareContentBean buildImageShare(long uid, String content, List<String> images) {
        ShareContentBean shareContentBean = new ShareContentBean();
        shareContentBean.setUid(uid);
        shareContentBean.setContent(content);
        shareContentBean.setSubject(createSubject(content));
        shareContentBean.setAttachments(images);
        return shareContentBean;
    }

    public static ShareContentBean buildVideoShare(long uid, String content, String coverPath, String videoPath, int videoWidth, int videoHeight) {
        VideosBean vb = new VideosBean();
        vb.setAttachment(videoPath);
        vb.setCover(coverPath);
        vb.setWidth(videoWidth);
        vb.setHeight(videoHeight);
        vb.setType("VIDEO");
        List<VideosBean> vbs = new ArrayList<>();
        vbs.add(vb);
        ShareContentBean shareContentBean = new ShareContentBean();
        shareContentBean.setUid(uid);
        shareContentBean.setContent(content);
        shareContentBean.setSubject(createSubject(content));
        shareContentBean.setVideos(vbs);
        shareContentBean.setType(3);
        return shareContentBean;
    }

    public static String createSubject(String content) {
        if (content.length() <= 10) {
            return content;
        }
        return content.substring(0, 10);
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTab() {
        return this.tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public int getSubClassId() {
        return this.subClassId;
    }

    public void setSubClassId(int subClassId) {
        this.subClassId = subClassId;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticle() {
        return this.article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getJump() {
        return this.jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFclassId() {
        return this.fclassId;
    }

    public void setFclassId(int fclassId) {
        this.fclassId = fclassId;
    }

    public List<Integer> getTopics() {
        return this.topics;
    }

    public void setTopics(List<Integer> topics) {
        this.topics = topics;
    }

    public List<String> getCustomTopics() {
        return this.customTopics;
    }

    public void setCustomTopics(List<String> customTopics) {
        this.customTopics = customTopics;
    }

    public List<String> getUseTopics() {
        return this.useTopics;
    }

    public void setUseTopics(List<String> useTopics) {
        this.useTopics = useTopics;
    }

    public List<String> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public List<VideosBean> getVideos() {
        return this.videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public List<Integer> getPolls() {
        return this.polls;
    }

    public void setPolls(List<Integer> polls) {
        this.polls = polls;
    }

    /* loaded from: classes.dex */
    public static class VideosBean {
        private int aid;
        private String attachment;
        private String cover;
        private int height;
        private String type;
        private int width;

        public int getAid() {
            return this.aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getAttachment() {
            return this.attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getCover() {
            return this.cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
