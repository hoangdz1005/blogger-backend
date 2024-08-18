package com.bloggerbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue
    Long id;
    String token;
    String tokenType;
    LocalDateTime expiryDate;
    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;
}