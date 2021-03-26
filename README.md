# AndroidPractice

初学Android后，第一个比较完整的练手项目

一个仿豆瓣APP（一切数据归豆瓣所有，本应用不做商用）
要开发什么的思路来源于开源项目：瓣呀`https://github.com/forezp/banya`，具体实现全部个人完成。

应用截图：项目目录`\screenshot`

描述：因为开发过程中豆瓣官方API突然不能用，通过网友分享的小技巧和私人搭建的API，勉强完成电影列表，电影详情，图书列表的开发。
其它功能没实现，但开发思路大同小异。项目练手功能算完成。
首页分为三个模块：电影、图书、音乐。电影模块包括热映榜、TOP250，图书模块包括综合、文学、流行、文化、生活。
实现了电影列表，电影详情，电影参演人员信息，图书列表的查看；搜索功能；注册，登录，注销功能。

所用技术：
1. Navigation + DrawerLayout
2. ViewPager2 + PagedListAdapter + RecyclerView + CardView
3. ViewModel + LiveData + Room
4. Retrofit + Glide
5. Search

开发难点：
1. ViewPager2嵌套ViewPager2，滑动冲突
2. 学习应用架构指南(ViewModel + LiveData + Repository[Room+Retrofit])
3. 拦截页面，跳转登录页面

本项目所用API：
1. 私人搭建的API，豆瓣电影JSON数据：`https://github.com/Rocket-Factory/DoubanMovieJSON`
2. 豆瓣官方图书API：`https://douban-api-docs.zce.me/book.html``apiKey=054022eaeae0b00e0fc068c0c0a2102a`

注册登录相关代码在另一个仓库：`https://github.com/2912117557/AndroidPractice_BackEnd`






