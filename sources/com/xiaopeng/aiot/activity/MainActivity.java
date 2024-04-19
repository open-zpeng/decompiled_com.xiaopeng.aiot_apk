package com.xiaopeng.aiot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.fragrance.ui2.TypeGreatMasterDialog;
import com.xiaopeng.aiot.device.fragrance.ui2.TypeIntroduceDialog;
import com.xiaopeng.aiot.model.common.AiCardUtils;
import com.xiaopeng.aiot.model.page.PageConfigFactory;
import com.xiaopeng.iotlib.base.BaseActivity;
import com.xiaopeng.iotlib.data.PageId;
import com.xiaopeng.iotlib.model.sound.AssetsSoundSource;
import com.xiaopeng.iotlib.model.sound.SoundManager;
import com.xiaopeng.iotlib.provider.power.PowerCenterManager;
import com.xiaopeng.iotlib.provider.router.RouterHelper;
import com.xiaopeng.xui.app.XToast;
import com.xiaopeng.xui.widget.XSwitch;
/* loaded from: classes.dex */
public class MainActivity extends BaseActivity {
    public static final boolean MANY_START = false;
    private int count;
    private int mSoundChannel = 0;
    private Handler mHandler = new Handler();

    @Override // com.xiaopeng.iotlib.base.BaseActivity
    protected View getRootView() {
        return null;
    }

    @Override // com.xiaopeng.iotlib.base.BaseActivity
    protected PageId onPageIdCreate() {
        return null;
    }

