package com.example.trader.Domain.Entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Document
public class TraderSideUser implements UserDetails{
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private List<String> roles = new ArrayList<>();
    private Map<String, BrokerSideUser> brokerSideUsers = new HashMap<>();

    @JSONField(serialize = false)
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

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JSONField(serialize = false)
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

    public Map<String, BrokerSideUser> getBrokerSideUsers() {
        return brokerSideUsers;
    }

    public void setBrokerSideUsers(Map<String, BrokerSideUser> brokerSideUsers) {
        this.brokerSideUsers = brokerSideUsers;
    }

    public BrokerSideUser addBrokerSideUser(BrokerSideUser brokerSideUser){
        return brokerSideUsers.put(brokerSideUser.getBrokerId().toString(), brokerSideUser);
    }

    public BrokerSideUser removeBrokerSideUser(Integer bid){
        return brokerSideUsers.remove(bid.toString());
    }

    public BrokerSideUser modifyBrokerSideUser(BrokerSideUser brokerSideUser){
        if (brokerSideUsers.get(brokerSideUser.getBrokerId()) == null)
            return null;
        return brokerSideUsers.put(brokerSideUser.getBrokerId().toString(), brokerSideUser);
    }

    public BrokerSideUser getBrokerSideUser(Integer bid){
        return brokerSideUsers.get(bid.toString());
    }
}
