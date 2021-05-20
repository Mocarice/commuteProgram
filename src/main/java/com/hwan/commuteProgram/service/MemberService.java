package com.hwan.commuteProgram.service;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface MemberService {
	public boolean loginCheck(MemberVO vo, HttpSession session);
	
	public MemberVO viewMember(MemberVO vo);
	
	public void logout(HttpSession session);
	
	public void addMember(MemberVO vo);
	
	public List<MemberVO> totalMember();
	
	public MemberVO oneMember(MemberVO vo);
}