package Maswillaeng.MSLback.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private String id;

    @Column(name = "tag_name", unique = true)
    private String name;

    public Tag(String name) {
        this.name = name;
    }

}