    @Override // com.xiaopeng.iotlib.base.BaseActivity
    protected void onCreateInit(Bundle bundle) {
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$-Boq6CfDKuJzRF_AkMFImHgxLgE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$0$MainActivity(view);
            }
        });
        findViewById(R.id.btn01).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$hDKBBJIvkGa6TnaALpNxXt4k6oI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$1$MainActivity(view);
            }
        });
        findViewById(R.id.btn011).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$aQdvQRaPnZf1uO1ZmnK5ntX5JFo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$2$MainActivity(view);
            }
        });
        findViewById(R.id.btn02).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$dd7brHy3dQxnDq_8l5PKvKsxpMA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$3$MainActivity(view);
            }
        });
        findViewById(R.id.btn021).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$IBwThOHwHchgglZAJfdK3_MGJLc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$4$MainActivity(view);
            }
        });
        findViewById(R.id.btn022).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$2iFWrPwivCL6xj5tJjKHDwHjaYs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$5$MainActivity(view);
            }
        });
        findViewById(R.id.btn030).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$H69j_Umx7V5LLRWAcE0bMiCcYnQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$6$MainActivity(view);
            }
        });
        findViewById(R.id.btn031).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$PF2cjhd1slUviXjJMn2AJdOha-0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$7$MainActivity(view);
            }
        });
        findViewById(R.id.btn040).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$qM9ayyCmUqn-poLKN59aqjlKeE8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$8$MainActivity(view);
            }
        });
        findViewById(R.id.btn050).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$yYDAj0SnzRdkb8WZtJOv92dYxCs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$9$MainActivity(view);
            }
        });
        findViewById(R.id.btn051).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$Xr_zfIkXrxaZX0pGBQcLiUowODI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$10$MainActivity(view);
            }
        });
        findViewById(R.id.btn03).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$4hSwKZEhVkJBh010wX1KcRmkeD8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$11$MainActivity(view);
            }
        });
        findViewById(R.id.btn04).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$sFmM5-W7RJZ4ZQh2BEdxg0_As_c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AiCardUtils.sendCoCard();
            }
        });
        findViewById(R.id.btn06).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$F0VIPegBVObBtRQKys-9BvhgUiQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AiCardUtils.sendFridgeDoorCard();
            }
        });
        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$oLD7i7rxYxYVGma4TKxqhb8NKtw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.lambda$onCreateInit$14(view);
            }
        });
        findViewById(R.id.btn11).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$Qhk4sLofYn8VZzCucqNuv-WIcm0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RouterHelper.setFridgeSwitchStatus(true);
            }
        });
        findViewById(R.id.btn12).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$5AY4k4I1SCbmA1XB7T6T5ijb-aM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.lambda$onCreateInit$16(view);
            }
        });
        findViewById(R.id.btn13).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$LgSSx6n3XN_3ZqZbcK3tcy14vjQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$17$MainActivity(view);
            }
        });
        findViewById(R.id.btn50).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$Yhz4WkzW0CG73qgnVgoC6b7Bjlo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$18$MainActivity(view);
            }
        });
        findViewById(R.id.btn51).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$OHNDx43FRMiwm2bC6OC0QySZdyo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$19$MainActivity(view);
            }
        });
        findViewById(R.id.btn52).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$aW4HZzl7I_Z276vxXVDuljDNKjk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$20$MainActivity(view);
            }
        });
        findViewById(R.id.btn14).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$BSFgDjIZd2-2Lt9FxerXlN45EdQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$21$MainActivity(view);
            }
        });
        findViewById(R.id.btn15).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$xh9P1Fz1ccHQ4UtE95cPsy0cRtM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$22$MainActivity(view);
            }
        });
        findViewById(R.id.btn16).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$f2jnR5VwxgiErNrWocZhwvoNeKQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$23$MainActivity(view);
            }
        });
        findViewById(R.id.btn17).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$SLLKM3vX06LfhK5nyStkwyTToU8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$24$MainActivity(view);
            }
        });
        findViewById(R.id.btn18).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$4cfcYrpMOtr_xD_MGRBL0R38QNM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$25$MainActivity(view);
            }
        });
        findViewById(R.id.btn19).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$E-1uEjfZnyqi7wU2DYm3ugjI700
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$26$MainActivity(view);
            }
        });
        findViewById(R.id.btn20).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$oQ9Nee7WthIYUD14mttZ0k5lI3k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$27$MainActivity(view);
            }
        });
        findViewById(R.id.btn21).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$Lv4IU6VUGKTciKJr67w7LUJi_bE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$28$MainActivity(view);
            }
        });
        findViewById(R.id.btn22).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$wUdMuVE_1DaCoh-n1l5mrkb1bl8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$29$MainActivity(view);
            }
        });
        findViewById(R.id.btn23).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$9GlGIfRPPDP4ZvoY5zwtoJlv8Ik
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$30$MainActivity(view);
            }
        });
        findViewById(R.id.btn24).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$p3D7EQImLhfz0Bn83BAodlG-sY0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$31$MainActivity(view);
            }
        });
        findViewById(R.id.btn25).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$_SNO-gg9GAZzGs5MW3ve9yYScTg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreateInit$32$MainActivity(view);
            }
        });
        ((XSwitch) findViewById(R.id.sw_sound)).setChecked(true);
        ((XSwitch) findViewById(R.id.sw_sound)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.aiot.activity.-$$Lambda$MainActivity$5wQMxzSrBUlOFlPaE3NuefehDnU
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                MainActivity.this.lambda$onCreateInit$33$MainActivity(compoundButton, z);
            }
        });
    }

    public /* synthetic */ void lambda$onCreateInit$0$MainActivity(View view) {
        dismissActivity();
    }

    public /* synthetic */ void lambda$onCreateInit$1$MainActivity(View view) {
        PageConfigFactory.FRAGRANCE.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$2$MainActivity(View view) {
        this.count = 0;
        startFragrance2();
    }

    public /* synthetic */ void lambda$onCreateInit$3$MainActivity(View view) {
        PageConfigFactory.FRIDGE.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$4$MainActivity(View view) {
        PageConfigFactory.FRIDGE2.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$5$MainActivity(View view) {
        PageConfigFactory.BLUE_FRIDGE.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$6$MainActivity(View view) {
        PageConfigFactory.CHILD_SEAT.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$7$MainActivity(View view) {
        PageConfigFactory.BLUE_CHILDSEAT.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$8$MainActivity(View view) {
        PageConfigFactory.WATCHES.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$9$MainActivity(View view) {
        PageConfigFactory.BLUE_AIR_BED.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$10$MainActivity(View view) {
        PageConfigFactory.AIR_BED.go(this);
    }

    public /* synthetic */ void lambda$onCreateInit$11$MainActivity(View view) {
        PageConfigFactory.SAMPLE.go(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onCreateInit$14(View view) {
        int trunkPowerSwitchStatus = RouterHelper.getTrunkPowerSwitchStatus();
        boolean powerSwitch = PowerCenterManager.get().getPowerSwitch();
        XToast.show("getTrunkPowerSwitchStatus: " + trunkPowerSwitchStatus + " , " + powerSwitch);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onCreateInit$16(View view) {
        String trunkPowerDelayOffTime = RouterHelper.getTrunkPowerDelayOffTime();
        XToast.show("getTrunkPowerDelayOffTime: " + trunkPowerDelayOffTime);
    }

    public /* synthetic */ void lambda$onCreateInit$17$MainActivity(View view) {
        Intent intent = new Intent("android.intent.action.showTrunkPowerDelayOffTimeDialog");
        intent.addFlags(16777216);
        sendBroadcast(intent);
    }

    public /* synthetic */ void lambda$onCreateInit$18$MainActivity(View view) {
        new TypeIntroduceDialog(this).show(101);
    }

    public /* synthetic */ void lambda$onCreateInit$19$MainActivity(View view) {
        new TypeGreatMasterDialog(this).show(105);
    }

    public /* synthetic */ void lambda$onCreateInit$20$MainActivity(View view) {
        new TypeGreatMasterDialog(this).show(106);
    }

    public /* synthetic */ void lambda$onCreateInit$21$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_DIANLAN, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$22$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_KUNLUN, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$23$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_QILI, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$24$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_QIANXING, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$25$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_SHIGUANG, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$26$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_XINGJI, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$27$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRAGRANCE_XINGYUN, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$28$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_POWER_OFF, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$29$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_POWER_ON, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$30$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_TEMPERATURE_SIX, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$31$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_TEMPERATURE_SUBZERO_SIX, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$32$MainActivity(View view) {
        SoundManager.get().playAssetSound(AssetsSoundSource.FRIDGE_TEMPERATURE_ZERO, this.mSoundChannel);
    }

    public /* synthetic */ void lambda$onCreateInit$33$MainActivity(CompoundButton compoundButton, boolean z) {
        SoundManager.get().release();
        if (z) {
            this.mSoundChannel = 0;
        } else {
            this.mSoundChannel = 1;
        }
    }

    void startFragrance2() {
        int i = this.count;
        if (i >= 100) {
            return;
        }
        this.count = i + 1;
        PageConfigFactory.FRAGRANCE2.go(this);
    }
}
