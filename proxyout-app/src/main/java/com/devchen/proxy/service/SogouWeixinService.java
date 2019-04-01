package com.devchen.proxy.service;

import com.devchen.proxy.dal.dao.WeixinPageSourceDAO;
import com.devchen.proxy.dal.entity.WeixinPageSourceEntity;
import com.devchen.proxy.entity.ProxyIpEntity;
import com.devchen.proxy.webDriver.IWebDriverHandler;
import com.devchen.proxy.webDriver.SogouWexinHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SogouWeixinService {

    @Resource
    private SogouWexinHandler sogouWexinHandler;

    @Resource
    private WebDriverService webDriverService;

    @Resource
    private ProxyIpService proxyIpService;

    @Resource
    private WeixinPageSourceDAO weixinPageSourceDAO;

    private final static Logger logger = LoggerFactory.getLogger(SogouWeixinService.class);

    private String sogouWeixinTemplate= "https://weixin.sogou.com/weixin?type=1&s_from=input&query=%s&ie=utf8&_sug_=n&_sug_type_=";

    public String getWeixinPageList(String weixin) {
        WeixinPageSourceEntity page =  weixinPageSourceDAO.selectOne(weixin);
        return page.getPageUrl();
    }

    public void saveWeixinPageUrl(String weixinId) {
        String url = getWeixinPageList(weixinId);
        WeixinPageSourceEntity page =weixinPageSourceDAO.selectOne(weixinId);
        if(page == null) {
            page = new WeixinPageSourceEntity();
            page.setPageUrl(url);
            page.setWeixinId(weixinId);
            weixinPageSourceDAO.insertOne(page);
        } else {
            page.setPageUrl(url);
            weixinPageSourceDAO.updatePageUrl(page);
        }

    }

    public  void runSpider() {
        List<String> test = new ArrayList<>();
        test.add("lc_funds");
        test.add("fuguo1999");
        for(String url : test) {
            try {
                saveWeixinPageUrl(url);
            }catch (Exception e) {
                logger.error("error", e);
            }

            try {
              Thread.sleep(5L * 60L * 1000L);
            } catch (Exception e) {
                logger.error("error", e);
            }

        }
    }



}
