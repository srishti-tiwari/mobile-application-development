/*
 * Assignment # : InClass05
 * File Name : 801092498_InClass05.zip
 * Name : Srishti Tiwari
 */
package uncc.com.inclass05;

import java.util.Date;
import java.util.Objects;

public class News {
    String title;
    String urlToImage;
    String description;
    Date publishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals( title, news.title ) &&
                Objects.equals( urlToImage, news.urlToImage ) &&
                Objects.equals( description, news.description ) &&
                Objects.equals( publishedAt, news.publishedAt );
    }

    @Override
    public int hashCode() {

        return Objects.hash( title, urlToImage, description, publishedAt );
    }


}
