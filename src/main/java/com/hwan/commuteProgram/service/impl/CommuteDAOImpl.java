package com.hwan.commuteProgram.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hwan.commuteProgram.service.CommuteDAO;
import com.hwan.commuteProgram.service.CommuteVO;

@Repository
public class CommuteDAOImpl implements CommuteDAO {

	
	@Inject
	private SqlSessionTemplate sqlSession;
	
	
	@Override
	public void inTime(CommuteVO vo) throws Exception {
		sqlSession.insert("commute.inTime_put", vo);
	}

	@Override
	public void outTime(CommuteVO vo) throws Exception {
		
		sqlSession.update("commute.outTime_put", vo);
		
	}

	@Override
	public void totalTime(CommuteVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("commute.totalTime_put", vo);
	}

	@Override
	public List<CommuteVO> commute(CommuteVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("commute.commuteTime");
	}
	
	@Override
	public void time_update(CommuteVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("commute.time_update", vo);
	}
	
	
}