package com.ibs.dockerbacked.config;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局配置
 * @author Chajian
 * @date 2020/9/17
 */
@Component
@Configuration
public class AppServiceConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ContainerHandler());
    }


//    public FilterRegistrationBean<Filter> filterFilterRegistrationBean(){
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
//        filterRegistrationBean.setFilter((Filter) shiro);
//        filterRegistrationBean.addInitParameter("targetFilterLifecycle", "true");
//        filterRegistrationBean.setAsyncSupported(true);
//        filterRegistrationBean.setEnabled(true);
//        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
//        return filterRegistrationBean
//    }

    /**
     * 普通的身份鉴权和验证realm
     * @return
     */
    @Bean
    public AuthorizeFilterShiro AuthorizeFilterShiro(){
        return new AuthorizeFilterShiro();
    }

    /**
     * 设置jwt认证bean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt",new AuthenFilterJwt());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        Map<String,String> filterRuleMap = new HashMap<>();

        filterRuleMap.put("/ibs/api/verify/**","anon");
        filterRuleMap.put("/404","anon");//404
        filterRuleMap.put("/**", "jwt");
//        filterRuleMap.put("/ibs/api//**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 设置shiro授权bean
     * @param authenrizationFilter
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(AuthorizeFilterShiro authenrizationFilter){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authenrizationFilter);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }



}
