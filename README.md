# mns: Money Never Sleeps!
IntelliJ IDEA平台插件. 支持查看股票实时行情. 支持美股, 港股, A股.

# Installation:
* IntelliJ IDEA -> Preferences -> Plugins -> Install Plugin From Disk..., 从本项目中下载 `lib/mns-x.x.x.jar`文件并选中, 安装.
* IntelliJ IDEA -> Preferences -> Plugins -> Marketplace, type `mns`/`money`/`money never`/`money never sleeps` to search and install.

# Compilation:
打开 `Terminal` 窗口, 运行 `./gradlew build` 命令, 结束后你会发现 `libs/mns-x.x.x.jar` 文件的生成.

# Usage:

Settings:

![Setting](/mns_screen_shot_settings.png)

US stocks:

![Detail](/mns_screen_shot_us.png)

Core indices:

![Index](/mns_screen_shot_index.png)


# Changes
* V1.0.0
  * 添加了设置窗口, 美股实时数据.
* V1.1.0
  * 添加了港股实时数据.
* V1.2.0
  * 添加了上证, 深证股票实时数据.
* V1.2.1
  * 格式化了成交额, 成交量, 总市值.
* V1.2.2
  * 新增了简洁模式.
* V1.4.0
  * 新增了核心指数.
* V1.4.1
  * 更新了 plugin.xml 以上传 Marketplace.
* V1.4.2
  * 跟随 IdeaIC 2020.2 更新.<br>
* V1.4.3
  * 激活了隐秘模式.<br>
* V1.5.0
  * 新增了基金数据.<br>
* V1.5.1
  * 添加了plugin icon.<br>
* V1.5.2
   * 代码优化, 以及 Android Studio 上面中文乱码的问题.<br>
  
# TODO
* **<s>基金</s>**
* **行情图**
