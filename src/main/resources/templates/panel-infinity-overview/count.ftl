{
  "datasource": {
    "uid": "e407f775-8b38-4c67-b2c3-298086473afa",
    "type": "yesoreyeram-infinity-datasource"
  },
  "fieldConfig": {
    "defaults": {
      "mappings": [],
      "thresholds": {
        "mode": "absolute",
        "steps": [
          {
            "color": "red",
            "value": null
          },
          {
            "color": "green",
            "value": 1
          }
        ]
      },
      "color": {
        "mode": "thresholds"
      },
      "min": -1
    },
    "overrides": []
  },
  "gridPos": {
    "h": 8,
    "w": 12,
    "x": 0,
    "y": 0
  },
  "id": 2,
  "options": {
    "reduceOptions": {
      "values": true,
      "calcs": [
        "lastNotNull"
      ],
      "fields": "/.*/"
    },
    "orientation": "auto",
    "textMode": "auto",
    "wideLayout": true,
    "colorMode": "value",
    "graphMode": "area",
    "justifyMode": "auto"
  },
  "pluginVersion": "10.2.2",
  "targets": [
    {
      "datasource": {
        "type": "yesoreyeram-infinity-datasource",
        "uid": "b16ab68b-982a-48aa-9e85-1784182e6b78"
      },
      "columns": [],
      "data": "",
      "filters": [],
      "format": "table",
      "global_query_id": "",
      "parser": "backend",
      "refId": "A",
      "root_selector": "",
      "source": "url",
      "type": "json",
      "url": "${apiUrl}",
      "url_options": {
        "data": "",
        "method": "GET"
      }
    }
  ],
  "title": "${title}",
  "transformations": [],
  "type": "stat",
  "description": "${description}"
}