package pe.com.qallarix.movistarcontigo.news.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class DataNews {
    private Message message;
    private NewsDetail news;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public NewsDetail getNews() {
        return news;
    }

    public void setNews(NewsDetail news) {
        this.news = news;
    }
}
