package com.ta.utdid2.b.a;

import java.io.UnsupportedEncodingException;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.utils.CharsetNames;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
/* compiled from: Base64.java */
/* loaded from: classes.dex */
public class b {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Base64.java */
    /* loaded from: classes.dex */
    public static abstract class a {
        public int a;
        public byte[] b;

        a() {
        }
    }

    public static byte[] decode(String str, int flags) {
        return decode(str.getBytes(), flags);
    }

    public static byte[] decode(byte[] input, int flags) {
        return decode(input, 0, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        C0023b c0023b = new C0023b(flags, new byte[(len * 3) / 4]);
        if (!c0023b.a(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (c0023b.a == c0023b.b.length) {
            return c0023b.b;
        }
        byte[] bArr = new byte[c0023b.a];
        System.arraycopy(c0023b.b, 0, bArr, 0, c0023b.a);
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Base64.java */
    /* renamed from: com.ta.utdid2.b.a.b$b  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static class C0023b extends a {
        private static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private final int[] c;
        private int state;
        private int value;

        public C0023b(int i, byte[] bArr) {
            this.b = bArr;
            this.c = (i & 8) == 0 ? a : b;
            this.state = 0;
            this.value = 0;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0061  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0068  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean a(byte[] r12, int r13, int r14, boolean r15) {
            /*
                Method dump skipped, instructions count: 324
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.b.C0023b.a(byte[], int, int, boolean):boolean");
        }
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(encode(input, flags), CharsetNames.US_ASCII);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return encode(input, 0, input.length, flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        c cVar = new c(flags, null);
        int i = (len / 3) * 4;
        if (cVar.f180b) {
            if (len % 3 > 0) {
                i += 4;
            }
        } else {
            switch (len % 3) {
                case 1:
                    i += 2;
                    break;
                case 2:
                    i += 3;
                    break;
            }
        }
        if (cVar.f181c && len > 0) {
            i += (((len - 1) / 57) + 1) * (cVar.f182d ? 2 : 1);
        }
        cVar.b = new byte[i];
        cVar.a(input, offset, len, true);
        if (a || cVar.a == i) {
            return cVar.b;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Base64.java */
    /* loaded from: classes.dex */
    public static class c extends a {
        static final /* synthetic */ boolean a = true;
        private static final byte[] c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 43, 47};
        private static final byte[] d = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 45, 95};
        int b;

        /* renamed from: b  reason: collision with other field name */
        public final boolean f180b;

        /* renamed from: c  reason: collision with other field name */
        public final boolean f181c;
        private int count;

        /* renamed from: d  reason: collision with other field name */
        public final boolean f182d;
        private final byte[] e;
        private final byte[] f;

        public c(int i, byte[] bArr) {
            this.b = bArr;
            this.f180b = (i & 1) == 0;
            boolean z = (i & 2) == 0;
            this.f181c = z;
            this.f182d = (i & 4) != 0;
            this.f = (i & 8) == 0 ? c : d;
            this.e = new byte[2];
            this.b = 0;
            this.count = z ? 19 : -1;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public boolean a(byte[] bArr, int i, int i2, boolean z) {
            int i3;
            int i4;
            int i5;
            byte b;
            int i6;
            int i7;
            byte b2;
            byte b3;
            int i8;
            int i9;
            byte[] bArr2 = this.f;
            byte[] bArr3 = this.b;
            int i10 = this.count;
            int i11 = i2 + i;
            int i12 = 0;
            switch (this.b) {
                case 0:
                default:
                    i3 = i;
                    i4 = -1;
                    break;
                case 1:
                    if (i + 2 <= i11) {
                        int i13 = i + 1;
                        int i14 = i13 + 1;
                        i4 = (bArr[i13] & 255) | ((this.e[0] & 255) << 16) | ((bArr[i] & 255) << 8);
                        this.b = 0;
                        i3 = i14;
                        break;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
                case 2:
                    i3 = i + 1;
                    if (i3 <= i11) {
                        byte[] bArr4 = this.e;
                        i4 = ((bArr4[1] & 255) << 8) | ((bArr4[0] & 255) << 16) | (bArr[i] & 255);
                        this.b = 0;
                        break;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
            }
            if (i4 == -1) {
                i5 = 0;
            } else {
                bArr3[0] = bArr2[(i4 >> 18) & 63];
                bArr3[1] = bArr2[(i4 >> 12) & 63];
                bArr3[2] = bArr2[(i4 >> 6) & 63];
                bArr3[3] = bArr2[i4 & 63];
                i10--;
                if (i10 != 0) {
                    i5 = 4;
                } else {
                    if (!this.f182d) {
                        i9 = 4;
                    } else {
                        i9 = 5;
                        bArr3[4] = MqttWireMessage.MESSAGE_TYPE_PINGRESP;
                    }
                    i5 = i9 + 1;
                    bArr3[i9] = 10;
                    i10 = 19;
                }
            }
            while (true) {
                int i15 = i3 + 3;
                if (i15 <= i11) {
                    int i16 = (bArr[i3 + 2] & 255) | ((bArr[i3 + 1] & 255) << 8) | ((bArr[i3] & 255) << 16);
                    bArr3[i5] = bArr2[(i16 >> 18) & 63];
                    bArr3[i5 + 1] = bArr2[(i16 >> 12) & 63];
                    bArr3[i5 + 2] = bArr2[(i16 >> 6) & 63];
                    bArr3[i5 + 3] = bArr2[i16 & 63];
                    i5 += 4;
                    i10--;
                    if (i10 != 0) {
                        i3 = i15;
                        i12 = 0;
                    } else {
                        if (this.f182d) {
                            bArr3[i5] = MqttWireMessage.MESSAGE_TYPE_PINGRESP;
                            i5++;
                        }
                        bArr3[i5] = 10;
                        i5++;
                        i3 = i15;
                        i10 = 19;
                        i12 = 0;
                    }
                } else {
                    if (z) {
                        int i17 = this.b;
                        if (i3 - i17 == i11 - 1) {
                            if (i17 > 0) {
                                b3 = this.e[i12];
                                i8 = 1;
                            } else {
                                int i18 = i12;
                                b3 = bArr[i3];
                                i3++;
                                i8 = i18;
                            }
                            int i19 = (b3 & 255) << 4;
                            this.b = i17 - i8;
                            int i20 = i5 + 1;
                            bArr3[i5] = bArr2[(i19 >> 6) & 63];
                            i5 = i20 + 1;
                            bArr3[i20] = bArr2[i19 & 63];
                            if (this.f180b) {
                                int i21 = i5 + 1;
                                bArr3[i5] = 61;
                                i5 = i21 + 1;
                                bArr3[i21] = 61;
                            }
                            if (this.f181c) {
                                if (this.f182d) {
                                    bArr3[i5] = MqttWireMessage.MESSAGE_TYPE_PINGRESP;
                                    i5++;
                                }
                                bArr3[i5] = 10;
                                i5++;
                            }
                        } else if (i3 - i17 == i11 - 2) {
                            if (i17 > 1) {
                                b = this.e[i12];
                                i6 = 1;
                            } else {
                                int i22 = i12;
                                b = bArr[i3];
                                i3++;
                                i6 = i22;
                            }
                            int i23 = (b & 255) << 10;
                            if (i17 > 0) {
                                i7 = i6 + 1;
                                b2 = this.e[i6];
                            } else {
                                i7 = i6;
                                b2 = bArr[i3];
                                i3++;
                            }
                            int i24 = i23 | ((b2 & 255) << 2);
                            this.b = i17 - i7;
                            int i25 = i5 + 1;
                            bArr3[i5] = bArr2[(i24 >> 12) & 63];
                            int i26 = i25 + 1;
                            bArr3[i25] = bArr2[(i24 >> 6) & 63];
                            int i27 = i26 + 1;
                            bArr3[i26] = bArr2[i24 & 63];
                            if (this.f180b) {
                                bArr3[i27] = 61;
                                i27++;
                            }
                            if (!this.f181c) {
                                i5 = i27;
                            } else {
                                if (this.f182d) {
                                    bArr3[i27] = MqttWireMessage.MESSAGE_TYPE_PINGRESP;
                                    i27++;
                                }
                                bArr3[i27] = 10;
                                i5 = i27 + 1;
                            }
                        } else if (this.f181c && i5 > 0 && i10 != 19) {
                            if (this.f182d) {
                                bArr3[i5] = MqttWireMessage.MESSAGE_TYPE_PINGRESP;
                                i5++;
                            }
                            bArr3[i5] = 10;
                            i5++;
                        }
                        boolean z2 = a;
                        if (!z2 && this.b != 0) {
                            throw new AssertionError();
                        }
                        if (!z2 && i3 != i11) {
                            throw new AssertionError();
                        }
                    } else if (i3 == i11 - 1) {
                        byte[] bArr5 = this.e;
                        int i28 = this.b;
                        this.b = i28 + 1;
                        bArr5[i28] = bArr[i3];
                    } else if (i3 == i11 - 2) {
                        byte[] bArr6 = this.e;
                        int i29 = this.b;
                        int i30 = i29 + 1;
                        this.b = i30;
                        bArr6[i29] = bArr[i3];
                        this.b = i30 + 1;
                        bArr6[i30] = bArr[i3 + 1];
                    }
                    this.a = i5;
                    this.count = i10;
                    return true;
                }
            }
        }
    }

    private b() {
    }
}
