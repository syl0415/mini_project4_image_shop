package com.aegis.image_shop.repository;

import java.util.List;

import com.aegis.image_shop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>{

    public List<Member> findByUserId(String userId);

    @Query("SELECT m.userNo, m.userId, m.userPw, m.userName, cd.codeName, m.coin, m.regDate "
            + "FROM Member m "
            + "INNER JOIN CodeDetail cd ON cd.codeValue = m.job "
            + "INNER JOIN CodeGroup cg ON cg.groupCode = cd.groupCode "
            + "WHERE cg.groupCode = 'A01' ORDER BY m.regDate DESC")
    public List<Object[]> listAllMember();

}