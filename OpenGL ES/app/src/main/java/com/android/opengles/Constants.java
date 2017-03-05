package com.android.opengles;

public class Constants {
    public static final int BYTES_PER_FLOAT            = 4;
    public static final int BYTES_PER_SHORT            = 3;

    public static final int POSITION_COMPONENT_COUNT   = 3;
    public static final int NORMAL_COMPONENT_COUNT     = 3;
    public static final int TEXCOORD_COMPONENT_COUNT   = 2;
    public static final int COUNT                      = POSITION_COMPONENT_COUNT +
                                                         NORMAL_COMPONENT_COUNT +
                                                         TEXCOORD_COMPONENT_COUNT;
    public static final int STRIDE                     = BYTES_PER_FLOAT * COUNT;
}
