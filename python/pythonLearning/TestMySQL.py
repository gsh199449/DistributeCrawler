#coding=utf8
'''
Created on 2013年12月12日

@author: GS
'''

'''
详细的mysql操作见:
    http://dev.mysql.com/doc/connector-python/en/connector-python-examples.html
'''
import mysql.connector
cnx = mysql.connector.connect(user='root', password='940409',
                              host='127.0.0.1',
                              database='python')

