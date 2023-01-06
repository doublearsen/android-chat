package cn.wildfire.chat.kit.conversation.baidutranslate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import cn.wildfirechat.remote.UploadMediaCallback;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Clifton
 * @create 2020/8/17 - 17:06
 */
public class Entry {

    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "C:\\workspace\\BaiduTranslate\\src\\main\\java\\com\\clifton\\translate\\il8n\\zh.json";
        String jsonStr = JsonUtils.readJsonFile(path);

        JSONObject jsonObject = (JSONObject) JSONArray.parseObject(jsonStr, Feature.OrderedField);

        Pattern pAz = Pattern.compile("^[A-Za-z0-9 !.]+$");
//        Pattern pZh = Pattern.compile("[\u4e00-\u9fa5]");
        Pattern pAZ = Pattern.compile("^[A-Z0-9 ]+$");
        Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
        LinkedList<String> linkedList = new LinkedList<>();
        LinkedList<String> resList = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            Matcher mAz = pAz.matcher(value.toString());
            if (!mAz.matches()) {
                iterator.remove();
                continue;
            }
            //纯大写字符串
            Matcher mAZ = pAZ.matcher(value.toString());
            if (mAZ.matches()){
                iterator.remove();
                continue;
            }

            String s = String.valueOf(entry.getValue());
            linkedList.add(s);
            stringBuilder.append(s).append("...");

            if (linkedList.size() % 50 == 0){
                String res = "";
                stringBuilder.setLength(0);
                Dto dto = JSON.parseObject(res, Dto.class);
                String dst = "";
                System.out.println(dst);
                resList.addAll(Arrays.asList(dst.split("。。。")[0].split("…")));
                Thread.sleep(1000);
            }
        }
        String res = "";
        Dto dto = JSON.parseObject(res, Dto.class);
        String dst = "";
        System.out.println(dst);
        resList.addAll(Arrays.asList(dst.split("。。。")[0].split("…")));


        if (resList.size() == linkedList.size()){
            while (!resList.isEmpty()){
                String dstItem = resList.poll();
                String key = linkedList.poll();
                jsonObject.put(key, dstItem);
            }


            //将json转换为json字符串
            String newJsonString = jsonObject.toString();
            JsonUtils.writeJsonFile(newJsonString, "C:\\workspace\\BaiduTranslate\\src\\main\\java\\com\\clifton\\translate\\il8n\\new.json");
        }else {
            System.out.println("队列不一样长");
        }

    }

}
