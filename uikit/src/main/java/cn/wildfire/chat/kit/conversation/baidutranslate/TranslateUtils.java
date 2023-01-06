package cn.wildfire.chat.kit.conversation.baidutranslate;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mars.xlog.Log;

import cn.wildfirechat.remote.UploadMediaCallback;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @author Clifton
 * @create 2020/8/12 - 15:18
 */
public class TranslateUtils {

    static String url = "http://api.fanyi.baidu.com/api/trans/vip/translate"; // 请求链接
    static String App_id = "20221227001510738";
    static String KEY = "e8BgTHZkNnsBZX0WpTpG";
    static String salt = "123qwe";

    public static String postFromParameters(String s,UploadMediaCallback callback) {
        String q = s;
//        String q = "device,group";
        String from = "auto";
        String to = "zh";
        String sign = generateSign(q);
        OkHttpClient okHttpClient = new OkHttpClient(); // OkHttpClient对象
        RequestBody formBody = new FormBody.Builder()
                .add("q", q)
                .add("from", from)
                .add("to", to)
                .add("appid", App_id)
                .add("salt", salt)
                .add("sign", sign)
                .build(); // 表单键值对
        Request request = new Request.Builder().url(url).post(formBody).build(); // 请求


        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFail(4);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200) {
                    callback.onFail(4);
                } else {
                    String strResponse = StringEscapeUtils.unescapeJava(response.body().string());
                    callback.onSuccess(strResponse);
                }

            }
        });


       /* okHttpClient.newCall(request).enqueue(new Callback() {// 回调

            public void onResponse(Call call, Response response) throws IOException {
                String s = StringEscapeUtils.unescapeJava(response.body().string());
                System.out.println(s);
                return;
            }

            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());//失败后的回调
                throw new RuntimeException();
            }
        });
        return null;*/
        return null;
    }


    public static void postFromParameters() {
        String q = "DashBoard...DashBoard Manage...DashBoard Add...DashBoard Edit";
//        String q = "device,group";
        String from = "auto";
        String to = "zh";
        String sign = generateSign(q);
        OkHttpClient okHttpClient = new OkHttpClient(); // OkHttpClient对象
        RequestBody formBody = new FormBody.Builder()
                .add("q", q)
                .add("from", from)
                .add("to", to)
                .add("appid", App_id)
                .add("salt", salt)
                .add("sign", sign)
                .build(); // 表单键值对
        Request request = new Request.Builder().url(url).post(formBody).build(); // 请求
        okHttpClient.newCall(request).enqueue(new Callback() {// 回调

            public void onResponse(Call call, Response response) throws IOException {
                String s = StringEscapeUtils.unescapeJava(response.body().string());
                JSONObject jsonObject = JSONObject.parseObject(s);
                System.out.println(jsonObject.get("trans_result"));
            }

            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());//失败后的回调
            }
        });
    }

    public static String getMD5(String info)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++)
            {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else
                {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }


    private static String generateSign(String q) {
        String tmp = App_id + q + salt + KEY;
        String strSign =  getMD5(tmp);
        return strSign;
    }

}
