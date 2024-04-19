package com.xiaopeng.aiot.model.page;

import android.net.Uri;
import androidx.lifecycle.Lifecycle;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.airbed.ui.AirBedPage;
import com.xiaopeng.aiot.device.blue.ui.BluePage;
import com.xiaopeng.aiot.device.childseat.ui.ChildSeatPage;
import com.xiaopeng.aiot.device.fragrance.ui.FragrancePage;
import com.xiaopeng.aiot.device.fragrance.ui2.FragrancePage2;
import com.xiaopeng.aiot.device.fridge.ui.FridgePage;
import com.xiaopeng.aiot.device.fridge.ui2.FridgePage2;
import com.xiaopeng.aiot.device.sample.SamplePage;
import com.xiaopeng.aiot.device.watches.ui.WatchesPage;
import com.xiaopeng.iotlib.base.BasePage;
import com.xiaopeng.iotlib.provider.iot.device.AirBed;
import com.xiaopeng.iotlib.provider.iot.device.ChildSafetySeat;
import com.xiaopeng.iotlib.provider.iot.device.Fridge;
/* loaded from: classes.dex */
public class PageConfigFactory {
    public static final PageConfigDetail SAMPLE = new PageConfigDetail() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.1
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "sample";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.sample_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new SamplePage(lifecycle);
        }
    };
    public static final PageConfigDetail FRAGRANCE = new PageConfigDetail() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.2
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "fragrance";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.fragrance_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new FragrancePage(lifecycle);
        }
    };
    public static final PageConfigDetail2 FRAGRANCE2 = new PageConfigDetail2() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.3
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "fragrance";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.fragrance2_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitleColor() {
            return R.color.panel_fragrance_title_color;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new FragrancePage2(lifecycle);
        }
    };
    public static final PageConfigDetail FRIDGE = new PageConfigDetail() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.4
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "fridge";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.fridge_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new FridgePage(lifecycle);
        }
    };
    public static final PageConfigDetail2 FRIDGE2 = new PageConfigDetail2() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.5
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "fridge";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.fridge2_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitleColor() {
            return R.color.panel_fridge_title_color;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new FridgePage2(lifecycle);
        }
    };
    public static final PageConfigCommon BLUE_FRIDGE = new PageConfigBlue() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.6
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "fridge";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.blue_fridge_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new BluePage(lifecycle, Fridge.DEVICE_TYPE);
        }
    };
    public static final PageConfigCommon BLUE_CHILDSEAT = new PageConfigBlue() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.7
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "childseat";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.blue_childseat_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new BluePage(lifecycle, ChildSafetySeat.DEVICE_TYPE);
        }
    };
    public static final PageConfigCommon BLUE_AIR_BED = new PageConfigBlue() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.8
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "airbed";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.blue_airbed_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new BluePage(lifecycle, AirBed.DEVICE_TYPE);
        }
    };
    public static final PageConfigCommon CHILD_SEAT = new PageConfigCommon() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.9
        public static final String PATH = "/device/childseat";

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.childseat_title;
        }

        @Override // com.xiaopeng.aiot.model.page.PageConfigCommon
        protected String path() {
            return PATH;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public boolean supportDirect() {
            return false;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public boolean supportVui() {
            return false;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public Uri createUri() {
            return PageConfigManager.createScheme().path(PATH).build();
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new ChildSeatPage(lifecycle);
        }
    };
    public static final PageConfigCommon AIR_BED = new PageConfigCommon() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.10
        public static final String PATH = "/device/airbed";

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.air_bed_title;
        }

        @Override // com.xiaopeng.aiot.model.page.PageConfigCommon
        protected String path() {
            return PATH;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public boolean supportDirect() {
            return false;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public boolean supportVui() {
            return false;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public Uri createUri() {
            return PageConfigManager.createScheme().path(PATH).build();
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new AirBedPage(lifecycle);
        }
    };
    public static final PageConfigCommon WATCHES = new PageConfigDetail() { // from class: com.xiaopeng.aiot.model.page.PageConfigFactory.11
        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public String pageKey() {
            return "watches";
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public int pageTitle() {
            return R.string.watch_title;
        }

        @Override // com.xiaopeng.iotlib.base.BasePageConfig
        public BasePage createPage(Lifecycle lifecycle) {
            return new WatchesPage(lifecycle);
        }
    };
}
