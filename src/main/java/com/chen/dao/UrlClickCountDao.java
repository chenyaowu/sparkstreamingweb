package com.chen.dao;

import com.chen.domain.UrlClickCount;
import com.chen.utils.HBaseUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UrlClickCountDao {
    public List<UrlClickCount> query(String day) throws IOException {
        List<UrlClickCount> list = new ArrayList<>();
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        Map<String,Long> map = hBaseUtils.query("url_clickcount",day);
        for (Map.Entry<String,Long> entry:map.entrySet() ){
            UrlClickCount urlClickCount = new UrlClickCount();
            urlClickCount.setName(entry.getKey());
            urlClickCount.setValue(entry.getValue());
            list.add(urlClickCount);
        }
        return list;
    }
}
