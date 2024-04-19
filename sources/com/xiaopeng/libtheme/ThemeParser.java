package com.xiaopeng.libtheme;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.xiaopeng.libtheme.ThemeManager;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
/* loaded from: classes.dex */
public class ThemeParser {
    private static final boolean DEBUG = false;
    private static final String NODE = "view";
    private static final String TAG = "ThemeXmlParser";
    private static final String _ATTR = "attr";
    private static final String _ID = "id";
    private static final String _ROOT = "root";
    private static final String _VALUE = "value";

    public static synchronized List<ThemeView> parseXml(Context context, String str) {
        synchronized (ThemeParser.class) {
            if (context != null) {
                try {
                    if (!TextUtils.isEmpty(str)) {
                        return parseXml(context, context.getAssets().open(str));
                    }
                } catch (Exception unused) {
                }
            }
            return null;
        }
    }

    public static synchronized List<ThemeView> parseXml(Context context, InputStream inputStream) {
        ArrayList arrayList;
        ThemeView themeView;
        synchronized (ThemeParser.class) {
            arrayList = new ArrayList();
            if (inputStream != null) {
                try {
                    XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                    newPullParser.setInput(inputStream, "utf-8");
                    ThemeView themeView2 = null;
                    for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                        String name = newPullParser.getName();
                        if (!TextUtils.isEmpty(name)) {
                            if (eventType != 2) {
                                if (eventType == 3) {
                                    if (themeView2 != null) {
                                        arrayList.add(themeView2);
                                    }
                                    themeView2 = null;
                                }
                            } else if (NODE.equals(name.toLowerCase())) {
                                String attributeValue = newPullParser.getAttributeValue(null, "id");
                                String attributeValue2 = newPullParser.getAttributeValue(null, _ROOT);
                                String attributeValue3 = newPullParser.getAttributeValue(null, _ATTR);
                                String attributeValue4 = newPullParser.getAttributeValue(null, "value");
                                if (TextUtils.isEmpty(attributeValue) || TextUtils.isEmpty(attributeValue3) || TextUtils.isEmpty(attributeValue4)) {
                                    themeView = null;
                                } else {
                                    ThemeView themeView3 = new ThemeView();
                                    themeView3.xmlId = attributeValue;
                                    themeView3.xmlRoot = attributeValue2;
                                    themeView3.xmlAttr = attributeValue3;
                                    themeView3.xmlValue = attributeValue4;
                                    themeView = resolveThemeView(context, themeView3);
                                }
                                themeView2 = themeView;
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        }
        return arrayList;
    }

    private static ThemeView resolveThemeView(Context context, ThemeView themeView) {
        if (context == null || themeView == null) {
            return null;
        }
        try {
            Resources resources = context.getResources();
            String packageName = context.getPackageName();
            if (!TextUtils.isEmpty(themeView.xmlId)) {
                themeView.resId = resources.getIdentifier(themeView.xmlId, "id", packageName);
            }
            if (!TextUtils.isEmpty(themeView.xmlAttr)) {
                themeView.resAttr = themeView.xmlAttr;
            }
            if (!TextUtils.isEmpty(themeView.xmlRoot)) {
                themeView.resRoot = resources.getIdentifier(themeView.xmlRoot, "id", packageName);
            }
            if (!TextUtils.isEmpty(themeView.xmlValue) && themeView.xmlValue.startsWith("@") && themeView.xmlValue.contains("/")) {
                String substring = themeView.xmlValue.substring(1, themeView.xmlValue.indexOf("/"));
                int identifier = resources.getIdentifier(themeView.xmlValue.substring(themeView.xmlValue.indexOf("/") + 1), substring, packageName);
                themeView.resType = ThemeManager.ResourceType.getType(substring);
                themeView.resValue = Integer.valueOf(identifier);
            }
            return themeView;
        } catch (Exception unused) {
            return null;
        }
    }
}
