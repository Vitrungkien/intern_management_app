package com.example.apidemo.security;

import com.example.apidemo.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String userName;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

//    public CustomUserDetails(Long id, String userName, String email, String password, List<GrantedAuthority> listAuthorities) {
//    }

    //Tu thong tin User chuyen sang thong tin CustomUserDetails
    public static CustomUserDetails mapUserToUserDetails(User user) {
        //Lay cac quyen tu doi tuong User
        List<GrantedAuthority> listAuthorities = user.getListRole().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                .collect(Collectors.toList());
        //Tra ve doi tuong CustomUserDetails
        return new CustomUserDetails(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                listAuthorities
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return true;
    }
}
