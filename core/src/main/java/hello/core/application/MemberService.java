package hello.core.application;

import hello.core.domain.Member;
import hello.core.domain.MemberRepository;
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
