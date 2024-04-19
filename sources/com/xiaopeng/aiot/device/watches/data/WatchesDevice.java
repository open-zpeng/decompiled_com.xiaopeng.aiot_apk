package com.xiaopeng.aiot.device.watches.data;

import com.xiaopeng.iotlib.data.DeviceInfo;
/* loaded from: classes.dex */
public class WatchesDevice extends DeviceInfo {
    public static final int STATUS_BIND = 1;
    public static final int STATUS_LOGOUT = -101;
    public static final int STATUS_QUERY_FAIL = -100;
    public static final int STATUS_UNBIND = 0;
    public static final int STATUS_UNINIT = -1;
    private double latitude;
    private String locationDesc;
    private String locationTime;
    private double longitude;
    private String name;
    private int networkState;
    private String phone;
    private String photo;
    private int power;
    private String powerTime;
    private int state;

    public static WatchesDevice reset(WatchesDevice watchesDevice) {
        if (watchesDevice == null) {
            return null;
        }
        watchesDevice.setState(-1);
        return watchesDevice;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String str) {
        this.photo = str;
    }

    public String getLocationDesc() {
        return this.locationDesc;
    }

    public void setLocationDesc(String str) {
        this.locationDesc = str;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String getName() {
        return this.name;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public void setName(String str) {
        this.name = str;
    }

    public int getPower() {
        return this.power;
    }

    public void setPower(int i) {
        this.power = i;
    }

    public int getNetworkState() {
        return this.networkState;
    }

    public void setNetworkState(int i) {
        this.networkState = i;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double d) {
        this.longitude = d;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double d) {
        this.latitude = d;
    }

    public String getLocationTime() {
        return this.locationTime;
    }

    public void setLocationTime(String str) {
        this.locationTime = str;
    }

    public String getPowerTime() {
        return this.powerTime;
    }

    public void setPowerTime(String str) {
        this.powerTime = str;
    }

    @Override // com.xiaopeng.iotlib.data.DeviceInfo
    public String toString() {
        return "WatchesDevice{state=" + this.state + ", phone='" + this.phone + "', name='" + this.name + "', power=" + this.power + ", powerTime=" + this.powerTime + ", networkState=" + this.networkState + ", longitude=" + this.longitude + ", latitude=" + this.latitude + ", locationTime=" + this.locationTime + ", locationDesc='" + this.locationDesc + "'}";
    }
}
