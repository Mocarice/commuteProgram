package com.hwan.commuteProgram.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public interface CommuteService {
	public void inTime(CommuteVO vo) throws Exception;
	
	public void outTime(CommuteVO vo) throws Exception;
	
	public void totalTime(CommuteVO vo) throws Exception;
	
	public List<CommuteVO> commute(CommuteVO vo) throws Exception;
	
	public void time_update(CommuteVO vo) throws Exception;

}