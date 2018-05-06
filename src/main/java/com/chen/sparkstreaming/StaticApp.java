package com.chen.sparkstreaming;

import com.chen.dao.OSClickCountDao;
import com.chen.domain.OSClickCount;
import com.chen.domain.UrlClickCount;
import com.chen.dao.UrlClickCountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StaticApp {
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String today = sdf.format(d);


    private static Map<String,String> url = new HashMap<>();

    static {
        url.put("rrtsmc","校园平台系统");
        url.put("dapp","综合素质评价系统");
        url.put("oas","OA办公系统");
        url.put("weixin","微信H5");


    }
    @Autowired
    private UrlClickCountDao urlClickCountDao;
    @Autowired
    private OSClickCountDao osClickCountDao;

    @RequestMapping("/url_clickcount_dynamic")
    @ResponseBody
    public List urlClickCount() throws Exception{
        System.out.println(today);
        List<UrlClickCount> list =  urlClickCountDao.query(today);
        for (UrlClickCount model :list){
            model.setName(url.get(model.getName().substring(9)));
        }
        return list;


    }
    @RequestMapping("/os_clickcount_dynamic")
    @ResponseBody
    public List osClickCount() throws Exception{
        List<OSClickCount> list =  osClickCountDao.query(today);
        for (OSClickCount model :list){
            model.setName(model.getName().substring(9));
        }
        return list;


    }

    @RequestMapping(value = "/urlecharts")
    public ModelAndView urlEcharts(){
        return new ModelAndView("urlecharts");
    }

    @RequestMapping(value = "/osecharts")
    public ModelAndView osEcharts(){
        return new ModelAndView("osecharts");
    }
}
