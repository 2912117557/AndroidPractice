# AndroidPractice
an android practice application based on douban api

初学Android后，第一个比较完整的练手项目

本项目实现豆瓣电影、图书、音乐的查看，基于以下两个api：
1. 豆瓣 Api V2：`https://douban-api-docs.zce.me/`
`apiKey=054022eaeae0b00e0fc068c0c0a2102a（该apiKey已失效）`
2. 豆瓣电影JSON数据：`https://github.com/Rocket-Factory/DoubanMovieJSON`

主要实现以下功能：
1. 电影列表、电影详细信息、参演人员信息查看，图书列表查看
2. 搜索
3. 注册登录注销
注册登录相关代码在另一个仓库：`https://github.com/2912117557/AndroidPractice_BackEnd`

问题：
做到一半，豆瓣封禁了apiKey，导致图书详细信息、音乐、评论没法实现
