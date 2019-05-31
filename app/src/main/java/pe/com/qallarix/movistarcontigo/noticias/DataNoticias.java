package pe.com.qallarix.movistarcontigo.noticias;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class DataNoticias {
    private Message message;
    private List<News> news;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<News> getNews() { return news; }
    public void setNews(List<News> value) { this.news = value; }


}
