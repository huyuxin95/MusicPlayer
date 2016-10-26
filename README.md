# MusicPlayer
这是用MediaPlay做的一个音乐播放器

主要分四个模块播放控制界面,曲库,百度搜索,最近播放列表

启动界面
--

![qi](https://github.com/huyuxin95/MusicPlayer/tree/master/images/01.png)

播放控制界面
--
主要是实现了音乐的播放暂停,上一曲,下一曲,单曲循环,列表循环,随机播放,播放控制写在服务里面

![](https://github.com/huyuxin95/MusicPlayer/tree/master/images/02.png)

播放列表界面
--
遍历手机里面的歌曲显示在listview里,给listview设置点击监听,点击item跳转到播放界面,播放对应歌曲

![](https://github.com/huyuxin95/MusicPlayer/tree/master/images/03.png)

百度搜索:
--
在项目的第三个模块,镶嵌了一个webview,将百度设为默认首页

![](https://github.com/huyuxin95/MusicPlayer/tree/master/images/04.png)

最近播放列表:
--
当播放新的一首歌曲时,将当前的歌曲与数据库中的播放记录进行对比,如果存在,将原来的记录删除,将现在的播放歌曲
放置数据的最末端,listview显示所有播放记录,这里对数据库的存储用的是第三方库litepal

![](https://github.com/huyuxin95/MusicPlayer/tree/master/images/05.png)
