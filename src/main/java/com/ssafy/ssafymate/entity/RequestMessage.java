package com.ssafy.ssafymate.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicInsert
public class RequestMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @NotNull
    private String type;

    @ColumnDefault("false")
    private Boolean readCheck;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    User sender;

    @NotNull
    @JsonBackReference
    @Column(name = "sender_id")
    private Long senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", insertable = false, updatable = false)
    User receiver;

    @NotNull
    @JsonBackReference
    @Column(name = "receiver_id")
    private Long receiverId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    Team team;

    @NotNull
    @JsonBackReference
    @Column(name = "team_id")
    private Long teamId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDateTime;

}
