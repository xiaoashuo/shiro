package com.example.shiro.shiro.service.impl;

import com.example.shiro.utils.spring.SpringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第一种动态更新方法
 * 第二种为注解权限的在shiroUtils可以看到
 * 应用启动时后会加载此过滤链
 * 过滤连内的url与身份1或权限 可以从数据库读取
 * 修改权限时可以动态更新
 */
@Service
public class DynamicPermServiceImpl {
    //{0}为占位符存储拼接身份的参数roles[身份1，身份2] perms[1,2]就是里面参数
    //MessageFormat.format(ROLE_STRING,"身份1,身份2,身份3");
    public static final String PREMISSION_STRING = "perms[{0}]";
    private static final String ROLE_STRING="roles[{0}]";
    public Map<String,String> loadFilterChainDefinitions(ShiroFilterFactoryBean shiroFilterFactoryBean){
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/login", "anon");
        // filterChainDefinitionMap.put("/level1", "roles[2]");

        // 系统权限列表
        // filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

        Map<String, Filter> filters = new LinkedHashMap<>();
        //filters.put("cusfilter",cusShiroFilter());
        // 注销成功，则跳转到指定页面
        // filters.put("logout", logoutFilter());
        filters.put("cusFilter", SpringUtils.getBean("cusShiroFilter"));
        shiroFilterFactoryBean.setFilters(filters);
        // 所有请求需要认证
        filterChainDefinitionMap.put("/**", "user");
        return filterChainDefinitionMap;
    }


    /**
     * 更新初始化权限
     * @param shiroFilterFactoryBean
     */
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean){
        synchronized (this){
            try {
                AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
                PathMatchingFilterChainResolver resolver= (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
                DefaultFilterChainManager manager= (DefaultFilterChainManager) resolver.getFilterChainManager();
                //清空旧的权限
                manager.getFilterChains().clear();
                shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions(shiroFilterFactoryBean));
                Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
                if (!CollectionUtils.isEmpty(chains)) {
                    Iterator var12 = chains.entrySet().iterator();

                    while(var12.hasNext()) {
                        Map.Entry<String, String> entry = (Map.Entry)var12.next();
                        String url = (String)entry.getKey();
                        String chainDefinition = (String)entry.getValue();
                        manager.createChain(url, chainDefinition);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();;
            }
        }
    }

}
