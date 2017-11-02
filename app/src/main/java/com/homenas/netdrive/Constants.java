package com.homenas.netdrive;

import android.Manifest;
import android.os.Environment;

/**
 * Created by engss on 24/10/2017.
 */

public class Constants {

    public static final String KEY_LAYOUT_MANAGER = "layoutManager";

    public enum LayoutManagerType {GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER}

    public static final int SPAN_COUNT = 2;

    public static final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static final int PERMISSIONS_REQUEST_CODE = 0;

    public static final String mStartPath = Environment.getExternalStorageDirectory().getAbsolutePath();

}
