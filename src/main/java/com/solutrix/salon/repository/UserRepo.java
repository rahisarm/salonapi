package com.solutrix.salon.repository;


import com.solutrix.salon.dto.UserDTO;
import com.solutrix.salon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "SELECT COALESCE(MAX(doc_no), 0) FROM my_user", nativeQuery = true)
    Optional<Integer> findMaxDocNo();

    @Query(value = "SELECT COALESCE(MAX(voc_no), 0) FROM my_user where brhid=:brhid", nativeQuery = true)
    Optional<Integer> findMaxVocNo(@Param("brhid") int brhid);

    Optional<User> findByUsername(String username);

    @Query("SELECT new com.solutrix.salon.dto.UserDTO(u.docno, u.username, u.fullname, u.email, u.mobile, u.roleid, ul.userlevel) " +
            "FROM User u " +
            "JOIN Userlevel ul ON u.roleid = ul.docno " +
            "WHERE u.brhid = :brhid AND u.status <> 7")
    List<UserDTO> fetchAllUsersByBranch(@Param("brhid") int brhid);
}
