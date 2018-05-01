# Full FCM Push/Pull Notification
### Featured In : [![](https://jitpack.io/v/MustafaGamalAbbas/Full_FCM_Push-Pull_Notification.svg)](https://jitpack.io/#MustafaGamalAbbas/Full_FCM_Push-Pull_Notification)

![Image](img/firebase_.png)
### Index 
* [Overview](#overview)
* [Features](#features)
* [Prerequisites](#prerequisites)
* [Usage](#usage)
* [Notes](#notes)
* [License](#license)


## Overview
FCM push/pull notification is a library that facilitate interaction with firebase could messaging either for pushing notification or receiving messages , and it also facilitate grouping people on topic or channel 
## Features
* Push notification to group of peaple subscribed to some topic or them tokens 
* Receive Firebase push messages 
* Subscribe or unsubscribe user device to topic or list of topics 
* Ability to build and launch notification (foregournd)
* Custom notifaction sound at foreground,background and killed status 
* Ability to send extra data with pushing notification 

## Prerequisites
* **Connect To Firebase** 
	* **1. Manually**
			 connect to exist project or create new project,
			 you can check out the [project Setting up](https://console.firebase.google.com/)
 	* **2. Automatically**
			 check out [Connect to Firebase using android studio](https://developer.android.com/studio/write/firebase)
* **Add dependencies**  

Add the firebase dependency to your app-level build.gradle file

```gradle
apply plugin: 'com.google.gms.google-services'
```
Add the library dependency in your app-level build.gradle file 
```gradle
dependencies {
              ...
    implementation 'com.github.MustafaGamalAbbas:Full_FCM_Push-Pull_Notification:1.05'
}
```
Add jitpack.io to your project-level build.gradle file  :
```gradle
allprojects {
  		repositories {
  			```
  			maven { url 'https://jitpack.io' }
  		}
  	}
```
Add google-services to your project-level build.gradle file  :
```gradle
dependencies {
       ``
       classpath 'com.google.gms:google-services:3.0.0'
  }
```
 
  

## Usage
### Subscription
```java
        //1- Subscribe user device to topic
		FCMSubscriptionManager.getInstance(this).subscribeToTopic("TopicName");
	//2- Unsubscribe user device from topic 
	        FCMSubscriptionManager.getInstance(this).unsubscribeFromTopic("TopicName");
	//3- Unsubscribe user device to list of topics 
		FCMSubscriptionManager.getInstance(this).unsubscribeAll();
	//4- Subscribe user device from all subscribed topics
		FCMSubscriptionManager.getInstance(this).subscribeListOfTopics(listOfTopics) //listOfTopics ( list of string )

```
### Pulling 
* **Init** 
```java
	// at Activity 
           FCMPullNotificationManager manager = FCMPullNotificationManager.getInstance(this);
	// at Fragment 
	   FCMPullNotificationManager manager = FCMPullNotificationManager.getInstance(getContext());
```
* **Register FCM Callback** 
```java
	// make to activity or fragment implement IPullFCMCallback and use this line. 
		(Like YourActivity implement IPullFCMCallback) 
	 	manager.registerListener( this);
	// or make an anonymous class 
		 manager.registerListener(new IPullFCMCallback() {
		    @Override
		    public void onMessageReceived(RemoteMessage message) {
			// it will be called reveive notification for FCM 
		    }

		    @Override
		    public void onDeviceRegistered(String tokenId) {
			// it will be called when user device register for first time (first run application) 
		    }

		    @Override
		    public void onErrorHappened(String errorMessage) {
			// it will be called when something wrong happened 
		    }
        });
```
* **Auto launch notification (Optional)** 
```java
	// just tell manager that you will handle notification
 	manager.autoLaunchNotification(builder);
	// builder!? 
	// build a Notificationbuilder that has all properties you need , that will affect on apprance and properties of notification 
 	 NotificationBuilder builder = new NotificationBuilder(this) // this--> Context, if you will use fragment ,use getContext()
	// should set intent that has the launched activity (
	 .setContentIntent(new Intent(getApplicationContext(), YourActivity.class))
	// icon notification that will be at foreground ONLY, 
	// at background the app icon will be in notification that's handle by firebase
	 .setNotificationIcon(id)
	// make your notification auto cancelled 
	 .setAutoCancel(true)
	// make sound of notification is defualt sound of device 
	.setDefaultSound()
	// set custom sound for notification (foreground and background)
	.setCustomSound(customSound)
	// customize notification title 
	.setNotificationTitle("Your custom title")
	// customize notification Message
	.setNotificationMessage("Your cutsom messages ")
```
### Pushing 
* **Init** 

[Getting ServerKey](https://stackoverflow.com/questions/37427709/firebase-messaging-where-to-get-server-key?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa)
```java
	// initiate and send your server key 
    	FCMPushNotificationManager manager = FCMPushNotificationManager.getInstance(serverKey);
```
* **Register FCM Callback** 
```java
	// make to activity or fragment implement IPushFCMCallback and use this line. 
		(Like YourActivity implement IPushFCMCallback) 
	 	manager.registerListener( this);
	// or make an anonymous class 
		manager.setPushFCMCallback(new IPushFCMCallback() {
                    @Override
                    public void onPushNotificationSuccess() {
                       // on pushinh notification  
                    }

                    @Override
                    public void onError(String errorMessage) {
                       // on can't push notification  
                    }
                });
```
* **Send Notification** 

```java
	// You can notify through 4 ways as following 
	
	//1- Notify group of people subscribed to some topic
	   manager.notifyByTopic("Title", "Message","TopicName")
	//2-Notify list of groups of people subscribed to some topics
	   manager.notifyByTopics("Title", "Message",ListOfTopics) //ListOfTopics --> List of String 
	//3-Notify just one person through his/her TokenID
	   manager.notifyByToken("Title", "Message",userToken)
	//4-Notify just some persons through their TokenID
	   manager.notifyByToken("Title", "Message",listOfTokensID) 
	 //(Optional) you can send extra data with notification    
         .addExtraData(data).send(); //data --> Map<Stirng,Stirng>
	 // To apply the action 
	 .send();
```
## Notes
* At background/killed status the icon of notification will be the app icon. 
* At background/killed if you use custom sound, the custom sound and default sound will being played so choose sound +1 second.
* FCM Pull/Push callback will being call if the activity or fragment, so be aware.
	
## License
--------


    Copyright 2018 MustafaGamalAbbas <Mustafapiso@outlook.com>.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
