package com.tintuc.gilplus.tinnhanh;

/**
 * Created by Zenphone on 5/15/2017.
 */

public class ItemList {
    private String image;
    private String text;
    private int itemid;
    private String spantime;
    private String source;
    private String urlviewer;
    private int sumcomment;
    private long timesource;



    public ItemList(String image, String text,int itemid, String spantime,String source,int sumcomment, long timesource,String urlviewer) {
        this.setImage(image);
        this.text = text;
        this.itemid=itemid;
        this.spantime=spantime;
        this.source=source;
        this.sumcomment=sumcomment;
        this.timesource=timesource;
        this.setUrlviewer(urlviewer);
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getSpantime() {
        return spantime;
    }

    public void setSpantime(String spantime) {
        this.spantime = spantime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getSumcomment() {
        return sumcomment;
    }

    public void setSumcomment(int sumcomment) {
        this.sumcomment = sumcomment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getTimesource() {
        return timesource;
    }

    public void setTimesource(long timesource) {
        this.timesource = timesource;
    }

    public String getUrlviewer() {
        return urlviewer;
    }

    public void setUrlviewer(String urlviewer) {
        this.urlviewer = urlviewer;
    }
}
