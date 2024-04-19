package com.xiaopeng.iotlib.model.sound;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import com.xiaopeng.iotlib.Iot;
import com.xiaopeng.iotlib.utils.LogUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class SoundManager {
    public static final int MEDIA_PLAYER_CHANNEL = 1;
    public static final int SOUND_POOL_CHANNEL = 0;
    private static final String TAG = SoundManager.class.getSimpleName();
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;
    private final AudioManager mAudioManager;
    private final ConcurrentHashMap<String, Integer> mLoadedSound;
    private MediaPlayer mMediaPlayer;
    public SoundPool mSoundPlayer;

    private SoundManager() {
        this.mLoadedSound = new ConcurrentHashMap<>();
        this.mAudioManager = (AudioManager) Iot.getApp().getSystemService("audio");
        this.mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.iotlib.model.sound.-$$Lambda$SoundManager$xq8Nv_NFfSNfSJ495pN5yxG7VzY
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i) {
                SoundManager.this.lambda$new$0$SoundManager(i);
            }
        };
    }

    public /* synthetic */ void lambda$new$0$SoundManager(int i) {
        String str = TAG;
        LogUtils.d(str, "audio focus change...  focus change type: " + i);
        if (i == -2 || i == -1) {
            pureStopMedia();
        }
    }

    public static SoundManager get() {
        return SingletonHolder.INSTANCE;
    }

    private SoundPool createSoundPool() {
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(1);
        AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
        builder2.setLegacyStreamType(3);
        builder.setAudioAttributes(builder2.build());
        return builder.build();
    }

    public void playAssetSound(String str, int i) {
        if (i == 0) {
            playBySoundPool(str);
        } else if (i == 1) {
            playByMediaPlayer(str, false);
        } else {
            String str2 = TAG;
            LogUtils.w(str2, "invalid channel :" + i);
        }
    }

    private synchronized int playBySoundPool(final String str) {
        String str2 = TAG;
        LogUtils.d(str2, "play res by sound pool " + str);
        if (this.mSoundPlayer == null) {
            this.mSoundPlayer = createSoundPool();
        }
        Integer num = this.mLoadedSound.get(str);
        if (num != null) {
            String str3 = TAG;
            LogUtils.d(str3, "Already loaded start play. " + str);
            this.mSoundPlayer.play(num.intValue(), 1.0f, 1.0f, 0, 0, 1.0f);
            return num.intValue();
        }
        if (this.mSoundPlayer != null) {
            AssetFileDescriptor assetFileDescriptor = null;
            try {
                assetFileDescriptor = Iot.getApp().getAssets().openFd(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (assetFileDescriptor == null) {
                String str4 = TAG;
                LogUtils.e(str4, "play openFd is null " + str);
                return 0;
            }
            try {
                this.mSoundPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.xiaopeng.iotlib.model.sound.-$$Lambda$SoundManager$8CDnRPBRsrx8HV_RLTkDC0iWjsU
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                        SoundManager.this.lambda$playBySoundPool$1$SoundManager(str, soundPool, i, i2);
                    }
                });
                int load = this.mSoundPlayer.load(assetFileDescriptor, 1);
                this.mLoadedSound.put(str, Integer.valueOf(load));
                try {
                    assetFileDescriptor.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                return load;
            } catch (AndroidRuntimeException e3) {
                e3.printStackTrace();
                try {
                    assetFileDescriptor.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        } else {
            String str5 = TAG;
            LogUtils.w(str5, "Load " + str + " Failed,mSoundPlayer is null!");
        }
        return 0;
    }

    public /* synthetic */ void lambda$playBySoundPool$1$SoundManager(String str, SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            String str2 = TAG;
            LogUtils.d(str2, "Load success and start play. " + str);
            this.mLoadedSound.put(str, Integer.valueOf(i));
            this.mSoundPlayer.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
            return;
        }
        String str3 = TAG;
        LogUtils.w(str3, "Load failure! " + str);
        this.mLoadedSound.remove(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00b5 A[Catch: all -> 0x00d0, TryCatch #7 {, blocks: (B:3:0x0001, B:5:0x0007, B:6:0x001d, B:8:0x0034, B:13:0x0048, B:16:0x0063, B:18:0x0067, B:19:0x0080, B:21:0x008a, B:34:0x009f, B:36:0x00b5, B:37:0x00bb, B:24:0x008f, B:30:0x0099, B:11:0x0042, B:20:0x0085, B:29:0x0096), top: B:56:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bb A[Catch: all -> 0x00d0, TRY_LEAVE, TryCatch #7 {, blocks: (B:3:0x0001, B:5:0x0007, B:6:0x001d, B:8:0x0034, B:13:0x0048, B:16:0x0063, B:18:0x0067, B:19:0x0080, B:21:0x008a, B:34:0x009f, B:36:0x00b5, B:37:0x00bb, B:24:0x008f, B:30:0x0099, B:11:0x0042, B:20:0x0085, B:29:0x0096), top: B:56:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void playByMediaPlayer(final java.lang.String r5, boolean r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.requestAudioFocus()     // Catch: java.lang.Throwable -> Ld0
            if (r0 != 0) goto L1d
            java.lang.String r0 = com.xiaopeng.iotlib.model.sound.SoundManager.TAG     // Catch: java.lang.Throwable -> Ld0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld0
            r1.<init>()     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r2 = "request audio failure! "
            r1.append(r2)     // Catch: java.lang.Throwable -> Ld0
            r1.append(r5)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Ld0
            com.xiaopeng.iotlib.utils.LogUtils.w(r0, r1)     // Catch: java.lang.Throwable -> Ld0
        L1d:
            java.lang.String r0 = com.xiaopeng.iotlib.model.sound.SoundManager.TAG     // Catch: java.lang.Throwable -> Ld0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld0
            r1.<init>()     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r2 = "play res by media player "
            r1.append(r2)     // Catch: java.lang.Throwable -> Ld0
            r1.append(r5)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Ld0
            com.xiaopeng.iotlib.utils.LogUtils.d(r0, r1)     // Catch: java.lang.Throwable -> Ld0
            r0 = 0
            android.app.Application r1 = com.xiaopeng.iotlib.Iot.getApp()     // Catch: java.io.IOException -> L41 java.lang.Throwable -> Ld0
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch: java.io.IOException -> L41 java.lang.Throwable -> Ld0
            android.content.res.AssetFileDescriptor r1 = r1.openFd(r5)     // Catch: java.io.IOException -> L41 java.lang.Throwable -> Ld0
            goto L46
        L41:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> Ld0
            r1 = r0
        L46:
            if (r1 != 0) goto L63
            java.lang.String r6 = com.xiaopeng.iotlib.model.sound.SoundManager.TAG     // Catch: java.lang.Throwable -> Ld0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld0
            r0.<init>()     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r1 = "play openFd is null "
            r0.append(r1)     // Catch: java.lang.Throwable -> Ld0
            r0.append(r5)     // Catch: java.lang.Throwable -> Ld0
            java.lang.String r5 = r0.toString()     // Catch: java.lang.Throwable -> Ld0
            com.xiaopeng.iotlib.utils.LogUtils.e(r6, r5)     // Catch: java.lang.Throwable -> Ld0
            r4.abandonAudioFocus()     // Catch: java.lang.Throwable -> Ld0
            monitor-exit(r4)
            return
        L63:
            android.media.MediaPlayer r2 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            if (r2 != 0) goto L80
            android.media.MediaPlayer r2 = new android.media.MediaPlayer     // Catch: java.lang.Throwable -> Ld0
            r2.<init>()     // Catch: java.lang.Throwable -> Ld0
            r4.mMediaPlayer = r2     // Catch: java.lang.Throwable -> Ld0
            android.media.AudioAttributes$Builder r2 = new android.media.AudioAttributes$Builder     // Catch: java.lang.Throwable -> Ld0
            r2.<init>()     // Catch: java.lang.Throwable -> Ld0
            r3 = 1
            r2.setLegacyStreamType(r3)     // Catch: java.lang.Throwable -> Ld0
            android.media.MediaPlayer r3 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            android.media.AudioAttributes r2 = r2.build()     // Catch: java.lang.Throwable -> Ld0
            r3.setAudioAttributes(r2)     // Catch: java.lang.Throwable -> Ld0
        L80:
            android.media.MediaPlayer r2 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            r2.reset()     // Catch: java.lang.Throwable -> Ld0
            android.media.MediaPlayer r2 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> L93 java.io.IOException -> L95
            r2.setDataSource(r1)     // Catch: java.lang.Throwable -> L93 java.io.IOException -> L95
            r1.close()     // Catch: java.io.IOException -> L8e java.lang.Throwable -> Ld0
            goto L9f
        L8e:
            r1 = move-exception
        L8f:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> Ld0
            goto L9f
        L93:
            r5 = move-exception
            goto Lc7
        L95:
            r2 = move-exception
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L93
            r1.close()     // Catch: java.io.IOException -> L9d java.lang.Throwable -> Ld0
            goto L9f
        L9d:
            r1 = move-exception
            goto L8f
        L9f:
            android.media.MediaPlayer r1 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            r1.setLooping(r6)     // Catch: java.lang.Throwable -> Ld0
            android.media.MediaPlayer r1 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            r1.prepareAsync()     // Catch: java.lang.Throwable -> Ld0
            android.media.MediaPlayer r1 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            com.xiaopeng.iotlib.model.sound.-$$Lambda$SoundManager$EGbAJJiNfMxfztD-76oeEfl-dSE r2 = new com.xiaopeng.iotlib.model.sound.-$$Lambda$SoundManager$EGbAJJiNfMxfztD-76oeEfl-dSE     // Catch: java.lang.Throwable -> Ld0
            r2.<init>()     // Catch: java.lang.Throwable -> Ld0
            r1.setOnPreparedListener(r2)     // Catch: java.lang.Throwable -> Ld0
            if (r6 == 0) goto Lbb
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            r5.setOnCompletionListener(r0)     // Catch: java.lang.Throwable -> Ld0
            goto Lc5
        Lbb:
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.lang.Throwable -> Ld0
            com.xiaopeng.iotlib.model.sound.-$$Lambda$SoundManager$06IY_VUamJ7f8PSGbyDJTdssMS8 r6 = new com.xiaopeng.iotlib.model.sound.-$$Lambda$SoundManager$06IY_VUamJ7f8PSGbyDJTdssMS8     // Catch: java.lang.Throwable -> Ld0
            r6.<init>()     // Catch: java.lang.Throwable -> Ld0
            r5.setOnCompletionListener(r6)     // Catch: java.lang.Throwable -> Ld0
        Lc5:
            monitor-exit(r4)
            return
        Lc7:
            r1.close()     // Catch: java.io.IOException -> Lcb java.lang.Throwable -> Ld0
            goto Lcf
        Lcb:
            r6 = move-exception
            r6.printStackTrace()     // Catch: java.lang.Throwable -> Ld0
        Lcf:
            throw r5     // Catch: java.lang.Throwable -> Ld0
        Ld0:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.iotlib.model.sound.SoundManager.playByMediaPlayer(java.lang.String, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$playByMediaPlayer$2(String str, MediaPlayer mediaPlayer) {
        String str2 = TAG;
        LogUtils.d(str2, "prepare success and play. " + str);
        mediaPlayer.start();
    }

    public /* synthetic */ void lambda$playByMediaPlayer$3$SoundManager(MediaPlayer mediaPlayer) {
        abandonAudioFocus();
    }

    private boolean requestAudioFocus() {
        return this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 3) != 0;
    }

    private void abandonAudioFocus() {
        this.mAudioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
    }

    public synchronized void preLoadAssetSource(ArrayList<String> arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            final String next = it.next();
            if (!TextUtils.isEmpty(next)) {
                if (this.mLoadedSound.get(next) != null) {
                    String str = TAG;
                    LogUtils.d(str, "pre load... " + next + " already load.");
                } else {
                    AssetFileDescriptor assetFileDescriptor = null;
                    try {
                        assetFileDescriptor = Iot.getApp().getAssets().openFd(next);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (this.mSoundPlayer == null) {
                        this.mSoundPlayer = createSoundPool();
                    }
                    if (assetFileDescriptor == null) {
                        return;
                    }
                    try {
                        this.mLoadedSound.put(next, Integer.valueOf(this.mSoundPlayer.load(assetFileDescriptor, 1)));
                        String str2 = TAG;
                        LogUtils.i(str2, "pre load..." + next + "load success. add to map.");
                        this.mSoundPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.xiaopeng.iotlib.model.sound.-$$Lambda$SoundManager$J3rLRpBF_pWv2H8qCvvQAMbiSvk
                            @Override // android.media.SoundPool.OnLoadCompleteListener
                            public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                                SoundManager.this.lambda$preLoadAssetSource$4$SoundManager(next, soundPool, i, i2);
                            }
                        });
                        try {
                            assetFileDescriptor.close();
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        try {
                            assetFileDescriptor.close();
                        } catch (Exception e4) {
                            e = e4;
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public /* synthetic */ void lambda$preLoadAssetSource$4$SoundManager(String str, SoundPool soundPool, int i, int i2) {
        if (i2 != 0) {
            this.mLoadedSound.remove(str);
            String str2 = TAG;
            LogUtils.w(str2, "pre load..." + str + "load failure. remove from map.");
        }
    }

    private void pureStopMedia() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public synchronized void stopMedia() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
        }
        abandonAudioFocus();
    }

    public synchronized void release() {
        if (this.mSoundPlayer != null) {
            this.mSoundPlayer.release();
            this.mLoadedSound.clear();
            this.mSoundPlayer = null;
        }
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final SoundManager INSTANCE = new SoundManager();

        private SingletonHolder() {
        }
    }
}
