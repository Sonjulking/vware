package com.ggck.vware.community.entity;


import com.ggck.vware.user.entity.SiteUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Community_Board")
public class CommunityPostEntity {

  @Id
  @SequenceGenerator(name = "post_sequence_generator", sequenceName = "post_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "post_sequence_generator")
  @Column(unique = true)
  private Integer postNumber; //게시글 넘버
  @Column(length = 200)
  private String subject;
  @Column(columnDefinition = "TEXT")
  private String content;
  private LocalDateTime createTime;

  private boolean deleteStatus = false;
  private LocalDateTime deleteTime;

  @ManyToOne
  private SiteUserEntity author;
  private LocalDateTime modifyTime;

}
