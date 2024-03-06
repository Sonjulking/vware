package com.ggck.vware.eggset.repository;

import com.ggck.vware.community_post.entity.CommunityPostEntity;
import com.ggck.vware.eggset.entity.EggSetEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository는 기본적으로 Entity 클래스만 받아준다.
public interface EggSetRepository extends JpaRepository<EggSetEntity, Long> {

  Optional<EggSetEntity> findByPlayer(String playerName);

}
