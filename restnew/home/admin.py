# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin

# Register your models here.
from .models import NameNews,TypesNews,RssLinks,Content
admin.site.register(NameNews)
admin.site.register(TypesNews)
admin.site.register(RssLinks)
admin.site.register(Content)
