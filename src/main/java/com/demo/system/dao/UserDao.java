package com.demo.system.dao;

import com.demo.system.bean.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * 
 * @author sdyang
 * @date 2016年1月23日 下午5:42:27
 */
@Component
public class UserDao{

	@Autowired
	private SqlSession sqlSession;

	public User findOne(Long pk_user){
		return this.sqlSession.selectOne("selectUserById", pk_user);
	}

	public List<User> findAll(){
		return this.sqlSession.selectList("selectAllUser");
	}

	public void insert(User user){
		 this.sqlSession.insert("insertUser",user);
	}

	public void update(User user){
		this.sqlSession.update("updateUser",user);
	}

	public void delete(Long pk_user){
		 this.sqlSession.delete("delete", pk_user);
	}

	// 根据用户登录帐号查询用户信息
	public User findByLoginid(String loginid) {
		return this.sqlSession.selectOne("selectUserByLoginid", loginid);
	}

}
