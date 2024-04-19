package com.xiaopeng.iotlib.model.activity;

import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.config.LogConfig;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.util.HashMap;
/* loaded from: classes.dex */
public class ActivityState {
    private HashMap<PageId, HashMap<ActivityStateItem, State>> sActivityCreatedMap;

    /* loaded from: classes.dex */
    public enum State {
        CREATE,
        START,
        RESUME,
        PAUSE,
        STOP
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final ActivityState INSTANCE = new ActivityState();

        private SingletonHolder() {
        }
    }

    private ActivityState() {
        this.sActivityCreatedMap = new HashMap<>();
    }

    public static ActivityState getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setActivityState(PageId pageId, ActivityStateItem activityStateItem, State state) {
        HashMap<ActivityStateItem, State> activityState = getActivityState(pageId);
        if (activityState == null) {
            activityState = new HashMap<>();
            this.sActivityCreatedMap.put(pageId, activityState);
        }
        activityState.put(activityStateItem, state);
        LogUtils.d(LogConfig.TAG_ACTIVITY, String.format("setActivityState pageId %s , state %s,item : %s ,pagesize: %s", pageId, state, Integer.valueOf(activityStateItem.hashCode()), Integer.valueOf(activityState.size())));
    }

    public HashMap<ActivityStateItem, State> getActivityState(PageId pageId) {
        return this.sActivityCreatedMap.get(pageId);
    }

    public State getActivityState(PageId pageId, ActivityStateItem activityStateItem) {
        HashMap<ActivityStateItem, State> activityState = getActivityState(pageId);
        if (activityState != null) {
            return activityState.get(activityStateItem);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clearActivityState(PageId pageId, ActivityStateItem activityStateItem) {
        HashMap<ActivityStateItem, State> activityState = getActivityState(pageId);
        if (activityState != null) {
            activityState.remove(activityStateItem);
        }
        LogUtils.d(LogConfig.TAG_ACTIVITY, String.format("clearActivityState pageId %s, size %s", pageId, Integer.valueOf(this.sActivityCreatedMap.size())));
    }
}
