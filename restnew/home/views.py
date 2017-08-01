# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
import time
import json
from .models import Content
from django.http import HttpResponse
from django.db.models import Q
# Create your views here.
def defaulttime(obj):
    """Default JSON serializer."""
    import calendar, datetime

    if isinstance(obj, datetime.datetime):
        if obj.utcoffset() is not None:
            obj = obj - obj.utcoffset()
        millis = int(
            calendar.timegm(obj.timetuple()) +
            obj.microsecond / 1000
        )
        return millis
    raise TypeError('Not sure how to serialize %s' % (obj,))
def index(request):

    data=json.dumps([dict(item) for item in Content.objects.values('id','pub_date' ,'title','name_new','thumb_img','url').order_by('-pub_date')[:11]],default=defaulttime)
    return HttpResponse( data, content_type='application/json')
def mobilecustom(request,typenew,timestamp):
    if int(typenew)==0:
        data=json.dumps([dict(item) for item in Content.objects.values('id','pub_date' ,'name_new','title','name_new__name','thumb_img','timestamp_source','url').filter(timestamp_source__lte=timestamp).order_by('-timestamp_source')[:11]],default=defaulttime)
    else:
        data=json.dumps([dict(item) for item in Content.objects.values('id','pub_date' ,'title','name_new','name_new__name','thumb_img','timestamp_source','url').filter(type_new=typenew,timestamp_source__lte=timestamp).order_by('-timestamp_source')[:11]],default=defaulttime)

    return HttpResponse( data, content_type='application/json')
def mobilemoreitem(request,typenew,timestamp):
    if int(typenew)==0:
        data=json.dumps([dict(item) for item in Content.objects.filter(timestamp_source__lte=timestamp).values('id','pub_date' ,'title','name_new','name_new__name','thumb_img','timestamp_source','url').order_by('-timestamp_source')[:11]],default=defaulttime)
    else:
        data=json.dumps([dict(item) for item in Content.objects.filter(timestamp_source__lte=timestamp,type_new=typenew).values('id','pub_date' ,'title','name_new','name_new__name','thumb_img','timestamp_source','url').order_by('-timestamp_source')[:11]],default=defaulttime)

    return HttpResponse( data, content_type='application/json')
def mobilefilter(request,typenew,timestamp,filtercustom):
    my_filter_qs = Q()
    sd_list = [int(x) for x in filtercustom.split(',')]
    qs= Content.objects.all()
    for s in sd_list:
        my_filter_qs = my_filter_qs | Q(name_new_id=s)
        
    qs=qs.filter(my_filter_qs)
    if int(typenew)==0:
        data=json.dumps([dict(item) for item in qs.values('id','pub_date' ,'name_new','title','name_new__name','thumb_img','timestamp_source','url').filter(timestamp_source__lte=timestamp).order_by('-timestamp_source')[:11]],default=defaulttime)
    else:
        data=json.dumps([dict(item) for item in qs.values('id','pub_date' ,'title','name_new','name_new__name','thumb_img','timestamp_source','url').filter(type_new=typenew,timestamp_source__lte=timestamp).order_by('-timestamp_source')[:11]],default=defaulttime)

    return HttpResponse( data, content_type='application/json')
def mobilefiltermore(request,typenew,timestamp,filtercustom):
    my_filter_qs = Q()
    sd_list = [int(x) for x in filtercustom.split(',')]
    qs= Content.objects.all()
    for s in sd_list:
        my_filter_qs = my_filter_qs | Q(name_new_id=s)
        
    qs=qs.filter(my_filter_qs)
    if int(typenew)==0:
        data=json.dumps([dict(item) for item in qs.filter(timestamp_source__lte=timestamp).values('id','pub_date' ,'title','name_new','name_new__name','thumb_img','timestamp_source','url').order_by('-timestamp_source')[:11]],default=defaulttime)
    else:
        data=json.dumps([dict(item) for item in qs.filter(timestamp_source__lte=timestamp,type_new=typenew).values('id','pub_date' ,'title','name_new','name_new__name','thumb_img','timestamp_source','url').order_by('-timestamp_source')[:11]],default=defaulttime)


    return HttpResponse( data, content_type='application/json')

