#!/usr/bin/env python
import sys
import simplejson as json

d = json.loads(open(sys.argv[1]).read())
print d['triples'].encode("utf-8")
