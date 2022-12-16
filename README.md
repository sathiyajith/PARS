# PARS 

## Overview :red_envelope:

As the name suggests, PARS (Prometheus Alert Routing Simulation) is a tool used to simulate Prometheus alerts. This is built using JavaFx as a Desktop application and runs on wide variety of systems like Windows, macOS and Linux Operating systems.

## Prometheus Overview 

- Alertmanager handles alerts sent by client applications such as Prometheus server. The [Alertmanager configuration](https://prometheus.io/docs/alerting/latest/configuration/) consists of the Receivers and Routes definitions. 
- Receivers are various notification integrations like Slack, Pagerduty, Email, Webhooks etc. that receive notifications when an alert is triggered.
- Routes map the alerts to corresponding receivers based on key-value attributes called MatcherConditions.
- Alerts are defined in Thanos rule files with key-value attributes called Labels. These labels must match with the MatcherConditions to reach the correct receiver. If the labels do not match with any of the MatcherConditions, it is directed to the Default Receiver. Please refer to the below diagram for clarity.
- The frequency of the alert and other features like silencing, inhibition, grouping and repetition are also governed by the Alertmanager configuration.

## Working and Features :gear:

- This tool parses the config and rules files. (Ignores comments if there are any!)
- Validates the yaml files for any indentation errors.
- Identifies the type of route (PD/Slack/Webhook/Email)
- Provides a picture of how Alerts are mapped to Receivers.
- It can handle labels/MatcherConditions with regex too.
- Implemented "continue:true" and Default Receiver logic in the routing.

- Note: Deprecated features are not added in this tool. Features like silencing, inhibition that affect the frequency of alerts are considered as special cases and so ignored. Alert routes heirarchy is not handled so far.

## Architecture :world_map:

![Architecture Diagram](https://github.com/sathiyajith/PARS/blob/0211030ef769e271dbe7850482c826db7bb263c8/res/snippet_1.png)

## Instructions :closed_book:

1. Upload the Alertmanager Config file and click on submit.
2. Upload the first Thanos rules file and click on submit. After submitting plus button will be enabled to add additional files.
3. Click on Validate to verify whether the uploaded yaml files are valid.
4. Click on Visualize to view the set of receivers and routes.
5. Click on Map button to map receivers and routes.

## Few Instances of the tool :camera_flash:
<img align="left" width="48%" height="289" src="https://github.com/sathiyajith/PASV/blob/main/res/snippet_1.png">
<img align="center" width="48%" height="289" src="https://github.com/sathiyajith/PASV/blob/main/res/snippet_2.png">


<img align="left" width="48%" height="289" src="https://github.com/sathiyajith/PASV/blob/main/res/snippet_3.png">
<img align="center" width="48%" height="289" src="https://github.com/sathiyajith/PASV/blob/main/res/snippet_4.png">

##  HyperLinks :paperclip:
Video Link : [https://drive.google.com/open?id=1nfijHLwoez5a6ttDLaJIi4uxTz2ma1Ie]

If you wish to contribute to this project, you can go thru the [To-Do tasks](https://github.com/sathiyajith/PARS/blob/main/res/ToDo.txt) and ping me.

(July 31 - now)
