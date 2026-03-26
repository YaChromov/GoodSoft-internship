package org.example.t5sr.dao.implMyBatis;


import org.example.t5sr.dao.RoleRepository;
import org.example.t5sr.dao.daoMapper.RoleDao;
import org.example.t5sr.model.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
@Primary
public class RoleRepositoryMyBatisImpl implements RoleRepository {

    private final RoleDao roleDao;

    public RoleRepositoryMyBatisImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role findByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return roleDao.findByName(name);
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public Set<Role> findRolesByNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Collections.emptySet();
        }
        return roleDao.findRolesByNames(names);
    }
}