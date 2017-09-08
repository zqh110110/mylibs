package com.smcb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.smcb.config.AppInfoConfig;
import com.smcb.config.WebSecurityConfig;
import com.smcb.entity.Analyst;
import com.smcb.entity.AnalystBuildinfo;
import com.smcb.entity.UserInfo;
import com.smcb.gen.util.AppInfo;
import com.smcb.gen.util.BuildResultListener;
import com.smcb.gen.util.FileUtil;
import com.smcb.gen.util.GenApkUtil;
import com.smcb.service.IAnalystBuildinfoService;
import com.smcb.service.IAnalystService;
import com.smcb.service.IUserInfoService;
import com.smcb.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/1.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AppInfoConfig appInfoConfig;
    @Autowired
    private IAnalystBuildinfoService analystBuildinfoService;
    @Autowired
    private IAnalystService analystService;

    private Producer captchaProducer = null;

    @Autowired
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @Autowired
    public IUserInfoService userInfoService;

    @RequestMapping("user")
    public String index() {
        return "/user/login";
    }

    @RequestMapping("buildpage")
    public ModelAndView buildform(@RequestParam(value = "analystId",required = false) String analystId) {

        ModelAndView mv = new ModelAndView();
        if (analystId!=null) {
            Analyst analyst = analystService.selectOne(new EntityWrapper<Analyst>().eq("id", analystId));
            AnalystBuildinfo analystBuildinfo = analystBuildinfoService.selectOne(new EntityWrapper<AnalystBuildinfo>().eq("analyst_id", analystId));
            mv.addObject("analystBuildinfo",analystBuildinfo);
            mv.addObject("analyst",analyst);
        }
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

        mv.setViewName("/page");
        mv.addObject("appInfoConfig", appInfoConfig);
        return mv;
    }

    @RequestMapping(value = "/getbuildresult/{appkey}/{versioname}/{versioncode}",method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView gotoBuildResult(HttpServletRequest request,HttpServletResponse response,@PathVariable("appkey") String appkey,@PathVariable("versioncode") String versioncode,@PathVariable("versioname") String versioname){
        ModelAndView mv = new ModelAndView();
        AppInfo appInfo = new AppInfo();
        appInfo.appKey = appkey;
        appInfo.versionName = versioname;
        appInfo.versionCode = versioncode;
        mv.addObject("appInfo",appInfo);
        mv.setViewName("pageresult");
        return mv;
    }


    @RequestMapping(value = "buildapp",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public void buildapp(HttpServletResponse response,@RequestBody String data){
//        ModelAndView mv = new ModelAndView();
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        AppInfo appInfo = null;
        try {
            appInfo = mapper.readValue(data,AppInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File destFile=new File(getDestAppPath(appInfo.appKey,appInfo.versionCode,appInfo.versionName));
        if (!appInfo.forcebuild&&destFile.exists()) {
            writer.println("@@fileExists@@");
            writer.flush();
            writer.close();
            return;
//            mv.addObject("appInfo",appInfo);
//            mv.setViewName("pageresult");
//            return mv;
        }
//        byte[] bytes = Base64.getDecoder().decode(appInfo.iconpic);
        String path = "";
        String imgPath= appInfo.iconpic;
        path = getClass().getClassLoader().getResource("static/").getFile()+imgPath ;
        File file = new File(path);
        String uplod = "-1";
        if (file.exists()) {
//            String dest = appInfoConfig.tempPicPath+"icon_"+System.currentTimeMillis()+".png";
//            int reslut = FileUtil.copyFile(path,dest);
//            if (reslut == 1) {
//                uplod = dest;
//            }
            uplod = path;
        }
        if ("-1".equals(uplod)) {
            writer.println("@@picUploadErr@@");
            writer.flush();
            writer.close();
            return;
//            mv.addObject("err","应用图标生成失败，请检查上传图片是否正确。");
        }
        appInfo.setModuleName(appInfoConfig.moduleName);
        appInfo.setProjectDir(appInfoConfig.projectDir);
        appInfo.setApkpath(getDestAppPath(appInfo.appKey,appInfo.versionCode,appInfo.versionName));

        appInfo.setBuildtype(appInfoConfig.buildtype);
        appInfo.setPkg(appInfoConfig.pkg + "_" + Base64.getEncoder().encodeToString(appInfo.appKey.getBytes()).toLowerCase());
        appInfo.setAppIcon(uplod);

        final boolean[] hasEnd = {false};
        AppInfo finalAppInfo = appInfo;
        PrintWriter finalWriter = writer;
        AppInfo finalAppInfo1 = appInfo;
        appInfo.setListener(new BuildResultListener() {
            @Override
            public void start() {

            }

            @Override
            public void process(String msg, int process) {
                logger.info(msg);
                String flg = "!!!!!";
                finalWriter.write(flg+msg);
                finalWriter.flush();
            }

            @Override
            public void success() {
                AnalystBuildinfo analystBuildinfo = analystBuildinfoService.selectById(finalAppInfo1.buildinfoId);
                if (analystBuildinfo!=null) {
                    analystBuildinfo.setBuildpath("/getbuildresult/" + finalAppInfo1.appKey + '/' + finalAppInfo1.versionName + '/' + finalAppInfo1.versionCode);
                    analystBuildinfoService.updateById(analystBuildinfo);
                }
                finalWriter.println("@@apkBuildSuccess@@");
                finalWriter.flush();
                finalWriter.close();
            }

            @Override
            public void fail() {
                finalWriter.println("@@apkBuildFail@@");
                finalWriter.flush();
                finalWriter.close();
            }

            @Override
            public void completed() {
                hasEnd[0] = true;
            }
        });
        int ret = GenApkUtil.runBuild(appInfo, null);
        if (ret!=0) {
            writer.println("@@buildQueueFull@@");
            writer.flush();
            writer.close();
//            mv.addObject("err","服务器应用打包队列已满，请稍候重试。");
        }

        while (!Thread.currentThread().isInterrupted()&&ret==0&& !hasEnd[0]&&!"-1".equals(uplod)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        mv.setViewName("pageresult");
        finalWriter.close();
//        return mv;
    }

    @RequestMapping("login")
    public ModelAndView login(UserInfo user, HttpServletRequest request, @RequestParam("kaptcha") String kaptcha) {
        ModelAndView mv = new ModelAndView();
        //用户输入的验证码的值
        String kaptchaExpected = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //校验验证码是否正确
        if (kaptcha == null || !kaptcha.equals(kaptchaExpected)) {
            mv.setViewName("forward:/user?code=1");
            request.setAttribute(WebSecurityConfig.SESSION_KEY, user);
            request.setAttribute("kaptchaerr", "验证码不正确");
            return mv;
        }

        user.setPassword(ThreeDES.byte2hex(ThreeDES.encryptMode(MD5.MD5Encoder(user.getPassword()).getBytes())));

        int count = userInfoService.selectCount(new EntityWrapper<UserInfo>(user));
        user = userInfoService.selectOne(new EntityWrapper<UserInfo>(user));
        if (count > 0) {
            mv.setViewName("/index");
            if (user.getAccount().equals("admin")) {
                mv.addObject("menu", "<li><a href=\"/userlist?page=1&s_account=&s_number=&s_name=\" target=\"right\"><span class=\"icon-caret-right\"></span>用户管理</a></li>\n" +
                        "    <li><a href=\"/adduser\" target=\"right\"><span class=\"icon-caret-right\"></span>添加用户</a></li>");
            }
        } else {
            mv.setViewName("forward:/user?code=2");
            request.setAttribute(WebSecurityConfig.SESSION_KEY, user);
            return mv;
        }
        request.getSession().setAttribute(WebSecurityConfig.SESSION_KEY, user);
        return mv;
    }

    /**
     * 获得二维码
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getQR/{appkey}/{versioname}/{versioncode}",method={RequestMethod.POST,RequestMethod.GET})
    public void getTwoDimensionForIOSs(HttpServletRequest request,HttpServletResponse response,@PathVariable("appkey") String appkey,@PathVariable("versioncode") String versioncode,@PathVariable("versioname") String versioname){


        try {
            String url = request.getServerName()+":"+request.getServerPort()+"/appDownload/"+appkey+"/"+versioname+"/"+versioncode;
            getQRCode(url, response, 200, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/kaptcha.jpg")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);

        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    private String upload(MultipartFile file) {
        if (file.isEmpty()) {
            return "-1";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = appInfoConfig.tempPicPath;
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return dest.getAbsolutePath();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }
    private String upload(byte[] file,String fileName) {
        // 文件上传后的路径
        String filePath = appInfoConfig.tempPicPath;
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(dest);
            fos.write(file);
            fos.flush();
            fos.close();
            return dest.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    private String getDestAppPath(String appkey,String versionCode,String versionName) {
        return appInfoConfig.appPath+appkey+"/app_"+appkey+"_"+versionCode+"_"+versionName+".apk";
    }

    @RequestMapping(value="/appDownload/{appkey}/{versioname}/{versioncode}",method= RequestMethod.GET)
    public void testDownload(HttpServletResponse res,@PathVariable("appkey") String appkey,@PathVariable("versioncode") String versioncode,@PathVariable("versioname") String versioname) throws IOException{
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=app.apk");
        File file=new File(getDestAppPath(appkey,versioncode,versioname));
        res.setContentLengthLong(file.length());

        ServletOutputStream fos = res.getOutputStream();
        FileInputStream fs=new FileInputStream(file);
        byte[] buffer=new byte[1024];
        int len=0;
        while((len=fs.read(buffer))!=-1){
            fos.write(buffer, 0, len);
        }
        fos.flush();
        fos.close();
        fs.close();
    }

    /**
     *  生成web版本二维码
     * @param url 要生成二维码的路径
     * @param response response对象
     * @param width 二维码宽度
     * @param height 二维码高度
     * @throws IOException
     */
    public void getQRCode(String url,HttpServletResponse response,int width,int height) throws IOException{
        if (url != null && !"".equals(url)) {
            ServletOutputStream stream = null;
            try {
                stream = response.getOutputStream();
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix m = writer.encode(url, BarcodeFormat.QR_CODE, height, width);
                MatrixToImageWriter.writeToStream(m, "png", stream);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("生成二维码失败!");
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
    }

    private int pageNum = 1;
    private int pageSIZE = 5;
    private String kwords;

    @RequestMapping(value = "userlist", method = RequestMethod.GET)
    public ModelAndView userlist(@RequestParam int page, @RequestParam(value = "", required = false) String s_account, @RequestParam(value = "", required = false) String s_number, @RequestParam(value = "", required = false) String s_name) {
        this.pageNum = page;
        EntityWrapper<UserInfo> wrapper = new EntityWrapper<UserInfo>();
        if(!StringUtils.isEmpty(s_account)){
            wrapper.like("account","%"+s_account+"%");
        }
        if(!StringUtils.isEmpty(s_number)){
            wrapper.like("number","%"+s_number+"%");
        }
        if(!StringUtils.isEmpty(s_name)){
            wrapper.like("name","%"+s_name+"%");
        }
        int total = userInfoService.selectCount(wrapper);
        int size = total / pageSIZE + (total % pageSIZE > 0 ? 1 : 0);
        List<UserInfo> users = userInfoService.selectPage(new Page<UserInfo>(page, pageSIZE),wrapper.ne("uuid","1").orderBy("update_time",false)).getRecords();
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", users);
        mv.addObject("size", size);
        mv.addObject("page", pageNum);
        mv.addObject("s_account", s_account);
        mv.addObject("s_number", s_number);
        mv.addObject("s_name", s_name);
        mv.addObject("total",total);
        mv.addObject("pageSIZE",pageSIZE);
        mv.setViewName("/user/userlist");
        return mv;
    }


    @RequestMapping("adduser")
    public ModelAndView adduser() {
        ModelAndView mv = new ModelAndView();
        UserInfo user = new UserInfo();
        mv.addObject("user", user);
        mv.setViewName("/user/useradd");
        return mv;
    }

    @RequestMapping(value = "doAdd", method = RequestMethod.POST)
    public ModelAndView doAdd(@ModelAttribute UserInfo user) {
        logger.debug("添加用户入口====");
        int size = userInfoService.selectCount(new EntityWrapper<UserInfo>().eq("account",user.getAccount()).or().eq("number",user.getNumber()));
        ModelAndView mv = new ModelAndView();
        if (size > 0) {
            mv.addObject("msg", "用户已存在!");
        } else {
            user.setUuid(AppUtils.getUUID());
            user.setPassword(ThreeDES.byte2hex(ThreeDES.encryptMode(MD5.MD5Encoder(user.getPassword()).getBytes())));
            user.setUpdateTime(DateFormatUtils.date2Str(new Date()));
            userInfoService.insert(user);
            mv.addObject("msg", "保存成功!");
            mv.addObject("user", new UserInfo());
        }
        mv.setViewName("/user/useradd");
        return mv;
    }

    @RequestMapping(value = "doDel", method = RequestMethod.GET)
    public ModelAndView doDel(@RequestParam String uuid) {
        ModelAndView mv = new ModelAndView("redirect:/userlist");
        mv.addObject("page", pageNum);
        if (uuid.equals("1")) return mv;
        userInfoService.delete(new EntityWrapper<UserInfo>().eq("uuid",uuid));
        return mv;
    }
}
