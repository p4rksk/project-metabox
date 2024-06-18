package org.example.metabox.payment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "payment_tb")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int price;
    private String theater_name;
    private String movie_name;
    
    // 결제일
    @CreationTimestamp
    private Timestamp createdAt;
}
