## Android 弹性下拉 ##

![](https://github.com/lvkaixuan/ReboundLayout/blob/master/Android下拉.gif)

 - 使用方法
 
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```
compile 'com.github.lvkaixuan:ReboundLayout:v1.0'
```

 - 布局中使用

```
<com.lkx.library.ReboundLayout
     android:layout_width="match_parent"
     android:layout_height="match_parent">
        ...
</com.lkx.library.ReboundLayout>
```

 - 注意事项

	ReboundLayout继承自ScrollView,所以内部只能包裹一个子布局.
