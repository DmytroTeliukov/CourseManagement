package com.dteliukov.model;

import com.dteliukov.enums.AnswerStatus;

public class Answer {
    private Long id;
    private Student student;
    private String sent;
    private String filePath;
    private Integer mark;
    private String ECTSMark;
    private AnswerStatus status;
}
