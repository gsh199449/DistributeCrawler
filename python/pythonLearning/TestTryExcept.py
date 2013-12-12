#coding=utf8
'''
Created on 2013年12月12日

@author: GS
'''
try:
    f = open('D:\Test\9.txt')
except IOError,msg:
    print 'haha'
    raise IOError(msg)

    