package com.xiaopeng.iotlib.provider.router;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
class MessageContentBean {
    static final int COMMING_SOUND_DEFAULT = 0;
    static final int COMMING_SOUND_NONE = 2;
    static final int COMMING_SOUND_WARNNING = 1;
    static final int EXECUTE_AFTER_TTS = 1;
    static final int EXECUTE_DELAY = 2;
    static final int EXECUTE_USER = 0;
    static final int PERMANENT_MSG = 1;
    static final String SENSING_TYPE_BEHAVIOR = "行为感知";
    static final String SENSING_TYPE_ENTERTAINMENT = "娱乐感知";
    static final String SENSING_TYPE_LIFE = "生活感知";
    static final String SENSING_TYPE_NATURAL = "环境感知";
    static final String SENSING_TYPE_ROAD = "路况感知";
    static final String SENSING_TYPE_SOCIAL = "社交感知";
    static final String SENSING_TYPE_SYSTEM = "系统感知";
    private String backgroundUrl;
    private List<MsgButton> buttons;
    private String callback;
    private boolean cancelFeedbackSound;
    private int comingSoundType;
    private String conditions;
    private int executeDelayTime;
    private int executeType;
    private boolean neverShow = false;
    private int permanent;
    private List<MsgPic> pics;
    private String sensingType;
    private List<String> titles;
    private String tts;
    private int type;
    private long validTime;

    private MessageContentBean() {
    }

    int getType() {
        return this.type;
    }

    void setType(int i) {
        this.type = i;
    }

    List<String> getTitles() {
        return this.titles;
    }

    void setTitles(List<String> list) {
        this.titles = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<MsgButton> getButtons() {
        return this.buttons;
    }

    void setButtons(List<MsgButton> list) {
        this.buttons = list;
    }

    List<MsgPic> getPics() {
        return this.pics;
    }

    void setPics(List<MsgPic> list) {
        this.pics = list;
    }

    int getPermanent() {
        return this.permanent;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setPermanent(int i) {
        this.permanent = i;
    }

    String getTts() {
        return this.tts;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setTts(String str) {
        this.tts = str;
    }

    long getValidTime() {
        return this.validTime;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setValidTime(long j) {
        this.validTime = j;
    }

    String getCallback() {
        return this.callback;
    }

    void setCallback(String str) {
        this.callback = str;
    }

    String getSensingType() {
        return this.sensingType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSensingType(String str) {
        this.sensingType = str;
    }

    int getComingSoundType() {
        return this.comingSoundType;
    }

    void setComingSoundType(int i) {
        this.comingSoundType = i;
    }

    int getExecuteType() {
        return this.executeType;
    }

    void setExecuteType(int i) {
        this.executeType = i;
    }

    int getExecuteDelayTime() {
        return this.executeDelayTime;
    }

    void setExecuteDelayTime(int i) {
        this.executeDelayTime = i;
    }

    boolean isCancelFeedbackSound() {
        return this.cancelFeedbackSound;
    }

    void setCancelFeedbackSound(boolean z) {
        this.cancelFeedbackSound = z;
    }

    boolean isNeverShow() {
        return this.neverShow;
    }

    void setNeverShow(boolean z) {
        this.neverShow = z;
    }

    String getConditions() {
        return this.conditions;
    }

    void setConditions(String str) {
        this.conditions = str;
    }

    String getBackgroundUrl() {
        return this.backgroundUrl;
    }

    void setBackgroundUrl(String str) {
        this.backgroundUrl = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MsgButton getButton(int i) {
        if (getButtons() == null || getButtons().size() <= i) {
            return null;
        }
        return getButtons().get(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MessageContentBean createContent() {
        return new MessageContentBean();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MessageContentBean addTitle(String str) {
        if (this.titles == null) {
            this.titles = new ArrayList();
        }
        this.titles.add(str);
        return this;
    }

    MessageContentBean addPic(MsgPic msgPic) {
        if (this.pics == null) {
            this.pics = new ArrayList();
        }
        this.pics.add(msgPic);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MessageContentBean addButton(MsgButton msgButton) {
        if (this.buttons == null) {
            this.buttons = new ArrayList();
        }
        this.buttons.add(msgButton);
        return this;
    }

    /* loaded from: classes.dex */
    static class MsgPic {
        String content;
        String pack;
        String url;

        MsgPic() {
        }

        static MsgPic createPic(String str, String str2, String str3) {
            MsgPic msgPic = new MsgPic();
            msgPic.setUrl(str);
            msgPic.setPack(str2);
            msgPic.setContent(str3);
            return msgPic;
        }

        String getUrl() {
            return this.url;
        }

        void setUrl(String str) {
            this.url = str;
        }

        String getPack() {
            return this.pack;
        }

        void setPack(String str) {
            this.pack = str;
        }

        String getContent() {
            return this.content;
        }

        void setContent(String str) {
            this.content = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class MsgButton {
        String content;
        String name;
        String notResponseWord;
        String pack;
        String responseGuideTTS;
        String responseTTS;
        String responseWord;
        boolean speechResponse;

        MsgButton() {
        }

        static MsgButton create(String str, String str2, String str3) {
            return create(str, str2, str3, false);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static MsgButton create(String str, String str2, String str3, boolean z) {
            MsgButton msgButton = new MsgButton();
            msgButton.name = str;
            msgButton.pack = str2;
            msgButton.content = str3;
            msgButton.speechResponse = z;
            return msgButton;
        }

        String getName() {
            return this.name;
        }

        void setName(String str) {
            this.name = str;
        }

        String getPack() {
            return this.pack;
        }

        void setPack(String str) {
            this.pack = str;
        }

        String getContent() {
            return this.content;
        }

        void setContent(String str) {
            this.content = str;
        }

        boolean isSpeechResponse() {
            return this.speechResponse;
        }

        void setSpeechResponse(boolean z) {
            this.speechResponse = z;
        }

        String getResponseWord() {
            return this.responseWord;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void setResponseWord(String str) {
            this.responseWord = str;
        }

        String getNotResponseWord() {
            return this.notResponseWord;
        }

        void setNotResponseWord(String str) {
            this.notResponseWord = str;
        }

        String getResponseTTS() {
            return this.responseTTS;
        }

        void setResponseTTS(String str) {
            this.responseTTS = str;
        }

        String getResponseGuideTTS() {
            return this.responseGuideTTS;
        }

        void setResponseGuideTTS(String str) {
            this.responseGuideTTS = str;
        }
    }
}
