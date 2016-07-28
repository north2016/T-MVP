进度有点慢,在很多人的建议和帮助下,逐步在完善


关键词 MVP+Retrofit+Rxjava+MaterialDesign+LeanCloud+NodeJS

简书：http://www.jianshu.com/p/b49958e1889d

weibo：http://weibo.com/1917320262

Github：https://github.com/north2014/T-MVP

QQ群：AndroidMVP   555343041 <a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=14f9009a0276624f6abf3221fe131c57ff05b70b5b4b922ed2c4aa4156155e73"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="AndroidMVP" title="AndroidMVP"></a>

####导语
删繁就简三秋树，领异标新二月花
>传统MVP给人的第一感觉通常是接口和类的暴涨，将Activity中除V之外的繁杂操作搬到P之后依然臃肿不堪。MVP从来都不是救命稻草，只能锦上添花，不能雪中送炭。也许在权衡利弊之后，很多人会对MVP望而却步。 现在  我们换一个思路，取其精华，去其糟粕，完成T-MVP大变身。

#一、T-MVP简介
***
话不多说，先上图：
![T-MVP架构图](http://upload-images.jianshu.io/upload_images/751860-0bcc0b49c3ab13a3.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 下面，看看如何利用泛型把我们从繁杂重复的劳动中解救出来。
>后台接口用的是LeanCloud的REST API，数据通过Node爬虫从网页上获取，先爬出十个页面的列表，然后每一个列表爬进去获取文章详情。爬虫代码见app.js。（建议替换成自己申请的appId和appKey）。

##1、页面分层结构

宗旨：纯粹界面操作交互，不需要MP参与的行为，尽量V自己做，保证MVP职责清晰，P只有干净简洁的协助VM的业务逻辑操作，M只处理数据操作。

![登录契约类](http://upload-images.jianshu.io/upload_images/751860-7c70aefc2a573290.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


相比谷歌官方Contract类，多了一个Model，用于将P中繁杂的数据操作分离解耦，让P层变得简洁明了,只处理M和V相关操作即可，登录Present的全部代码：

![登录Present的全部代码](http://upload-images.jianshu.io/upload_images/751860-60544cde35b71772.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


RxManage用于管理订阅者、观察者、以及事件。（详见代码）
例如：

发送事件： `mRxManage.post(C.EVENT_LOGIN, user);`

接收事件：`mRxManage.on(C.EVENT_LOGIN, arg ->mView.initUserInfo((_User) arg)); `

LoginModel的全部代码：
![LoginModel的全部代码](http://upload-images.jianshu.io/upload_images/751860-e07ed878aa12ae29.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


抽离出Model不仅各层更加分工明确便于Model的复用，而且大大简化了Presenter的代码量，让P层更简洁更专注。职能清爽，一目了然。

##2、列表页
例如一个列表页，用户关心的，只有列表的Item展示：

  `mTRecyclerView.setItemView(ItemView.class);`

不需要在当前页面写网络请求，不需要写下拉刷新和分页加载更多的回调，不需要写任何Adapter，只需要这一句即可。因为用泛型在TRecyclerView里面写过抽象层的操作，将监听和数据的获取封装成通用模板，从此可以一劳永逸了。

比如，分页加载的封装操作：
![TRecyclerView部分代码](http://upload-images.jianshu.io/upload_images/751860-60b1a09e543d2a24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

对于有HeaderView的List也一样:

 `mTRecyclerView.setHeaderView(HeaderView.class).setItemView(ItemView.class);`

ViewHolder也是相当简洁的写法：
![ViewHolder](http://upload-images.jianshu.io/upload_images/751860-ff9843e014510adc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


至此，项目可以永远告别OnRefresh ,onLoadMore ,Adapter。

##3、项目结构
利用泛型封装的一些Base类，总代码不超过1000行，也是T-MVP的核心：

![T-MVP项目结构](http://upload-images.jianshu.io/upload_images/751860-281b2b0198b49042.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

T-MVP利用泛型解藕和高度抽象封装之后，相较于传统MVC和MVP代码量大大精简，架构的代码量也是精简至极：

例如BasePresenter的全部代码：

![BasePresenter的全部代码](http://upload-images.jianshu.io/upload_images/751860-3f8dca2e4444e87a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#二、T-MVP的目标
    //TODO
  * 1 整理Base类，吸纳意见之后，继续优化封装，将简洁进行到底。让MVP不再背负繁杂的名声。
  * 2 发布到github
  * 3 争取发布到jcenter
  
  
  正在做：
  * 1 添加持久层数据存储realm
  
  * 2 recyclerview支持多种LayoutManager （GridLayoutManager StaggeredGridLayoutManager）,支持多头部，多列表类型，多底部。
  
  * 3 添加fragment的MVP,同时也解决了一个页面需要多个P的问题
  
  * 4 尽量把反射去掉，免得有人抓住把柄说性能不好
  
  * 5 把框架部分隔离出来，单独做成Library
  
  * 6 用apt自动帮我们生成一些黑科技代码，达到事半功倍的效果



#三、进度
 * 1登录、注册
 * 2列表分类
 * 3文章详情 文章评论列表
 * 4用户中心 用户评论列表 
 * 5更换头像
 * 6用户列表

项目截图：


![7247fc46jw1f3p5r5tsynj20u01hcwl0.jpg](http://upload-images.jianshu.io/upload_images/751860-a76fb610afd96fac.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



![7247fc46jw1f3p5r8cjjhj20u01hc47t.jpg](http://upload-images.jianshu.io/upload_images/751860-55c37ef159f474ec.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![7247fc46jw1f3p5r35jqnj20u01hcdt4.jpg](http://upload-images.jianshu.io/upload_images/751860-67d564d3254f6e89.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![7247fc46jw1f3p5rceufqj20u01hcnaz.jpg](http://upload-images.jianshu.io/upload_images/751860-836f500152f280bc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



## License

```
 No Fucking License.  
```
