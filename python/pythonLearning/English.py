#coding=utf8
'''
Created on 2013年12月15日

@author: GS
'''
import urllib
from urllib import urlopen
params = urllib.urlencode({'username':'120702205','password':'940409'})
f = urllib.urlopen("http://202.206.64.197/index.php", params)
#json="""{"year":2013,"month":12,"day":10,"date":20131210,"elecnum":100,"used":4,"inputelec":100}"""
#f = urlopen("""http://202.206.64.193/ElecWebService/CommitService?json=%s&user=gsh199449""" % json)
print f.getcode()
print f.read()