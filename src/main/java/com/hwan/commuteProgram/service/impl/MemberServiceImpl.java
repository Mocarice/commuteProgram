package com.hwan.commuteProgram.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.hwan.commuteProgram.service.MemberDAO;
import com.hwan.commuteProgram.service.MemberService;
import com.hwan.commuteProgram.service.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	MemberDAO memberDao;
	
	@Override
	public boolean loginCheck(MemberVO vo, HttpSession session) {
		boolean result = memberDao.loginCheck(vo);
		if (result) {
			MemberVO vo2 = viewMember(vo);
			
			session.setAttribute("userId", vo2.getUserId());
			session.setAttribute("userName", vo2.getUserName());
			session.setAttribute("role", vo2.getRole());
			session.setAttribute("ip", vo2.getIp());
		}
		return result;
	}
	
	@Override
	public MemberVO viewMember(MemberVO vo) {
		return memberDao.viewMember(vo);
	}

	@Override
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMember(MemberVO vo) {
		// TODO Auto-generated method stub
		memberDao.addMember(vo);
	}

	@Override
	public List<MemberVO> totalMember() {
		return memberDao.totalMember();
		
	}

	@Override
	public MemberVO oneMember(MemberVO vo) {
		return memberDao.oneMember(vo);
	}
	
}