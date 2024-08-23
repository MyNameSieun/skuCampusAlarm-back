package com.example.skuCampusAlarm.service;

import com.example.skuCampusAlarm.domain.Member;
import com.example.skuCampusAlarm.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
            vaildateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
    }

    private void vaildateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    public List<Member> findMembers(){
            return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
    public Optional<Member> login(String email, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            Member foundMember = member.get();
            String decodedPassword = new String(Base64.getDecoder().decode(foundMember.getPassword1()));
            if (decodedPassword.equals(password)) {
                return member;
            }
        }
        return Optional.empty();
    }
    public boolean isEmailAlreadyRegistered(String email) {
        Optional<Member> existingMember = memberRepository.findByEmail(email);
        return existingMember.isPresent();
    }
    public boolean isNicknameAlreadyRegistered(String nickname) {
        Optional<Member> existingMember = memberRepository.findByNickname(nickname);
        return existingMember.isPresent();
    }
    public boolean isNicknameAlreadyUsed(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }
    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }


        // other methods...


}
