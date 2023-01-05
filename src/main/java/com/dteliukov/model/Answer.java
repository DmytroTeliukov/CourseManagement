package com.dteliukov.model;

import com.dteliukov.enums.AnswerStatus;
import com.dteliukov.review.AnswerRecord;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Answer {
    @SerializedName("answer_id")
    private Long id;
    @SerializedName("student")
    private User student;
    @SerializedName("sent")
    private String sent;
    @SerializedName("checked")
    private String checked;
    @SerializedName("comment")
    private String comment;
    @SerializedName("path")
    private String filePath;
    @SerializedName("mark")
    private Integer mark;
    @SerializedName("ects_mark")
    private String ECTSMark;
    @SerializedName("status")
    private AnswerStatus status;

    public Answer() {}

    public Answer(Long id, User student, String sent, String checked, String comment,
                  String filePath, Integer mark, String ECTSMark, AnswerStatus status) {
        this.id = id;
        this.student = student;
        this.sent = sent;
        this.checked = checked;
        this.comment = comment;
        this.filePath = filePath;
        this.mark = mark;
        this.ECTSMark = ECTSMark;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Answer id(Long id) {
        this.id = id;
        return this;
    }

    public User getStudent() {
        return student;
    }

    public Answer student(User student) {
        this.student = student;
        return this;
    }

    public String getSent() {
        return sent;
    }

    public Answer sent(String sent) {
        this.sent = sent;
        return this;
    }

    public String getChecked() {
        return checked;
    }

    public Answer checked(String checked) {
        this.checked = checked;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Answer comment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public Answer filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public Integer getMark() {
        return mark;
    }

    public Answer mark(Integer mark) {
        this.mark = mark;
        return this;
    }

    public String getECTSMark() {
        return ECTSMark;
    }

    public Answer ectsMark(String ECTSMark) {
        this.ECTSMark = ECTSMark;
        return this;
    }

    public AnswerStatus getStatus() {
        return status;
    }

    public Answer status(AnswerStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(student.email, answer.student.email) &&
                Objects.equals(sent, answer.sent) &&
                Objects.equals(checked, answer.checked) &&
                Objects.equals(comment, answer.comment) &&
                Objects.equals(filePath, answer.filePath) &&
                Objects.equals(mark, answer.mark) &&
                Objects.equals(ECTSMark, answer.ECTSMark) &&
                status == answer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, sent, checked, comment, filePath, mark, ECTSMark, status);
    }

    public Answer clone() {
        return new Answer(id, student, sent, checked, comment, filePath, mark, ECTSMark, status);
    }

    public AnswerRecord create(){
        return new AnswerRecord(id, student, sent, checked, comment, filePath, mark, ECTSMark, status);
    }
    public void restore(AnswerRecord memento){
        this.id = memento.id();
        this.student = memento.student();
        this.sent = memento.sent();
        this.checked = memento.checked();
        this.comment = memento.comment();
        this.filePath = memento.filePath();
        this.mark = memento.mark();
        this.ECTSMark = memento.ECTSMark();
        this.status = memento.status();
    }
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
