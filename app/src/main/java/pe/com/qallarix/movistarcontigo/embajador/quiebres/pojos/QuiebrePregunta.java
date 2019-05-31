package pe.com.qallarix.movistarcontigo.embajador.quiebres.pojos;

import java.util.List;

public class QuiebrePregunta {
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
