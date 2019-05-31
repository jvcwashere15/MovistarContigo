package pe.com.qallarix.movistarcontigo.autenticacion.pojos;


import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseToken {
    Message message;
    Token token;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
