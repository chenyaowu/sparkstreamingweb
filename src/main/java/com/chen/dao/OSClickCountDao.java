package com.chen.dao;

import com.chen.domain.OSClickCount;
import com.chen.utils.HBaseUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OSClickCountDao {
    public List<OSClickCount> query(String day) throws IOException {
        List<OSClickCount> list = new ArrayList<>();
        HBaseUtils hBaseUtils = HBaseUtils.getInstance();
        Map<String,Long> map = hBaseUtils.query("os_clickcount",day);
        for (Map.Entry<String,Long> entry:map.entrySet() ){
            OSClickCount osClickCount = new OSClickCount();
            osClickCount.setName(entry.getKey());
            osClickCount.setValue(entry.getValue());
            list.add(osClickCount);
        }
        return list;
    }
}
