package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {
	@Autowired MemberRepository memRepo;
	
}
