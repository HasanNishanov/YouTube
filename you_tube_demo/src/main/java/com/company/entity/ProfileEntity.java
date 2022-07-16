package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile_")
@NoArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String tempEmail;

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private Boolean visible = Boolean.TRUE;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status = ProfileStatus.ACTIVE;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRole role = ProfileRole.ROLE_USER;


    @JoinColumn(name = "photo_id")
    @OneToOne(targetEntity = AttachEntity.class,fetch = FetchType.LAZY)
    private AttachEntity photo;

    public ProfileEntity(Integer id) {
        this.id = id;
    }
}
