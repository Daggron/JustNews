package com.example.news;


public class news {

    private String date;
    private String title;
    private String tag;
    private String url;
    private String author;
    private String image;
    public news(String author,String date,String title,String tag,String url,String image){
        this.date=date;
        this.title=title;
        this.tag=tag;
        this.url=url;
        this.author=author;
        this.image=image;
    }


    public String getDate(){
        return date;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl(){
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }
}
