package com.example.trader.Domain.Entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Document
public class TraderSideUser implements UserDetails{
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String password;
    private List<String> roles;
    private Map<Integer, BrokerSideUser> brokerSideUsers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
        if (roles != null)
            roles.stream().forEach(e -> {
                grantedAuthority.add(new SimpleGrantedAuthority(e));
            });
        return grantedAuthority;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<Integer, BrokerSideUser> getBrokerSideUsers() {
        return brokerSideUsers;
    }

    public void setBrokerSideUsers(Map<Integer, BrokerSideUser> brokerSideUsers) {
        this.brokerSideUsers = brokerSideUsers;
    }
}
