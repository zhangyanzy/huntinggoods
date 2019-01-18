package com.jinxun.hunting_goods.util;

import java.util.Arrays;

/**
 * Created by admin on 2019/1/8.
 */

public class Base64 {


    private static final char[] toBase64 = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final int linemax = -1;
    private static final boolean doPadding = true;
    private static final byte[] newline = null;

    public Base64() {
    }

    private static final int outLength(int var0) {
        return 4 * ((var0 + 2) / 3);
    }

    private static int encode0(byte[] var0, int var1, int var2, byte[] var3) {
        char[] var4 = toBase64;
        int var5 = var1;
        int var6 = (var2 - var1) / 3 * 3;
        var1 += var6;
        int var7 = 0;

        while(true) {
            int var8;
            int var9;
            int var11;
            do {
                do {
                    if(var5 >= var1) {
                        if(var5 < var2) {
                            var8 = var0[var5++] & 255;
                            var3[var7++] = (byte)var4[var8 >> 2];
                            if(var5 == var2) {
                                var3[var7++] = (byte)var4[var8 << 4 & 63];
                                var3[var7++] = 61;
                                var3[var7++] = 61;
                            } else {
                                var9 = var0[var5] & 255;
                                var3[var7++] = (byte)var4[var8 << 4 & 63 | var9 >> 4];
                                var3[var7++] = (byte)var4[var9 << 2 & 63];
                                var3[var7++] = 61;
                            }
                        }

                        return var7;
                    }

                    var8 = Math.min(var5 + var6, var1);
                    var9 = var5;

                    for(int var10 = var7; var9 < var8; var3[var10++] = (byte)var4[var11 & 63]) {
                        var11 = (var0[var9++] & 255) << 16 | (var0[var9++] & 255) << 8 | var0[var9++] & 255;
                        var3[var10++] = (byte)var4[var11 >>> 18 & 63];
                        var3[var10++] = (byte)var4[var11 >>> 12 & 63];
                        var3[var10++] = (byte)var4[var11 >>> 6 & 63];
                    }

                    var9 = (var8 - var5) / 3 * 4;
                    var7 += var9;
                    var5 = var8;
                } while(var9 != -1);
            } while(var8 >= var2);

            byte[] var13 = newline;
            var11 = newline.length;

            for(var8 = 0; var8 < var11; ++var8) {
                byte var12 = var13[var8];
                var3[var7++] = var12;
            }
        }
    }

    public static String encode(byte[] var0) {
        byte[] var1 = new byte[outLength(var0.length)];
        int var2;
        if((var2 = encode0(var0, 0, var0.length, var1)) != var1.length) {
            var1 = Arrays.copyOf(var1, var2);
        }

        return new String(var1, 0, 0, var1.length);
    }

}
