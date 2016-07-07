package com.demo.system.dao;

import com.demo.system.bean.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * 
 * @author sdyang
 * @date 2016年2月16日 下午4:40:08
 */
@Component
public class ResourceDao {

    @Autowired
    private SqlSession sqlSession;

    public Resource findOne(Long pk_resource){
        return this.sqlSession.selectOne("selectResourceById", pk_resource);
    }

    public List<Resource> findAll(){
        return this.sqlSession.selectList("selectAllResource");
    }

    public void insert(Resource resource){
        this.sqlSession.insert("insertResource",resource);
    }

    public void update(Resource resource){
        this.sqlSession.update("updateResource",resource);
    }

    public void delete(Long pk_resource){
        this.sqlSession.delete("delete", pk_resource);
    }

}
