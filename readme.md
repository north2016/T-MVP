####为兼容linux、ubuntu等系统，运行之前需要发布插件到本地  
运行前需要在Terminal中运行 ./gradlew -p buildsrc clean build uploadArchives --info


QQ群：AndroidMVP   555343041 <a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=14f9009a0276624f6abf3221fe131c57ff05b70b5b4b922ed2c4aa4156155e73"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="AndroidMVP" title="AndroidMVP"></a>


#更新日志：

2017/1／9： AOP新增CheckLoginAspect切片拦截未登陆用户，控制未登陆用户的操作权限

2017/1／8： 使用Apt封装Retrofit生成ApiFactory替换掉所有的Repository，狂删代码

2017/1／7： 使用DataBinding替换掉所有的ButterKnife，狂删代码

2017/1／6： 使用DataBinding替换掉所有的ViewHolder，狂删代码，从此迈向新时代

2016/12／30：使用Apt生成全局路由TRouter，更优雅的页面跳转，支持传递参数和共享view转场动画

2016/12／29：去掉BaseMultiVH新增VHSelector支持更完美的多ViewHolder

2016/12／28：使用Apt生成全局的ApiFactory替代所有的Model

2016/12／27：增加了BaseMultiVH扩展支持多类型的ViewHolder

2016/12／26：抽离CoreAdapterPresenter优化TRecyclerView

#系列文章：

##[安卓AOP实战：面向切片编程](http://www.jianshu.com/p/b96a68ba50db)

##[Android实用技巧之:用好泛型,少写代码](http://www.jianshu.com/p/0f6800ded3da)

##[安卓AOP实战:APT打造极简路由](http://www.jianshu.com/p/6ccfa7b50f0e)
>全局路由TRouter，更优雅的页面跳转


##[安卓AOP实战:Javassist强撸EventBus](http://www.jianshu.com/p/33d8a3165b07)
>加入OkBus，实现注解传递事件

##[安卓AOP三剑客:APT,AspectJ,Javassist](http://www.jianshu.com/p/dca3e2c8608a)
>1、去掉所有反射>2、新增apt初始化工厂，替换掉了dagger2。>3、新增aop切片，处理缓存和日志

关键词 AOP+MVP+Retrofit+Rxjava+MaterialDesign+LeanCloud+NodeJS

简书：http://www.jianshu.com/p/b49958e1889d

weibo：http://weibo.com/1917320262

Github：https://github.com/north2016/T-MVP


#答疑
运行报错的，自觉google，检查As版本(2.2.2)，gradle配置等(AndroidStudio每个版本gradle的api都不一样，坑)，低版本手机FloatingActionButton在xml会报错，因为icon用了SVG(5.0+)

参考配置：   as:2.2.2    grade:2.2.3       buildTools:24.0.3


本demo现阶段处于快速优化迭代状态，只提供idea，暂不提供lib
   
![首页](app.gif)
## License

```
 No Fucking License.  
```



