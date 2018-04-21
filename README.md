使用jitpack进行操作，如果需要引入，则需要以下步骤，现阶段为zzh0.0.1版本。
当前版本只有一个圆形图片

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
	        compile 'com.github.Sorrowa:MyMoudle:0.0.1'
	}
