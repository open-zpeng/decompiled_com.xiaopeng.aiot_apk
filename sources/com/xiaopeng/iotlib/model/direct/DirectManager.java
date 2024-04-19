package com.xiaopeng.iotlib.model.direct;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.lang.ref.WeakReference;
import java.util.HashMap;
/* loaded from: classes.dex */
public class DirectManager {
    private static final String TAG = "DirectManager";
    private String mAuth;
    private Application mContext;
    private final HashMap<PageId, WeakReference<IDirectElementListener>> mDirectListeners;
    private String mElementParameter;
    private ElementDirectTaskData mElementTask;
    private final HashMap<PageId, PageDirect> mPageHashMap;
    private String mScheme;
    private IDirectUriPageIdConverter mUriPageIdConverter;

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final DirectManager INSTANCE = new DirectManager();

        private SingletonHolder() {
        }
    }

    public static DirectManager get() {
        return SingletonHolder.INSTANCE;
    }

    private DirectManager() {
        this.mDirectListeners = new HashMap<>();
        this.mPageHashMap = new HashMap<>();
    }

    public void init(Application application, String str, String str2, String str3, IDirectUriPageIdConverter iDirectUriPageIdConverter) {
        this.mContext = application;
        this.mScheme = str;
        this.mAuth = str2;
        this.mElementParameter = str3;
        this.mUriPageIdConverter = iDirectUriPageIdConverter;
        LogUtils.d("DirectManager", " init= scheme : " + str + " ,auth : " + str2 + " ,elementParameter : " + str3);
    }

    public void addPage(PageDirect pageDirect) {
        pageDirect.setElements(pageDirect.getAction().createElements());
        this.mPageHashMap.put(pageDirect.getPageId(), pageDirect);
        LogUtils.d("DirectManager", " addPage=  " + pageDirect.toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addDirectListener(PageId pageId, IDirectElementListener iDirectElementListener) {
        LogUtils.d("DirectManager", " addDirectListener=  pageId : " + pageId + " ,listener : " + iDirectElementListener);
        this.mDirectListeners.put(pageId, new WeakReference<>(iDirectElementListener));
        ElementDirectTaskData elementDirectTaskData = this.mElementTask;
        if (elementDirectTaskData == null || !pageId.equals(elementDirectTaskData.getPageId())) {
            return;
        }
        iDirectElementListener.onElementDirect(this.mElementTask.getElement());
        this.mElementTask = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeDirectListener(PageId pageId) {
        LogUtils.d("DirectManager", " removeDirectListener=  pageId : " + pageId + " ,listener : " + this.mDirectListeners.remove(pageId));
    }

    public boolean go(String str) {
        if (this.mContext == null) {
            LogUtils.w("DirectManager", " go= mContext is null " + str);
            return false;
        }
        Uri parse = Uri.parse(str);
        if (!isPageDirect(parse)) {
            LogUtils.w("DirectManager", " go= not pageDirect " + str);
            return false;
        }
        PageId convert = this.mUriPageIdConverter.convert(parse);
        LogUtils.i("DirectManager", " go= pageId : " + convert);
        PageDirect pageDirect = this.mPageHashMap.get(convert);
        if (pageDirect == null) {
            LogUtils.w("DirectManager", " go= page is null; uri is invalid : " + str);
            return false;
        } else if (pageDirect.getAction() == null) {
            LogUtils.w("DirectManager", "goPage= page.getAction() is null do nothing" + parse);
            return false;
        } else if (!pageDirect.getAction().isSupport()) {
            LogUtils.w("DirectManager", " go= not support " + str);
            return false;
        } else if (!isElementDirect(parse)) {
            LogUtils.i("DirectManager", " go= not element; goPage " + str);
            goPage(pageDirect, parse);
            return true;
        } else if (pageDirect.getElements() == null) {
            LogUtils.w("DirectManager", " go= page.getElements() is null " + str);
            return false;
        } else {
            ElementDirect element = pageDirect.getAction().getElement(pageDirect, parse.getQueryParameter(this.mElementParameter));
            if (element == null) {
                LogUtils.w("DirectManager", " go= uri's parameter is null " + str);
                return false;
            } else if (element.getAction() != null && !element.getAction().isSupport()) {
                LogUtils.w("DirectManager", " go= element not support " + str);
                return false;
            } else {
                WeakReference<IDirectElementListener> weakReference = this.mDirectListeners.get(convert);
                if (weakReference == null || weakReference.get() == null) {
                    LogUtils.i("DirectManager", " go= listener is null just goPage " + str);
                    this.mElementTask = new ElementDirectTaskData(pageDirect.getPageId(), element);
                    goPage(pageDirect, parse);
                    return true;
                }
                LogUtils.i("DirectManager", " go= onElementDirect " + str);
                weakReference.get().onElementDirect(element);
                return true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void goElement(View view, ElementDirect elementDirect) {
        if (elementDirect != null) {
            if (elementDirect.getParentId() > 0) {
                View findViewById = view.findViewById(elementDirect.getParentId());
                if (findViewById != null) {
                    showElementItemView(findViewById.findViewById(elementDirect.getId()));
                    return;
                }
                return;
            }
            showElementItemView(view.findViewById(elementDirect.getId()));
        }
    }

    private void goPage(PageDirect pageDirect, Uri uri) {
        pageDirect.getAction().doAction(this.mContext);
    }

    private void showElementItemView(final View view) {
        StringBuilder sb = new StringBuilder();
        sb.append("showElementItemView  : ");
        sb.append(view == null ? " view is null " : " ");
        LogUtils.i("DirectManager", sb.toString());
        if (view != null) {
            view.post(new Runnable() { // from class: com.xiaopeng.iotlib.model.direct.-$$Lambda$DirectManager$NyJss-PpdZwyXsKtBzV-xPJRcrA
                @Override // java.lang.Runnable
                public final void run() {
                    VuiFloatingLayerManager.show(view, 1);
                }
            });
        }
    }

    private boolean isPageDirect(Uri uri) {
        if (uri != null) {
            return this.mScheme.equals(uri.getScheme()) && this.mAuth.equals(uri.getAuthority());
        }
        return false;
    }

    private boolean isElementDirect(Uri uri) {
        if (isPageDirect(uri)) {
            return !TextUtils.isEmpty(uri.getQueryParameter(this.mElementParameter));
        }
        return false;
    }
}
