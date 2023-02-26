package farm.board.service.member;

import farm.board.domain.member.Member;
import farm.board.dto.member.MemberDto;
import farm.board.exception.MemberNotFoundException;
import farm.board.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto read(Long id){
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new); //회원 조회
        return MemberDto.toDto(member); // 회원 데이터 전송
    }

    @Transactional
    public void delete(Long id){
        if(notExistsMember(id)) throw new MemberNotFoundException(); //회원이 없으면 예외 발생
        memberRepository.deleteById(id); //회원 삭제
    }

    private boolean notExistsMember(Long id){ // id에 해당하는 회원 정보가 존재하지 않는지 확인
        return !memberRepository.existsById(id);
    }
}
