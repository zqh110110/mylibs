package com.smcb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smcb.config.AppInfoConfig;
import com.smcb.config.WebSecurityConfig;
import com.smcb.entity.Analyst;
import com.smcb.entity.AnalystBuildinfo;
import com.smcb.entity.UserInfo;
import com.smcb.gen.util.AppInfo;
import com.smcb.mapper.AnalystMapper;
import com.smcb.service.IAnalystBuildinfoService;
import com.smcb.service.IAnalystService;
import com.smcb.utils.HttpDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqh
 * @since 2017-04-07
 */
@Controller
@RequestMapping
public class AnalystController {

    @Autowired
    public IAnalystService analystService;
    @Autowired
    public IAnalystBuildinfoService analystBuildinfoService;
    @Autowired
    private AppInfoConfig appInfoConfig;

    @RequestMapping("analystpage")
    public ModelAndView analystPage() {
        ModelAndView mav = new ModelAndView("/analyst/analystlist");
        try {
            String json = HttpDeal.get("http://www.shaomaicaibo.com/static/mconfig/version.ini");
            JSONObject parse = (JSONObject) JSON.parse(json);
            String versionCode = parse.getString("1");
            String versionName = parse.getString("2");
            if (versionName!=null){
                appInfoConfig.setVersionName(versionName);
                appInfoConfig.setVersionCode(versionCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mav.addObject("appInfoConfig", appInfoConfig);
        return mav;
    }

    @RequestMapping("analysts")
    @ResponseBody
    public Map<String,Object> getAnalysService(HttpSession session,@RequestParam(value = "pageNum",required=false,defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",required=false,defaultValue = "10") int pageSize, @RequestParam(value = "keyword",required = false,defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("/analyst/analystlist");
        UserInfo user = (UserInfo) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        int count = analystService.selectCount(new EntityWrapper<Analyst>().eq("user_id",user.getUuid()).like("analyst_name",keyword));
        List<Analyst> analysts = analystService.selectPage(new Page<Analyst>(pageNum,pageSize),new EntityWrapper<Analyst>().eq("user_id",user.getUuid()).like("analyst_name",keyword)).getRecords();
        for (Analyst analyst:analysts) {
            if (analyst.getBuildinfoId()!=null&&analyst.getBuildinfoId()!=0) {
                AnalystBuildinfo analystBuildinfo = analystBuildinfoService.selectById(analyst.getBuildinfoId());
                File file=new File(getDestAppPath(analyst.getAppkey(),analystBuildinfo.getVersionCode(),analystBuildinfo.getVersionName()));
                if (file.exists()) {
                    analyst.setTemp(analystBuildinfo.getBuildpath());
                }
            }
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("analysts",analysts);
        map.put("count",count);
        return map;
    }

    @RequestMapping("addanalystpage")
    public ModelAndView addAnalystPage() {
        ModelAndView mav = new ModelAndView("/analyst/addanalyst");
        return mav;
    }

    @RequestMapping(value = "addanalyst",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String addAnalystPage(HttpSession session, HttpServletRequest request, @RequestBody String data) {
        ObjectMapper mapper = new ObjectMapper();
        Analyst analyst = null;
        try {
            analyst = mapper.readValue(data,Analyst.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserInfo user = (UserInfo) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        analyst.setUserId(user.getUuid());
        analystService.insert(analyst);
        return "success";
    }

    private String getDestAppPath(String appkey,String versionCode,String versionName) {
        return appInfoConfig.appPath+appkey+"/app_"+appkey+"_"+versionCode+"_"+versionName+".apk";
    }

    @RequestMapping(value = "updateanalyst",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String updateAnalystPage(HttpSession session, HttpServletRequest request, @RequestBody String data) {
        ObjectMapper mapper = new ObjectMapper();
        Analyst analyst = null;
        try {
            analyst = mapper.readValue(data,Analyst.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        analystService.updateById(analyst);
        return "success";
    }

    @RequestMapping("getbuildinfo")
    @ResponseBody
    public AnalystBuildinfo getAnalystBuildInfo(@RequestParam("analystId") String analystId) {
            return analystBuildinfoService.selectOne(new EntityWrapper<AnalystBuildinfo>().eq("analyst_id",analystId));
    }
    @RequestMapping(value = "addanalystbuild",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String addAnalystBuildConfig(HttpSession session, HttpServletRequest request, @RequestBody String data) {
        ObjectMapper mapper = new ObjectMapper();
        AnalystBuildinfo analystBuildinfo = null;
        try {
            analystBuildinfo = mapper.readValue(data,AnalystBuildinfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Analyst analyst = new Analyst();
        analyst = analystService.selectById(analystBuildinfo.getAnalystId());
        AnalystBuildinfo obj = (AnalystBuildinfo) analystBuildinfoService.selectOne(new EntityWrapper<AnalystBuildinfo>().eq("analyst_id", analystBuildinfo.getAnalystId()));
        if (obj!=null) {
            analystBuildinfo.setId(obj.getId());
        }
        analystBuildinfoService.insertOrUpdate(analystBuildinfo);
        analyst.setBuildinfoId(analystBuildinfo.getId());
        analystService.updateById(analyst);
        return "success";
    }

    @RequestMapping(value = "delanalyst",method = RequestMethod.POST)
    @ResponseBody
    public String dleAnalyst(HttpSession session, HttpServletRequest request, @RequestParam("id") String id) {
        boolean b = analystService.deleteById(id);
        if (b) {
            return "success";
        } else {
            return "fail";
        }
    }

}
