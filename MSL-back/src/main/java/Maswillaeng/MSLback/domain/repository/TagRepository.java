package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {


    @Query("select t from Tag t where t.name in :tagList")
    List<Tag> findByNameList(@Param("tagList") List<String> tagList);

}