package com.example.mova.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApiExceptions {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class MovieRecordNotFoundException extends  RuntimeException{
        public MovieRecordNotFoundException(Long movieRecordId){
            super("해당 영화기록 내용을 찾을 수 없습니다. movieRecordId =" + movieRecordId);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class MyMissionNotFoundException extends RuntimeException{
        public MyMissionNotFoundException(Long myMissionId){
            super("해당 미션을 찾을 수 없습니다. myMissionId = " + myMissionId);
        }
    }
}
