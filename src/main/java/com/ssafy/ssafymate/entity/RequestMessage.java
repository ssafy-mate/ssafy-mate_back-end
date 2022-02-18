package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class RequestMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String message;

    @NotNull
    @Column(length = 20)
    private String type;

    @ColumnDefault("'pending'")
    @Column(length = 20)
    private String requestStatus;

    @NotNull
    @Column(length = 20)
    private String project;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    User sender;

    @NotNull
    @Column(name = "sender_id")
    private Long senderId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", insertable = false, updatable = false)
    User receiver;

    @NotNull
    @Column(name = "receiver_id")
    private Long receiverId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    Team team;

    @NotNull
    @Column(name = "team_id")
    private Long teamId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @ColumnDefault("true")
    @Column(length = 20)
    private Boolean senderRead;

    @ColumnDefault("true")
    @Column(length = 20)
    private Boolean receiverRead;

}
