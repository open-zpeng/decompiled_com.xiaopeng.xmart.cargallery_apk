package com.xiaopeng.share.encode;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpHeaders;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.GetRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.PostRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
/* loaded from: classes.dex */
public class NoneSecurityUtils {
    private static final String OTP = "otp";
    private static final String TAG = "NoneSecurityUtils";
    private static final List<String> AUTH_EXCLUDE = Arrays.asList(BizConstants.HEADER_SIGNATURE, BizConstants.HEADER_AUTHORIZATION);
    private static final List<String> SIGN_EXCLUDE = Arrays.asList(BizConstants.HEADER_SIGNATURE);

    public static String getAuthorization(IRequest request, String method, String body, String key, String otp) {
        String xpSignature = generateSignature(request, method, body, key, AUTH_EXCLUDE);
        Map<String, String> map = new HashMap<>(1);
        map.put(BizConstants.AUTHORIZATION_XPSIGN, xpSignature);
        map.put(OTP, otp);
        byte[] bytes = new byte[0];
        try {
            bytes = new Gson().toJson(map).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buildTokenData(otp, bytes);
    }

    public static String getSignature(IRequest request, String method, String body, String key) {
        return generateSignature(request, method, body, key, SIGN_EXCLUDE);
    }

    private static String generateSignature(IRequest request, String method, String body, String key, List<String> excludeSet) {
        String md5;
        Mac hmacSHA256;
        HttpHeaders headers = getRequestHeaders(request);
        Map<String, String> map = new HashMap<>();
        if (headers != null) {
            map.putAll(headers.headersMap);
        }
        for (String s : excludeSet) {
            map.remove(s);
        }
        String parameter = getSignParameters(map, request.getUrl());
        try {
            md5 = TextUtils.isEmpty(body) ? "" : MD5Utils.getMD5(body);
            hmacSHA256 = Mac.getInstance("HmacSHA256");
            try {
                byte[] secretBytes = key.getBytes("UTF-8");
                hmacSHA256.init(new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA256"));
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        try {
            byte[] sha256Encode = hmacSHA256.doFinal((method + '&' + parameter + '&' + md5).getBytes("UTF-8"));
            String signed = SecurityCommon.parseByte2HexStr(sha256Encode).toLowerCase();
            return signed;
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    private static String buildTokenData(String otp, byte[] data) {
        try {
            String dataContent = new String(data);
            LogUtils.d(TAG, "xp-sign:" + dataContent);
            return new String(Base64.encode(data, 2), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static HttpHeaders getRequestHeaders(IRequest request) {
        if (request instanceof GetRequestAdapter) {
            return ((GetRequestAdapter) request).getHeaders();
        }
        if (request instanceof PostRequestAdapter) {
            return ((PostRequestAdapter) request).getHeaders();
        }
        return null;
    }

    private static String getSignParameters(Map<String, String> headMap, String url) {
        String value;
        List<String> keyList = new ArrayList<>(headMap.keySet());
        Uri uri = Uri.parse(url);
        Set<String> paramKeys = uri.getQueryParameterNames();
        keyList.addAll(paramKeys);
        Collections.sort(keyList);
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < keyList.size(); index++) {
            String key = keyList.get(index);
            if (paramKeys.contains(key) || key.startsWith(BizConstants.HEADER_PREFIX)) {
                if (headMap.containsKey(key)) {
                    if (BuildInfoUtils.isEngVersion() && !TextUtils.isEmpty(uri.getQueryParameter(key))) {
                        throw new RuntimeException("Duplicate keys in header and parameters!");
                    }
                    value = headMap.get(key);
                    key = key.toUpperCase();
                } else {
                    value = uri.getQueryParameter(key);
                }
                if (!TextUtils.isEmpty(value)) {
                    if (builder.length() > 0) {
                        builder.append('&');
                    }
                    builder.append(key + "=" + value);
                }
            }
        }
        return builder.toString();
    }
}
