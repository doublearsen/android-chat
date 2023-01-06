package cn.wildfire.chat.kit.conversation.baidutranslate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clifton
 * @create 2021/2/7 - 14:07
 */
public class Dto {
    String from;
    String to;
    public List<trans_result> resultList = new ArrayList<>();


    public class trans_result {
        String src;
        public String dst;

        @Override
        public String toString() {
            return "res{" +
                    "src='" + src + '\'' +
                    ", dst='" + dst + '\'' +
                    '}';
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<trans_result> getTransResult() {
        return resultList;
    }

    public void setTransResult(List<trans_result> transResult) {
        this.resultList = transResult;
    }
}
