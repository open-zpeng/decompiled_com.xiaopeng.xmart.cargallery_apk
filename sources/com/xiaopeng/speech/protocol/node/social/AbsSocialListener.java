package com.xiaopeng.speech.protocol.node.social;
/* loaded from: classes.dex */
public abstract class AbsSocialListener implements SocialListener {
    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialMotorcadeOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialMotorcadeClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialGrabMic() {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialGrabMicCancel() {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialCreateTopic() {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialReplyTopic() {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialQuitChat() {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialConfirm(String intent) {
    }

    @Override // com.xiaopeng.speech.protocol.node.social.SocialListener
    public void onSocialCancel(String intent) {
    }
}
