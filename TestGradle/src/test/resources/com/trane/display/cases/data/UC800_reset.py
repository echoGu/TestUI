#!/usr/bin/python
# UC800_reset.py

import sys, httplib

errCode = 0

httpConn = httplib.HTTPConnection('192.168.1.3', 80)

httpConn.request('POST', '/evox/hardware/reset', '<int val="60878720"/>')
httpResp = httpConn.getresponse()
if (httpResp.status != 200 or httpResp.read().find('<err') >= 0):
    errCode = 1

httpConn.close()
sys.exit(errCode)
