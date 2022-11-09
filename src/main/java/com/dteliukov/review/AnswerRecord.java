package com.dteliukov.review;

import com.dteliukov.enums.AnswerStatus;
import com.dteliukov.model.User;

public record AnswerRecord(Long id, User student, String sent, String checked, String comment, String filePath,
                           Integer mark, String ECTSMark, AnswerStatus status) {

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", student=" + student +
                ", sent='" + sent + '\'' +
                ", checked='" + checked + '\'' +
                ", comment='" + comment + '\'' +
                ", filePath='" + filePath + '\'' +
                ", mark=" + mark +
                ", ECTSMark='" + ECTSMark + '\'' +
                ", status=" + status +
                '}';
    }
}
