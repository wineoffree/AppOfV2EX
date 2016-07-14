# README
##这是基于andriod的V2EX手机APP的开发不同进度的文件，直到完成整个V2EXAPP,基本上一天更新一次，项目文件是基于andriod studio创建的。主要介绍每个项目的
###                             Author：wineoffree
####使用说明 
导入andriod studio即可使用
####版本更新
#####moudle1：
实现了页面左右移动，解析接口的JSON生成了下拉框<br>
页面移动通过viewpager实现<br>
解析JSON是通过HTTPURLconnection获取，再用JSONobject，JSONarray解析再利用MAP存入LIST中
#####moudle2：
加入了一个简单的搜索功能 基于sqlite<br>
先调用MainActivity中被注释的insert方法 初始化 在就可以查询 InitialTable中有数据介绍<br>
这次由于时间原因只是实现了一个很简单的搜索功能，以后修改

#####moudle3：
加入了图片内存缓存和本地文件缓存
文字内容本地文件缓存<br>
界面一无网也可生成<br>
暂时未将网络与无网分开，以后配合界面修改时设计一下
#####moudle4：
用AutocompleteTextView实现了一个自动补全的搜索功能<br>
暂时只能根据id进行搜索<br>
以后会完善。
#####moudle5：
重写了代码 看起来会比原来方便些<br>
按照V2EX网页重写了pageview和滑动标题<br>
用jsoup以及正则表达式爬取网络，生成第一层界面<br>
修复了下图片错位问题，但加载有些慢，以后利用三级缓存解决
#####moudle6：
用jsoup以及正则表达式爬取网络，生成第二层界面<br>
稍微修改了下界面<br>
二层界面主题图片暂时不显示，到时用第一层保存的文件和缓存生成
#####moudle7：
增加了图片三级缓存，用户体验逼原来好多了<br>
增加了根据文件无网显示一级主界面，判断无网时使用<br>
增加了刷新功能，点击即可
#####moudle8：
修改了反正图片错位机制，滚动时不加载<br>
同时发现爬取图片的正则有问题，已更改，现在的用户体验好多了
#####moudle9：
增加了搜索功能<br>
美化了下界面。。虽然还是不好看<br>
项目基本功能完成，以后不断完善
