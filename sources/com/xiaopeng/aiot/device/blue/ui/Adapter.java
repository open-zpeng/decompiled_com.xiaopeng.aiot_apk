package com.xiaopeng.aiot.device.blue.ui;

import com.xiaopeng.aiot.R;
import com.xiaopeng.aiot.device.blue.data.ScanDevice;
import com.xiaopeng.iotlib.base.BaseAdapter;
import com.xiaopeng.iotlib.provider.iot.device.Fridge;
import com.xiaopeng.xui.widget.XListSingle;
/* loaded from: classes.dex */
class Adapter extends BaseAdapter<ScanDevice> {
    private String mDeviceType;

    @Override // com.xiaopeng.iotlib.base.BaseAdapter
    protected int layoutId(int i) {
        return R.layout.ble_item;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDeviceType(String str) {
        this.mDeviceType = str;
    }

    @Override // com.xiaopeng.iotlib.base.BaseAdapter
    protected void convert(BaseAdapter.ViewHolder viewHolder, int i) {
        XListSingle xListSingle = (XListSingle) viewHolder.getView(R.id.list_device);
        ScanDevice item = getItem(i);
        if (Fridge.DEVICE_TYPE.equals(this.mDeviceType)) {
            xListSingle.setText(String.format("%s %s", item.getName(), item.getDeviceId()));
        } else {
            xListSingle.setText(String.format("%s    %s", item.getName(), item.getDeviceId()));
        }
    }
}
