package com.devchen.proxy.controller;

import com.devchen.proxy.dal.dao.WeixinPageResultDAO;
import com.devchen.proxy.dal.entity.WeixinPageSourceEntity;
import com.devchen.proxy.entity.ProxyResultEntity;
import com.devchen.proxy.service.ProxyIpService;
import com.devchen.proxy.service.ProxyService;
import com.devchen.proxy.service.SogouWeixinService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.xalan.xsltc.compiler.util.StringStack;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ProxyController {

    @Resource
    private ProxyService proxyService;

    @Resource
    private SogouWeixinService sogouWeixinService;

    @Resource
    private WeixinPageResultDAO weixinPageResultDAO;

    @RequestMapping(value = "/proxy", method = RequestMethod.POST)
    public void proxyRequest(@RequestParam("request") String proxyRequestJson, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws Exception{
        proxyService.proxy(httpRequest,httpResponse,proxyRequestJson);

    }

    @RequestMapping(value = "/weixin-proxy", method = RequestMethod.GET)
    @ResponseBody
    public WeixinPageSourceEntity proxyRequest(@RequestParam("id") String id) throws Exception{
        return sogouWeixinService.getWeixinPageList(id);

    }


    @RequestMapping(value = "/weixin-proxy-v2", method = RequestMethod.GET)
    @ResponseBody
    public List<ProxyResultEntity> proxyRequestV2(@RequestParam("id") String id) throws Exception{
        String json =  weixinPageResultDAO.selectOne(id).getPageUrl();
        List<ProxyResultEntity> result = (new Gson()).fromJson(json, new TypeToken<List<ProxyResultEntity>>() {
        }.getType());
        return result;
    }

}
