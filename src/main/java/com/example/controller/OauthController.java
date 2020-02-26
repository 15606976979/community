package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.dao.UserRepository;
import com.example.pojo.User;
import com.example.pojo.github.AccessTokenDTO;
import com.example.pojo.github.GitHubUser;
import com.example.util.OkHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 第一步，用户登录时重定向到github
 * 第二步，用户成功登陆后，github跳转回来的地址会携带一个“code”，并且会把上一步6个参数中的state返回来，
 *       我们要写代码接收这个code，并且写一个post请求，发送到以下access_token接口。
 * 第三步，通过user这个API，第二步获得的token为参数就可以拿到具体的用户信息。
 */
@Controller
@RequestMapping("/authoriza")
public class OauthController {

    @Autowired
    private OkHttpUtils okHttpUtils;

    @Autowired
    private UserRepository userRepository;

    @Value("${github.cliend.id}")
    private String cliendId;

    @Value("${github.cliend.secret}")
    private String cliendSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Value("${github.getAccessToken.url}")
    private String getAccessTokenUrl;

    @Value("${github.getUser.url}")
    private String getUserUrl;

    //首页
    @RequestMapping("/index")
    public String index(HttpServletRequest request,HttpServletResponse response){
        //登录成功 写入cookie和session
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                User user = this.userRepository.findByToken(cookie.getValue());
                request.getSession().setAttribute("user",user);
                response.addCookie(cookie);
            }
        }
        return "index";
    }

    /*
     * 第二步，用户成功登陆后，github跳转回来的地址会携带一个“code”，并且会把上一步6个参数中的state返回来，
     *       我们要写代码接收这个code，并且写一个post请求，发送到以下access_token接口。
     * 第三步，通过user这个API，第二步获得的token为参数就可以拿到具体的用户信息。
     * {"login":"15606976979","id":61258451,"node_id":"MDQ6VXNlcjYxMjU4NDUx","avatar_url":"https://avatars3.githubusercontent.com/u/61258451?v=4","gravatar_id":"","url":"https://api.github.com/users/15606976979","html_url":"https://github.com/15606976979","followers_url":"https://api.github.com/users/15606976979/followers","following_url":"https://api.github.com/users/15606976979/following{/other_user}","gists_url":"https://api.github.com/users/15606976979/gists{/gist_id}","starred_url":"https://api.github.com/users/15606976979/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/15606976979/subscriptions","organizations_url":"https://api.github.com/users/15606976979/orgs","repos_url":"https://api.github.com/users/15606976979/repos","events_url":"https://api.github.com/users/15606976979/events{/privacy}","received_events_url":"https://api.github.com/users/15606976979/received_events","type":"User","site_admin":false,"name":null,"company":null,"blog":"","location":null,"email":null,"hireable":null,"bio":null,"public_repos":2,"public_gists":0,"followers":0,"following":0,"created_at":"2020-02-20T01:35:36Z","updated_at":"2020-02-24T07:18:38Z","private_gists":0,"total_private_repos":0,"owned_private_repos":0,"disk_usage":2,"collaborators":0,"two_factor_authentication":false,"plan":{"name":"free","space":976562499,"collaborators":0,"private_repos":10000}}
     */
    @RequestMapping("callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO tokenDTO = new AccessTokenDTO();
        tokenDTO.setClient_id(cliendId);
        tokenDTO.setClient_secret(cliendSecret);
        tokenDTO.setRedirect_uri(redirectUri);
        tokenDTO.setCode(code);
        tokenDTO.setState(state);

        String reponseJson = okHttpUtils.postHttp(JSON.toJSONString(tokenDTO),getAccessTokenUrl);
        String accessToken = reponseJson.split("&")[0].split("=")[1];

        String gitHubUserStr = okHttpUtils.getHttp(getUserUrl+accessToken);
        System.out.println(gitHubUserStr);
        GitHubUser gitHubUser = JSON.parseObject(gitHubUserStr,GitHubUser.class);
        System.out.println(gitHubUser);
        if (gitHubUser != null){
            User user = new User();
            user.setName(gitHubUser.getLogin());
            user.setAccountId(gitHubUser.getId().toString());
            user.setToken(UUID.randomUUID().toString());//token持久化到库里，进到首页先判断token在数据库存不存在
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            this.userRepository.save(user);
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/authoriza/index";
        }else{
            //登录失败  重新登录
            return "redirect:/authoriza/index";
        }
    }
}
