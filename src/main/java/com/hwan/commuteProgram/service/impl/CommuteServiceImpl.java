package com.hwan.commuteProgram.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.hwan.commuteProgram.service.CommuteDAO;
import com.hwan.commuteProgram.service.CommuteService;
import com.hwan.commuteProgram.service.CommuteVO;


@Service("CommuteService")
public class CommuteServiceImpl implements CommuteService {
	
	@Inject
	private CommuteDAO dao;
	
	@Override
	public void inTime(CommuteVO vo) throws Exception {
		System.out.println("여기까지완료1");
		dao.inTime(vo);
		
	}
	public void inTime() throws Exception {
		
	}

	@Override
	public void outTime(CommuteVO vo) throws Exception {
		dao.outTime(vo);
	}

	@Override
	public void totalTime(CommuteVO vo) throws Exception {
		dao.totalTime(vo);
	}

	@Override
	public List<CommuteVO> commute(CommuteVO vo) throws Exception {
		return dao.commute(vo);

	}
	
	@Override
	public void time_update(CommuteVO vo) throws Exception {
		dao.time_update(vo);
	}
	
}