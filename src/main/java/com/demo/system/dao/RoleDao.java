package com.demo.system.dao;
import com.demo.system.bean.Role;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * 
 * @author sdyang
 * @date 2016年1月24日 上午10:49:57
 */
@Component
public class RoleDao{
    @Autowired
    private SqlSession sqlSession;

    public Role findOne(Long pk_role){
        return this.sqlSession.selectOne("selectRoleById", pk_role);
    }

    public List<Role> findAll(){
        return this.sqlSession.selectList("selectAllRole");
    }

    public void insert(Role role){
        this.sqlSession.insert("insertRole",role);
    }

    public void update(Role role){
        this.sqlSession.update("updateRole",role);
    }

    public void delete(Long pk_role){
        this.sqlSession.delete("delete", pk_role);
    }

}
