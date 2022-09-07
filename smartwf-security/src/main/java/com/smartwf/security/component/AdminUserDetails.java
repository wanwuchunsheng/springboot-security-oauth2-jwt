package com.smartwf.security.component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartwf.common.pojo.UmsAdmin;
import com.smartwf.common.pojo.UmsPermission;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminUserDetails implements UserDetails {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UmsAdmin umsAdmin;
    
    private List<UmsPermission> permissionList;
  
    public AdminUserDetails() {
    	
    }
    
    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) { 
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    /**
     * 
     * list.stream().map().collect()方法,可以获取list中JavaBean的某个字段,转成一个新的list
     * 
     * */
    //@JsonIgnore
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue()!=null)
                .map(permission ->new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    
    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    
    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }

	
}
