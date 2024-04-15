package com.example.dianyinggoupiao_springboot.repositroy;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema,Integer> {
    @Override
    Optional<Cinema> findById(Integer id);

    /**
     * 根据影院名称查询影院id
     * @param name 影院名称
     * @return 影院id
     */
    Optional<Cinema> findByName(String name);



    @Transactional
    @Modifying
    @Query("update Cinema c set c.avatarId = :avatarId where c.cinemaId = :id")
    void updateAvatarIdById(@Param("id") int id, @Param("avatarId") String avatarId);

}
