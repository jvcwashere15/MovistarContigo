package pe.com.qallarix.movistarcontigo.ambassador.breaks.pojos;

import java.util.List;

public class QuestionBreak {
    private Message message;
    private List<Question> questions;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
