package com.xiaopeng.aiot.device.airbed.api;

import com.xiaopeng.aiot.device.airbed.data.AirBedDevice;
import com.xiaopeng.iotlib.model.api.IotApi;
import com.xiaopeng.iotlib.model.sp.IAirbedSave;
import com.xiaopeng.iotlib.model.sp.SaveManager;
import com.xiaopeng.iotlib.provider.blue.BluetoothCallBack;
import com.xiaopeng.iotlib.provider.blue.BluetoothHelper;
import com.xiaopeng.iotlib.provider.iot.device.AirBed;
import com.xiaopeng.iotlib.provider.iot.device.Base;
import com.xiaopeng.iotlib.utils.LogUtils;
import com.xiaopeng.iotlib.utils.ThreadUtils;
/* loaded from: classes.dex */
public class AirBedApiXuiImpl extends IotApi<AirBedDevice> implements AirBedApi, BluetoothCallBack {
    private static final int HARD_VALUE = 53;
    private static final int SOFT_VALUE = 33;
    private static final int STANDARD_VALUE = 43;
    private static final String TAG = "AirBedApi";
    private int mBleState;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.DeviceApi
    public String logTag() {
        return "AirBedApi";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AirBedApiXuiImpl() {
        super(AirBed.DEVICE_TYPE, new String[]{Base.PROP_CONNECT_STATE, AirBed.PROP_BED_HARDWARE_STATUS, AirBed.PROP_BED_PUMP_STATUS, AirBed.PROP_BED_PRESSURE, AirBed.PROP_BED_WORK_CURRENT, AirBed.PROP_BED_HARDNESS_LEVEL}, new String[]{AirBed.PROP_BED_PUMP, AirBed.PROP_BED_UN_PUMP, AirBed.PROP_STOP_COMMAND});
        this.mBleState = -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi
    public AirBedDevice createData() {
        String logTag = logTag();
        LogUtils.i(logTag, "======createData " + rawDataInfo() + ", mBleState" + this.mBleState);
        AirBedDevice airBedDevice = new AirBedDevice();
        if (getIotDevice() == null) {
            airBedDevice.setStatus(-1);
            return airBedDevice;
        }
        if (this.mBleState == -1) {
            this.mBleState = BluetoothHelper.get().getState();
        }
        int i = this.mBleState;
        if (i == 10 || i == 13) {
            airBedDevice.setStatus(1);
            return airBedDevice;
        } else if (i == 11) {
            airBedDevice.setStatus(2);
            return airBedDevice;
        } else if (!"100".equals(getRawValue(Base.PROP_CONNECT_STATE))) {
            airBedDevice.setStatus(3);
            return airBedDevice;
        } else {
            airBedDevice.setStatus(4);
            String rawValue = getRawValue(AirBed.PROP_BED_HARDWARE_STATUS);
            airBedDevice.setBedBumpStatus(getRawValue(AirBed.PROP_BED_PUMP_STATUS));
            airBedDevice.setBedHardwareStatus(rawValue);
            int bedPressure = SaveManager.get().getAirbedSave() != null ? SaveManager.get().getAirbedSave().getBedPressure() : 1;
            String logTag2 = logTag();
            LogUtils.d(logTag2, "the pressureLevel is: " + bedPressure);
            airBedDevice.setBedHardnessLevel(Integer.toString(bedPressure));
            return airBedDevice;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.xiaopeng.iotlib.model.api.IotApi, com.xiaopeng.iotlib.model.api.IDeviceApi
    public AirBedDevice loadData() {
        this.mBleState = BluetoothHelper.get().getState();
        String logTag = logTag();
        LogUtils.d(logTag, " loadData  mBleState " + this.mBleState);
        return (AirBedDevice) super.loadData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.IotApi, com.xiaopeng.iotlib.model.api.DeviceApi
    public void startListenerSignal() {
        super.startListenerSignal();
        BluetoothHelper.get().addCallBack(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.iotlib.model.api.IotApi, com.xiaopeng.iotlib.model.api.DeviceApi
    public void stopListenerSignal() {
        super.stopListenerSignal();
        BluetoothHelper.get().removeCallBack(this);
        this.mBleState = -1;
    }

    @Override // com.xiaopeng.iotlib.provider.blue.BluetoothCallBack
    public void onBleStateChanged(int i) {
        String logTag = logTag();
        LogUtils.i(logTag, "onBleStateChanged: " + i);
        this.mBleState = i;
        notifyChanged(createData());
    }

    @Override // com.xiaopeng.aiot.device.airbed.api.AirBedApi
    public void inflateBed() {
        LogUtils.i(logTag(), " inflateBed... ");
        inflate();
    }

    @Override // com.xiaopeng.aiot.device.airbed.api.AirBedApi
    public void deflateBed() {
        LogUtils.i(logTag(), " deflateBed... ");
        setDeviceProperties(AirBed.PROP_BED_UN_PUMP, AirBed.BED_PUMP_DEFAULT_VALUE);
    }

    @Override // com.xiaopeng.aiot.device.airbed.api.AirBedApi
    public void interruptBedBump() {
        LogUtils.i(logTag(), " interruptBedBump... ");
        setDeviceProperties(AirBed.PROP_STOP_COMMAND, AirBed.BED_PUMP_DEFAULT_VALUE);
    }

    @Override // com.xiaopeng.aiot.device.airbed.api.AirBedApi
    public void hardnessUp(String str) {
        String logTag = logTag();
        LogUtils.i(logTag, " hardnessUp " + str);
        setDeviceProperties(AirBed.PROP_BED_HARDNESS_UP, str);
    }

    @Override // com.xiaopeng.aiot.device.airbed.api.AirBedApi
    public void hardnessDown(String str) {
        String logTag = logTag();
        LogUtils.i(logTag, " hardnessDown " + str);
        setDeviceProperties(AirBed.PROP_BED_HARDNESS_UP, str);
    }

    @Override // com.xiaopeng.aiot.device.airbed.api.AirBedApi
    public void hardnessSet(String str) {
        String logTag = logTag();
        LogUtils.i(logTag, " hardnessSet " + str);
        setDeviceProperties(AirBed.PROP_BED_HARDNESS_SET, str);
    }

    @Override // com.xiaopeng.aiot.device.airbed.api.AirBedApi
    public void setHardnessLevel(int i) {
        String logTag = logTag();
        LogUtils.i(logTag, " setHardnessLevel " + i);
        setHardnessLevel(i, true);
    }

    public void inflate() {
        IAirbedSave airbedSave = SaveManager.get().getAirbedSave();
        if (airbedSave == null) {
            setDeviceProperties(AirBed.PROP_BED_PUMP, AirBed.BED_PUMP_DEFAULT_VALUE);
            LogUtils.d("AirBedApi", "No memory airbed pressure,inflate default value.");
            return;
        }
        setHardnessLevel(airbedSave.getBedPressure(), true);
        LogUtils.d("AirBedApi", "set airbedSave value " + airbedSave.getBedPressure());
    }

    public void setHardnessLevel(final int i, boolean z) {
        int i2;
        String str;
        boolean deviceProperties;
        LogUtils.d("AirBedApi", "setHardnessLevel" + i);
        if (i == 0) {
            i2 = 33;
            str = AirBed.BED_PUMP_SOFT_VALUE;
        } else if (i == 1) {
            i2 = 43;
            str = AirBed.BED_PUMP_STANDARD_VALUE;
        } else if (i != 2) {
            str = null;
            i2 = 0;
        } else {
            i2 = 53;
            str = AirBed.BED_PUMP_HARD_VALUE;
        }
        if (str != null) {
            String rawValue = getRawValue(AirBed.PROP_BED_PRESSURE);
            LogUtils.d("AirBedApi", "current pressure is: " + rawValue + " expected pressure is " + i2);
            int parseInt = Integer.parseInt(rawValue) - i2;
            if (parseInt <= 0) {
                deviceProperties = setDeviceProperties(AirBed.PROP_BED_PUMP, str);
            } else if (parseInt <= 10) {
                deviceProperties = setDeviceProperties(AirBed.PROP_BED_HARDNESS_DOWN, AirBed.BED_UN_PUMP_ONE);
            } else {
                deviceProperties = setDeviceProperties(AirBed.PROP_BED_HARDNESS_DOWN, AirBed.BED_UN_PUMP_TWO);
            }
            if (deviceProperties) {
                if (z) {
                    ThreadUtils.SINGLE.post(new Runnable() { // from class: com.xiaopeng.aiot.device.airbed.api.-$$Lambda$AirBedApiXuiImpl$liCT_j0P_Wzn427f-mprlKSbmLs
                        @Override // java.lang.Runnable
                        public final void run() {
                            AirBedApiXuiImpl.lambda$setHardnessLevel$0(i);
                        }
                    });
                    return;
                }
                return;
            }
            LogUtils.e("AirBedApi", "setHardnessLevel error" + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$setHardnessLevel$0(int i) {
        IAirbedSave airbedSave = SaveManager.get().getAirbedSave();
        if (airbedSave != null) {
            airbedSave.saveBedPressure(i);
        }
    }
}
