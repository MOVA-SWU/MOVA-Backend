package com.example.mova.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    PEACE("평화"),
    HUMAN_RIGHTS("인권"),
    POVERTY("빈곤"),
    EDUCATION("교육"),
    ENVIRONMENT("환경"),
    SCIENCE("과학"),
    HEALTH("건강"),
    COMMUNITY("커뮤니티"),
    ETHICS("윤리");

    private final String label;

    Category(String label){
        this.label = label;
    }

    //JSON 직렬화 시 한글 레이블을 사용
    @JsonValue
    public String getLabel(){
        return label;
    }

    //JSON 표현 -> 객체 (역직렬화)
    @JsonCreator
    public static Category fromLabel(String label){
        for (Category category : values()){
            if (category.label.equals(label)){
                return category;
            }
        }
        throw new IllegalArgumentException(("유효하지 않은 카테고리입니다." + label));
    }
}

