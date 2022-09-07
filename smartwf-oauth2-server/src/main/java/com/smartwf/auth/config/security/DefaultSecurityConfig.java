package com.smartwf.auth.config.security;

import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.smartwf.auth.modules.service.AdminUserDetails;
import com.smartwf.auth.modules.service.UmsAdminService;
import com.smartwf.common.pojo.UmsAdmin;
import com.smartwf.common.pojo.UmsPermission;
import com.smartwf.common.service.IGlobalCache;

import lombok.SneakyThrows;

/**
 * <p>授权服务器安全策略</p>
 *
 * @author WCH
 * Date 2022/5/10
 * @version 1.0
 */
@EnableWebSecurity(debug = true)
public class DefaultSecurityConfig {
		
	@Autowired
	private UmsAdminService umsAdminService;
	
	@Autowired
	private IGlobalCache globalCache;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	/**
     * 配置 请求授权
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 配置 请求授权
        http.authorizeRequests(authorizeRequests ->
                // 任何请求都需要认证（不对未登录用户开放）
                authorizeRequests.anyRequest().authenticated()
            )
                // 表单登录
                .formLogin()
            .and()
                .logout()
            .and()
                .oauth2ResourceServer().jwt();
        
        return http.build();
    }
    
    /**
     * 自定义登录
     * 
     * */
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            UmsAdmin admin = umsAdminService.getAdminByUsername(username);
            if (admin != null) {
            	/**
                List<UmsPermission> permissionList = umsAdminService.getPermissionList(admin.getId());
                AdminUserDetails adminUserDetails = new AdminUserDetails(admin,permissionList);
                //redis保存信息
                globalCache.set(admin.getUsername(), adminUserDetails);
                //封装返回
    	        User user = new User(admin.getUsername(),admin.getPassword(), adminUserDetails.getAuthorities());
    	        return user;
                */
                
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		        //for (SysRole sysRole : sysRoles) {
		            //封装用户信息和角色信息 到 SecurityContextHolder全局缓存中
		            grantedAuthorities.add(new SimpleGrantedAuthority("zhangsan"));
		            grantedAuthorities.add(new SimpleGrantedAuthority("lisi"));
		        //}
                //封装返回
    	        User user = new User(admin.getUsername(),admin.getPassword(), grantedAuthorities);
    	        return user;
    	        
    	        
    	       //return  (UserDetails) new InMemoryUserDetailsManager(userDetails);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }
    

    /**
     * jwt解码器
     * 客户端认证授权后，需要访问user信息，解码器可以从令牌中解析出user信息
     *
     * @return
     */
    @SneakyThrows
    @Bean
    JwtDecoder jwtDecoder() {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
        // 读取cer公钥证书来配置解码器
        ClassPathResource resource = new ClassPathResource("myjks.cer");
        Certificate certificate = certificateFactory.generateCertificate(resource.getInputStream());
        RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
    
    

    /**
     * 开放一些端点的访问控制
     * 不需要认证就可以访问的端口
     * @return
     */
    //@Bean
/*    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/actuator/health", "/actuator/info");
    }*/
}
