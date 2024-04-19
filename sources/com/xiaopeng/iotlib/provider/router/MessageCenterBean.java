package com.xiaopeng.iotlib.provider.router;

import com.xiaopeng.iotlib.provider.router.MessageContentBean;
import java.util.List;
import java.util.UUID;
/* loaded from: classes.dex */
class MessageCenterBean {
    static final int CONTENT_POSITION_NOTIFY = 2;
    static final int CONTENT_POSITION_NOTIFY_BOX = 1;
    static final int OPPORTUNITY_GETOFF_SCENE = 4;
    static final int OPPORTUNITY_GETON_MOMENT = 3;
    static final int OPPORTUNITY_GETON_SCENE = 1;
    static final int OPPORTUNITY_OTHER = 0;
    static final int OPPORTUNITY_RUNNING_SCENE = 2;
    static final int OPSITION_ACCOUNT = 9;
    static final int OPSITION_AI = 1;
    static final int OPSITION_CHARGE = 5;
    static final int OPSITION_CONFIGURE = 12;
    static final int OPSITION_DC = 14;
    static final int OPSITION_MUSIC = 16;
    static final int OPSITION_NAV = 2;
    static final int OPSITION_OTA = 11;
    static final int OPSITION_RESOURCE_CENTER = 15;
    static final int OPSITION_WASH = 3;
    static final int TYPE_ACC = 12;
    static final int TYPE_AUDIOBOOKS = 3;
    static final int TYPE_BIRTHDAY = 11;
    static final int TYPE_EASTER_EGG = 100;
    static final int TYPE_HAZE = 20;
    static final int TYPE_HEAVY_FOG = 19;
    static final int TYPE_HEAVY_RAINS = 18;
    static final int TYPE_HEAVY_SNOWFALL = 17;
    static final int TYPE_MAP = 10;
    static final int TYPE_MAP_PARK = 14;
    static final int TYPE_MAP_PATH = 13;
    static final int TYPE_MUSIC = 2;
    static final int TYPE_MUSIC_VIP = 22;
    static final int TYPE_NORMAL = 1;
    static final int TYPE_OTA_FAILED = 7;
    static final int TYPE_OVERTIME = 15;
    static final int TYPE_PATH_SELECT = 16;
    static final int TYPE_PM_25 = 9;
    static final int TYPE_RAINS = 21;
    static final int TYPE_RECHARGE = 4;
    static final int TYPE_RECHARGE_FULL = 8;
    static final int TYPE_SUCCESS = 6;
    static final int TYPE_WELCOME = 5;
    private int bizType;
    private int carState;
    private String content;
    private Content contentObject;
    private String messageId;
    private int messageType;
    private String packName;
    private int priority;
    private int read_state;
    private int scene;
    private long ts;
    private int type;
    private int uid;
    private long validEndTs;
    private long validStartTs;

    private MessageCenterBean() {
    }

    String getMessageId() {
        return this.messageId;
    }

    void setMessageId(String str) {
        this.messageId = str;
    }

    int getMessageType() {
        return this.messageType;
    }

    void setMessageType(int i) {
        this.messageType = i;
    }

    int getType() {
        return this.type;
    }

    void setType(int i) {
        this.type = i;
    }

    long getValidStartTs() {
        return this.validStartTs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setValidStartTs(long j) {
        this.validStartTs = j;
    }

    long getValidEndTs() {
        return this.validEndTs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setValidEndTs(long j) {
        this.validEndTs = j;
    }

    long getTs() {
        return this.ts;
    }

    void setTs(long j) {
        this.ts = j;
    }

    int getBizType() {
        return this.bizType;
    }

    void setBizType(int i) {
        this.bizType = i;
    }

    int getScene() {
        return this.scene;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setScene(int i) {
        this.scene = i;
    }

    int getPriority() {
        return this.priority;
    }

    void setPriority(int i) {
        this.priority = i;
    }

    int getUid() {
        return this.uid;
    }

    void setUid(int i) {
        this.uid = i;
    }

    String getPackName() {
        return this.packName;
    }

    void setPackName(String str) {
        this.packName = str;
    }

    int getCarState() {
        return this.carState;
    }

    void setCarState(int i) {
        this.carState = i;
    }

    int getRead_state() {
        return this.read_state;
    }

    void setRead_state(int i) {
        this.read_state = i;
    }

    String getContent() {
        return this.content;
    }

    void setContent(String str) {
        this.content = str;
    }

    Content getContentObject() {
        return this.contentObject;
    }

    void setContentObject(Content content) {
        this.contentObject = content;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MessageContentBean getContentBean() {
        Content content = this.contentObject;
        if (content == null) {
            return null;
        }
        return content.getContent();
    }

    List<MessageContentBean.MsgButton> getButtonList() {
        if (getContentBean() != null) {
            return getContentBean().getButtons();
        }
        return null;
    }

    MessageContentBean.MsgButton getButton(int i) {
        if (getContentBean() != null) {
            return getContentBean().getButton(i);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MessageCenterBean create(int i, MessageContentBean messageContentBean) {
        MessageCenterBean messageCenterBean = new MessageCenterBean();
        messageCenterBean.setMessageId(UUID.randomUUID().toString());
        long currentTimeMillis = System.currentTimeMillis();
        messageCenterBean.setValidStartTs(currentTimeMillis);
        messageCenterBean.setValidEndTs(3600000 + currentTimeMillis);
        messageCenterBean.setMessageType(101);
        messageCenterBean.setType(1);
        messageCenterBean.setTs(currentTimeMillis);
        messageCenterBean.setBizType(i);
        messageCenterBean.setPriority(1);
        Content content = new Content();
        content.setType(1);
        content.setOpportunity(0);
        content.setPosition(1);
        content.setContent(messageContentBean);
        messageCenterBean.setContentObject(content);
        return messageCenterBean;
    }

    public String toString() {
        return "MessageCenterBean{messageId='" + this.messageId + "'}";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Content {
        MessageContentBean content;
        String contentStr;
        int opportunity;
        int position;
        int type;

        Content() {
        }

        int getType() {
            return this.type;
        }

        void setType(int i) {
            this.type = i;
        }

        int getPosition() {
            return this.position;
        }

        void setPosition(int i) {
            this.position = i;
        }

        int getOpportunity() {
            return this.opportunity;
        }

        void setOpportunity(int i) {
            this.opportunity = i;
        }

        MessageContentBean getContent() {
            return this.content;
        }

        void setContent(MessageContentBean messageContentBean) {
            this.content = messageContentBean;
        }

        String getContentStr() {
            return this.contentStr;
        }

        void setContentStr(String str) {
            this.contentStr = str;
        }
    }
}
