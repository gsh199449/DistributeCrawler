#coding=utf8
'''
Created on 2013年12月15日

@author: GS
'''
import urllib
from urllib import urlopen
params = urllib.urlencode({'json': """{"year":2013,"month":12,"day":10,"date":20131210,"elecnum":100,"used":4,"inputelec":100}""", 'user': 'oo'})
f = urllib.urlopen("http://202.206.64.193/ElecWebService/CommitService", params)
#json="""{"year":2013,"month":12,"day":10,"date":20131210,"elecnum":100,"used":4,"inputelec":100}"""
#f = urlopen("""http://202.206.64.193/ElecWebService/CommitService?json=%s&user=gsh199449""" % json)
print f.getcode()
print f.read()
