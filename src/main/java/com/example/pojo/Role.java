package com.example.pojo;

import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role",fetch=FetchType.EAGER)
    private Set<Admin> admins = new HashSet<>();


    // fetch必须加，不加执行测试类会异常，org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.bjsxt.pojo.Roles.menus, could not initialize proxy - no Session
    @ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    // @JoinTable：用来映射中间表
    // @JoinColumn用来维护一个外键,即所关联的中间表中的外键字段
    // inverseJoinColumns 中间表另一侧的外键字段
    @JoinTable(name = "t_role_menu",joinColumns = @JoinColumn(name = "role_id"),inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<Menu> menus = new HashSet<>();

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
