# PASV  :star:

## Overview

As the name suggests, PASV (Prometheus Alert Simulation and Visualization) is a tool used to simulate and visualize Prometheus alerts. This is built using JavaFx as a Desktop application and runs on wide variety of systems like Windows, macOS and Linux Operating systems.

## Working :gear:
- Alertmanager handles alerts sent by client applications such as Prometheus server. The Alertmanager configuration consists of the Receivers and Routes definitions. 
- Receivers are the various notification integrations like Slack, Pagerduty, Email, Webhooks etc. that receive notifications when an alert is sent.
- Routes map the alerts to corresponding receivers based on key-value pairs called MatcherConditions.
- Alerts are defined in several Thanos rule files with key-value pairs called Labels. These labels must match with the MatcherConditions to reach the correct receiver. If the labels do not match with any of the MatcherConditions, it is directed to the Default Receiver. Please refer to the below diagram for clarity.
- The frequency of the alert and other features like silencing, inhibition, grouping and repetition are also governed by the Alertmanager configuration.
- This tool simulates few functionalities of Alertmanager and provides a basic picture of how Alerts are mapped to Receivers.

<img align="center" width="555" height="570" src="https://github.com/sathiyajith/PASV/blob/main/res/Architecture%20Diagram.png">

## Instructions :closed_book:

1. Select Upload from the Dashboard and Upload the Alertmanager Config file and Thanos rules file in Upload Page.
2. Click on Visualize to view the set of receivers and routes.
3. Click on Map button to map receivers and routes.

## Few Instances of the tool
<img align="left" width="480" height="289" src="https://github.com/sathiyajith/PASV/blob/main/res/snippet_1.png">
<img align="center" width="480" height="289" src="https://github.com/sathiyajith/PASV/blob/main/res/snippet_2.png">

##  HyperLinks :paperclip:
Video Link : [https://drive.google.com/open?id=1nfijHLwoez5a6ttDLaJIi4uxTz2ma1Ie]

(July 31 - now)
