package com.example.amar.ads_newsapp_2.Model;

/**
 * Created by Amar on 6/29/2017.
 */

public class NewsItemClass {


    //https://newsapi.org/#documentation
/*
status	(string) - If the request was successful or not. Options: ok, error. In the case of error a code and message property will be populated.
    source	(string) - The identifier of the source requested.
    sortBy	(string) - Which type of article list is being returned. Options: top, latest, popular.[ we are using by defult Latest as sortByForm]
    articles	(array) The list of headline metadata requested.
    {       author	(string) - The author of the article.
            description(string) - A description or preface for the article.
            title	(string) - The headline or title of the article.
            url	(string) - The direct URL to the content page of the article.
            urlToImage	(string) - The URL to a relevant image for the article.
            publishedAt	(string) - The best attempt at finding a date for the article.
    }
*/
// Variable Declaration
    String nSource;
    String nAuthor;
    String nTitle;
    String nDescription;
    String nUrl;
    String nUrlToImage;
    String nPublishedAt;

    //Constructor
    public NewsItemClass(String source, String author, String title, String description, String url, String urlToImage, String publishedAt)
    {
        this.nSource = source;
        this.nAuthor = author;
        this.nTitle = title;
        this.nDescription = description;
        this.nUrl = url;
        this.nUrlToImage = urlToImage;
        this.nPublishedAt = publishedAt;

    }
//Getters and setters

    public String getnAuthor() {
        return nAuthor;
    }

    public void setnAuthor(String nAuthor) {
        this.nAuthor = nAuthor;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnDescription() {
        return nDescription;
    }

    public void setnDescription(String nDescription) {
        this.nDescription = nDescription;
    }

    public String getnUrl() {
        return nUrl;
    }

    public void setnUrl(String nUrl) {
        this.nUrl = nUrl;
    }

    public String getnUrlToImage() {
        return nUrlToImage;
    }

    public void setnUrlToImage(String nUrlToImage) {
        this.nUrlToImage = nUrlToImage;
    }

    public String getnPublishedAt() {
        return nPublishedAt;
    }

    public void setnPublishedAt(String nPublishedAt) {
        this.nPublishedAt = nPublishedAt;
    }





}
