package org.example.t5s.dao.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.t5s.model.Role;

import java.util.List;
import java.util.Set;

@Mapper
public interface RoleDao {

    Role findByName(String name);
    Set<Role> findAll();
    Set<Role> findRolesByNames(@Param("names") List<String> names);
    Set<Role> findRolesByUserLogin(String login);

}