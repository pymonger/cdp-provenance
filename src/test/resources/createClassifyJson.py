#!/usr/bin/env python
import sys
import simplejson as json

d = {}
d['schema'] = open('opmo_es.rdf').read()
d['triples'] = open(sys.argv[1]).read()
print json.dumps(d, indent=2)
