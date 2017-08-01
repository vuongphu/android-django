from django.conf.urls import url

from . import views
app_name = 'home'

urlpatterns = [
	url(r'^$', views.index, name='index'),
    url(r'^mobile/(?P<typenew>[0-9]+)/(?P<timestamp>[0-9]+)$', views.mobilecustom, name='pagetype'),
    url(r'^mobile/(?P<typenew>[0-9]+)/t=(?P<timestamp>[0-9]+)/$', views.mobilemoreitem, name='pagetype'),
    url(r'^mobile/(?P<typenew>[0-9]+)/(?P<timestamp>[0-9]+)/filter=(?P<filtercustom>.+)/$', views.mobilefilter, name='pagetype'),
    url(r'^mobile/(?P<typenew>[0-9]+)/t=(?P<timestamp>[0-9]+)/filter=(?P<filtercustom>.+)/$', views.mobilefiltermore, name='pagetype'),
]