package kr.co.lotteOn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Member")
public class Member {

    @Id
    private String id;
    private String password;
    private String name;
    private String gender;
    private String email;
    private String hp;
    private String rating;
    private String role;
    private String zip;
    private String addr1;
    private String addr2;
    private String regDate;
    private String leaveDate;
}