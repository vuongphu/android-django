from __future__ import absolute_import, unicode_literals
from celery import shared_task

from datetime import datetime, timedelta
import json
from .models import NameNews,TypesNews,RssLinks,Content
import requests
import lxml.html
import os
import time
from django.utils import timezone
from urlparse import urlparse
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36',
}
@shared_task
def request_rss(x, y):
    
    for i in RssLinks.objects.all(): 
        url=i.link_rss
        name=i.name_new.name

        if name == 'vnexpress.net':
            data=get_vnexpress(url)
        elif name == 'zing.vn':
            data=get_zing(url)
        elif name == 'tuoitre.vn':
            data=get_tuoitre(url)
        if data !=None:
            insertnews(data,i.name_new,i.type_new)
        
    return x + y
def get_vnexpress(url):
    timeformat=''
    dic={}
    count=0
    timeformat='%a, %d %b %Y %H:%M:%S +0700'
    r = requests.get(url,headers=headers)
    try:
        if r.status_code == 200:

            doc = lxml.html.fromstring(r.text.encode('utf-8'))

            items=doc.findall('.//item')
            # print r.text
            for g in items:
                try:
                    listitem={}
                    title = g.xpath(".//title/text()")
                    title= str(title[0].encode('utf-8'))
                    ahref = g.xpath(".//description/img/@src")[0]
                    pubDate=g.xpath(".//pubdate/text()")[0]
                    url = g.xpath(".//guid/text()")[0]
                    listitem={"pub_date":int(time.mktime(time.strptime(str(pubDate.strip()), timeformat))) ,"title":title,"src":ahref,"url":url}
                    dic[count]=listitem
                    count=count+1
                except:
                    None
            return dic
        else:
            return None
    except:
        return None
def get_zing(url):
    r = requests.get(url,headers=headers)
    doc = lxml.html.fromstring(r.text.encode('utf-8'))
    items=doc.findall('.//item')
    dic={}
    count=0
    timeformat='%m/%d/%Y %H:%M:%S %p'
    try:
        if r.status_code == 200:
            for g in items:
                try:
                    listitem={}
                    title = g.xpath(".//title/text()")
                    title= str(title[0].encode('utf-8'))
                    ahref = g.xpath(".//enclosure/@url")[0]
                    pubDate=g.xpath(".//pubdate/text()")[0]
                    url = g.xpath(".//link")[0].tail

                    listitem={"pub_date":int(time.mktime(time.strptime(str(pubDate.strip()), timeformat))) ,"title":title,"src":ahref,"url":url}
                    dic[count]=listitem
                    count=count+1
                except:
                    None
            return dic
        else:
            return None
    except:
        return None        

def get_tuoitre(url):
    return None
def insertnews(data,name,types):
    length=0
    array_link={}

    for key, value in data.iteritems():
        if (Content.objects.filter(url=value['url']).exists()==False):
            p = Content(title=value['title'],
                        url =value['url'],
                        timestamp_source = int(value['pub_date']) ,
                        pub_date=timezone.now(),
                        up_date=timezone.now(),
                        thumb_img=value['src'],
                        userupload=0,
                        type_new=types,
                        name_new=name)
            p.save()
            print "DONE"
        else:
            return False
    return True