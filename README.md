# Project 3 - *Simple Tweets*

**Simple Tweets** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **X** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X]	User can **sign in to Twitter** using OAuth login
* [X] User can **switch between Timeline and Mention views using tabs**
  * [X] User is displayed the username, name, and body for each tweet
  * [X] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
  * [X] User can **pull down to refresh tweets** in either timeline
  * [X] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.
* [ ] User can navigate to **view their own profile**
  * [ ] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [ ] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [ ] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [ ] Profile view includes that user's timeline
* [ ] User can **compose and post a new tweet**
  * [X] User can click a “Compose” icon in the Action Bar on the top right (USING FAB INSTEAD)
  * [ ] User can then enter a new tweet and post this to twitter
  * [ ] User is taken back to home timeline with **new tweet visible** in timeline
  * [ ] User can **see a counter with total number of characters left for tweet** on compose tweet page
* [X] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [ ] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in an offline mode.

The following **optional** features are implemented:

* [ ] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [ ] Usernames and hashtags are styled + clickable within tweets [using clickable spans](http://guides.codepath.com/android/Working-with-the-TextView#creating-clickable-styled-spans).
* [ ] User can view following / followers list through the profile
* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] User can **"reply" to any tweet on their home timeline**
  * [ ] The user that wrote the original tweet is automatically "@" replied in compose
* [ ] User can click on a tweet to be **taken to a "detail view"** of that tweet
 * [ ] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ ] Improve the user interface and theme the app to feel twitter branded
* [ ] User can **search for tweets matching a particular query** and see results

The following **stretch** features are implemented:

* [ ] Used Parcelable instead of Serializable leveraging the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
- [ ] Replaced all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [ ] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to cleanup event handling blocks.
* [ ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] User can view their direct messages (or send new ones)

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright 2016 Pablo Arango

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.