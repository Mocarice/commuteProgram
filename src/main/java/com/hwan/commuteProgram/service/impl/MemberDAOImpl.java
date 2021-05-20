package com.hwan.commuteProgram.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import com.hwan.commuteProgram.service.MemberDAO;
import com.hwan.commuteProgram.service.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{
	@Inject
	SqlSessionTemplate sqlSession;
	
	@Override
	public boolean loginCheck(MemberVO vo) {
		String name = sqlSession.selectOne("member.loginCheck",vo); //mapper.xml 쿼리문의 namespace.id를 뜻함
		return (name == null) ? false : true;
	}
	
	@Override
	public MemberVO viewMember(MemberVO vo) {
		return sqlSession.selectOne("member.viewMember", vo);
	}

	@Override
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addMember(MemberVO vo) {
		sqlSession.insert("member.addMember", vo);
	}
	
	@Override
	public List<MemberVO> totalMember() {
		return sqlSession.selectList("member.totalMember");
	}

	@Override
	public MemberVO oneMember(MemberVO vo) {
		return sqlSession.selectOne("member.oneMember", vo);
	}
	
}