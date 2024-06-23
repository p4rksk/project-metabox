package org.example.metabox.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.metabox.book.Book;

@NoArgsConstructor
@Entity
@Data
@Table(name = "guest_tb")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "guest", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Book book;

    // 생년 월일
    private String birth;

    private String password;

    @Column(unique = true, nullable = false)
    private String phone;

    @Builder
    public Guest(Integer id, Book book, String birth, String password, String phone) {
        this.id = id;
        this.book = book;
        this.birth = birth;
        this.password = password;
        this.phone = phone;
    }
}