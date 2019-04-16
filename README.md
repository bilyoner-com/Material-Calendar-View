# Material-Calendar-View

Latest Version [![](https://jitpack.io/v/bilyoner-com/Material-Calendar-View.svg)](https://jitpack.io/#bilyoner-com/Material-Calendar-View)

Material-Calendar-View is a simple and customizable calendar widget for Android based on Material Design. The widget has two funcionalities: a date picker to select dates (available as an XML widget and a dialog) and a classic calendar. The date picker can work either as a single day picker, many days picker or range picker.

We described a simple usage of the component [in this article](http://applandeo.com/blog/material-calendar-view-customized-calendar-widget-android/).

## How to use?
Make sure you are using the newest **com.android.support:appcompat-v7**.

Make sure you are using Java 8 in your project. If not, add below code to **build.gradle** file:
```
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

Make sure you have defined the **jcenter()** repository in project's **build.gradle** file:
```
allprojects {
    repositories {
        jcenter()
    }
}
```

Add the dependency to module's **build.gradle** file:
```
dependencies {
    compile 'com.github.bilyoner-com:Material-Calendar-View:1.5.4'
}
```

To your **XML layout** file add:
```xml
<com.applandeo.materialcalendarview.CalendarView
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## License
```
Copyright 2017, Applandeo sp. z o.o.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
