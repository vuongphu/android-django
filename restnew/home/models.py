# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from django.utils import timezone
import datetime
# Create your models here.
class NameNews(models.Model):

    name = models.TextField()
    class Meta:
         verbose_name = "News Name"
    def __unicode__(self): #chu y
        return self.name
class TypesNews(models.Model):

    typenews = models.TextField()
    class Meta:
         verbose_name = "Types News"

    def __unicode__(self): #chu y
        return self.typenews

class RssLinks(models.Model):
    name_new=models.ForeignKey(NameNews)
    type_new=models.ForeignKey(TypesNews)
    # typenew=models.ForeignKey(T04, on_delete=models.CASCADE,related_name="typenew")
    link_rss=models.CharField(max_length=100,blank=True)
    
    tag_header=models.CharField(max_length=100,blank=True)
    type_header=models.CharField(max_length=100,blank=True)
    name_header=models.CharField(max_length=100,blank=True)

    tag_content=models.CharField(max_length=100,blank=True)
    type_content=models.CharField(max_length=100,blank=True)
    name_content=models.CharField(max_length=100,blank=True)

    tag_keyword=models.CharField(max_length=100,blank=True)
    type_keyword=models.CharField(max_length=100,blank=True)
    name_keyword=models.CharField(max_length=100,blank=True)
    class Meta:
         verbose_name = "Rss Links"
class Content(models.Model): # thoi su
    name_new=models.ForeignKey(NameNews)
    type_new=models.ForeignKey(TypesNews)
    title = models.CharField(max_length=200)
    url= models.CharField(max_length=250)
    pub_date = models.DateTimeField('date published')
    up_date = models.DateTimeField('date published')
    time_source = models.DateTimeField(null=True)
    yes_short=models.BooleanField(default=False)
    timestamp_source =models.IntegerField(null=True)
    content = models.TextField(blank=True)
    keyword = models.CharField(max_length=300,blank=True)
    head=models.CharField(max_length=300,blank=True)
    thumb_img=models.CharField(max_length=150,blank=True)
    viewoneday=models.IntegerField(default=0)
    viewweek=models.IntegerField(default=0)   
    viewmonth=models.IntegerField(default=0)   
    views_all=models.IntegerField(default=0)
    useruploads= (
        ('0', 'AdminAuto'),
        ('1', 'SuperAdmin'),
        ('2', 'Mod'),
        ('3', 'User')
    )
    userupload = models.CharField(max_length=2, choices=useruploads)
     
    class Meta:
         verbose_name = "Spaper Content"
    def plushid(self):
        return self.id+1
    def subtract(self):
        return self.id-1
    def __unicode__(self): #chu y
        return self.title

