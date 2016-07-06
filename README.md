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

