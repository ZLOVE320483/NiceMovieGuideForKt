# NiceMovieGuideForKt

####项目产生背景
>最近公司的项目打算引入dagger2，这可有点慌，因为之前也看过一点，但是并未深入理解，也从未在项目中落地过。
于是打算找个开源项目，看看dagger2是如何使用的，以便将来公司项目中引入dagger2时不至于啥也不会，到处挖坑。
####学习过程
>在学习开源项目之前，自然是需要了解一些基础只是，否则直接上代码肯定会是一头雾水，所以就先看了下面的这些入门文章：
 
 学习dagger2，一定要先理解依赖注入（或者说控制反转）到底是什么，建议阅读下面的系列文章：
   
   [Android 神兵利器Dagger2使用详解（一）基础使用](https://blog.csdn.net/mq2553299/article/details/73065745)
   [Android 神兵利器Dagger2使用详解（二）Module&Component源码分析](https://blog.csdn.net/mq2553299/article/details/73136396)
 
 看完上面的还需要对dagger2再深一步的了解，至少需要知道是怎么使用的，所以又看了下面的文章：
 [Android - Dagger2使用详解](https://www.jianshu.com/p/2cd491f0da01) （注：这篇文章的帮助还是很大的）
 
 看过几篇文章之后，打算开始写代码了，于是在GitHub上找了一个开源项目：[原项目](https://github.com/esoxjem/MovieGuide)
 但是该项目clone下来运行起来后发现忘了请求是不成功的，原因是作者并没有公开访问api的key，所以需要自己申请，
 于是到这里 [申请key](https://www.themoviedb.org/settings/api)

于是有了第一个项目：[第一个项目](https://github.com/ZLOVE320483/NiceMovie)

这个过程让自己对dagger2有了进一步理解
 
后来又了解到公司项目打算慢慢转为kotlin编写了，所以才有了现在的这个项目，将之前的项目原封不动的转成了kotlin，当然也借助了很多优秀的文章

[如何在你的Kotlin代码中移除所有的!!(非空断言)](https://juejin.im/post/5afd9090f265da0ba46a0429)

[从原理分析Kotlin的延迟初始化: lateinit var和by lazy](https://juejin.im/post/5affc369f265da0b9b079629)
 
 [kotlin 笔记：高阶函数的使用](https://juejin.im/entry/58eb3e2da0bb9f006929674a)
 
 [Kotlin 扩展函数详解与应用](https://blog.csdn.net/ComWill/article/details/77206508)
 
 ####项目截图
 ![列表页](https://github.com/ZLOVE320483/NiceMovieGuideForKt/blob/master/img/device-2018-11-16-185727.png)
 
 ![详情页](https://github.com/ZLOVE320483/NiceMovieGuideForKt/blob/master/img/device-2018-11-16-185748.png)