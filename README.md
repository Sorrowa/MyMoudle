使用jitpack进行操作，如果需要引入，则需要以下步骤，现阶段为zzh0.0.6版本
当前版本有一个圆形图片，针对int[]类型的快速排序，以及一个将float转化为dp的转化工具,自定义柱状图，一个仿制tim文件列表的view，一个圆形进度view


Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        androidTestImplementation 'com.github.Sorrowa:MyMoudle:0.0.6'
	}
