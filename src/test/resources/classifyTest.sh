#!/bin/sh

./createClassifyJson.py test3.ttl > classify_this.json 
curl -v -X POST --data-binary @classify_this.json http://localhost:5000/services/cdp_jena/classify_this > results.json
./dumpResultTriples.py results.json > classified.ttl
