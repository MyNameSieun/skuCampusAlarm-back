package com.example.skuCampusAlarm;

import com.example.skuCampusAlarm.repository.MemberRepository;
import com.example.skuCampusAlarm.repository.PostRepository;
import com.example.skuCampusAlarm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

}
