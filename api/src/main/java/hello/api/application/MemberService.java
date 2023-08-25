package hello.api.application;

import hello.api.domain.Member;
import hello.api.domain.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
