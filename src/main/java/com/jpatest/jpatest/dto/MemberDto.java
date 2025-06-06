package com.jpatest.jpatest.dto;

import com.jpatest.jpatest.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private String email;
    private String tel;


    private static ModelMapper modelMapper = new ModelMapper();

    static{
        modelMapper.typeMap(MemberDto.class, Member.class)
                .addMappings(mapper ->{
                    mapper.skip(Member::setId); // Dto -> entity와 변수가 다른경우 매핑 제외
                });
    }


    // entity -> dto
    public static MemberDto of(Member member){
        return modelMapper.map(member, MemberDto.class);
    }

    //dto -> entity
    // modelMapper.map(앞,뒤) > 앞쪽에 있는
    public Member createMember(){
        return modelMapper.map(this, Member.class);
    }
}

// @Column( name = "user_id" )
// @Column( length = 50 ) varchar(50)설정
// @Column( naullable = false) null을 허용하지 않는다
