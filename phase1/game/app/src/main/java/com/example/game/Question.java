package com.example.game;

/**
 * Question
 */
public class Question {

    /** Indicates the string of the question*/
    private String question;

    /** Indicates the string of the answers*/
    private String option1;
    private String option2;
    private String option3;

    /** Indicates the number of the answer key*/
    private int answerNR;

    /** Constructs a new question*/
    public Question(String question, String option1, String option2, String option3, int answerNR) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNR = answerNR;
    }

    /**
     * Return the question.
     *
     * @return the string of the question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Set question.
     *
     * @param question the string of the question.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Return the answer 1.
     *
     * @return the first answer.
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Set the answer 1.
     *
     * @param option1 the string of the first answer.
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     * Return the answer 2.
     *
     * @return the second answer.
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Set the answer 2.
     *
     * @param option2 the string of the second answer.
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }

    /**
     * Return the answer 2.
     *
     * @return the third answer.
     */
    public String getOption3() {
        return option3;
    }

    /**
     * Set the answer 3.
     *
     * @param option3 the string of the third answer.
     */
    public void setOption3(String option3) {
        this.option3 = option3;
    }

    /**
     * Return the answer key.
     *
     * @return the integer of the answer key.
     */
    public int getAnswerNR() {
        return answerNR;
    }

    /**
     * Set the answer key.
     *
     * @param answerNR the integer key.
     */
    public void setAnswerNR(int answerNR) {
        this.answerNR = answerNR;
    }
}
