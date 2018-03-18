# EditTag
[![](https://jitpack.io/v/qiugang/EditTag.svg)](https://jitpack.io/#qiugang/EditTag)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EditTag-green.svg?style=true)](https://android-arsenal.com/details/1/3907)

Just use it to edit tags like EditText edit text.
 
### Demo
<img src="/screenshots/s01.jpg" width="360" height="640" />

### How to use

* Add the dependency

```groovy
 repositories {
     maven { url "https://jitpack.io" }
 }
 dependencies {
     compile 'com.github.qiugang:EditTag:{latest version}'
 }
```
* Add EditTag View in your layout resource

```xml
<me.originqiu.library.EditTag
   android:id="@+id/edit_tag_view"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   app:tag_layout="@layout/view_sample_tag"
   app:delete_mode_bg="#FF4081"
   app:input_layout="@layout/view_sample_input_tag"/>
```


* Custom your Tag ,Input EditText layout resource
* Set  tag list : ```setTagList(List<String> yourTags)```
* Get tag list: ```getTagList()```
* Set input tag enable ```setEditable(true)```
* Add a tag ```addTag(String tagContent)```
* Remove tag: ```removeTag(String... tagValue)```
* Set Tag data changed callback: ```setTagAddCallBack(TagAddCallback tagAddCallBack)``` and ```setTagDeletedCallback(TagDeletedCallback tagDeletedCallback)```
* ⚠ When you custom input_layout layout, please use ```MEditText``` replaced ```EditText```, in order to avoid some of the soft keyboard can not receive the delete action event

### Todo
 - [x] Delete single tag out of order


### Thanks
* [FlowLayout](https://github.com/hongyangAndroid/FlowLayout/blob/master/flowlayout-lib%2Fsrc%2Fmain%2Fjava%2Fcom%2Fzhy%2Fview%2Fflowlayout%2FFlowLayout.java)

### License
    The MIT License (MIT)

    Copyright (c) 2015 OriginQiu

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.



