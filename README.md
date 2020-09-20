# pdd-myshop
自家店铺管理：一个javaweb工程实例 有前后端和小程序端
先丢个git地址：https://github.com/meiliqin/pdd-myshop.git。

然后，来磕一下背景。

背景
2018年弟弟高考完上大学，我怀着宝宝，生之前还是比较闲，在妹妹的建议下开了一家拼多多个人店铺，一穷二百涉足电商。

由于是副业玩票，根本没抱太大希望，没想到啊，可能是遇上拼多多生长的红利期，就做起来了，2019年的大半年，妈妈在广州一边帮我带娃，一边打包发货，日出20-50单左右，此时是随机试款主打卖烘焙diy产品。

2019年年底，妹妹又让开一家宠物用品店铺。我和弟弟一个带娃程序员，一个穷苦大学生，实属门外人士，但是妹妹是做外贸批发的，离生意最近，熟悉产品和供应链。于是一家人合力干票大的，正儿八经开个大店。

https://meiliqin.oss-cn-beijing.aliyuncs.com/blog/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202020-02-16%20%E4%B8%8B%E5%8D%886.01.09.png

2019年从双11到双12左右，实现了日出千单。虽然出现了太多问题，缺货，缺人，缺面单，仓库，打单，搬货...事情太多了，一家人只有妈妈辞了工作在家打包，其他人都是兼职。而且爸妈在老家负责发货，爸妈电脑手机电商什么的都不太会，我和弟弟妹妹都在外面不同的三个地方，沟通成本巨大，完全负荷不了，真是想放弃。

严重的问题是，根本不知道哪个产品哪个款今天卖了多少！什么时候要进货！进货每个款要进多少！单子多了，产品和款式多了，爸妈边发货边估计，酱糊了！

看着家庭微信群里天天吵，天天乱糟糟，我自然要想办法解决燃眉之急，不然他们沟通不好，担心爸妈要气病了，急病了。

怎么办呢
首先，丢个图他们明确了下各人的角色和职责，明确妹妹是老板，主要决策由她来拍板，分工图如下：

图呢？丢哪去了呀，还没找到。

然后，沟通成本大的问题，我想了很多办法，我们正规公司有优秀的办公协作软件。但是爸妈经常用语音在群里说缺什么货，我们听语音当然不方便，重要消息就这么漏掉了。我叫他们用文字消息，叫不要发群，叫用协作小程序，都不好使，后来打印了一沓商品库存表，让他们每天填表拍给我们，都不太好。事实上，爸妈年纪大了，我应该为他们减负，而不是为难他们。（其实我也劝过妹妹不要开店了，不要让爸妈在家发货了太辛苦，但是爸妈好像干得很快乐很起劲，他们当了一辈子工人，觉得打包发货算轻松活，而且在老家的人面前有一种当老板的自豪感）。

那么，爸妈什么都不用干，妹妹那边就知道每天每个产品卖了多少，要进多少货，又赚了多少钱呢。

我研究了一些erp系统，打算买一个便宜点的用用，管家婆啊，管易云啊，网店管家啊，贵就算了，操作成本都挺大，接入拼多多还得另外找人。也难怪他们是给公司用的，实在不适合我家。

真的开始干了
没办法，只有自己开发咯，其实我真的不想搞，我本职是做安卓开发的，要搭后台写前端要耗费我很多时间和精力，我宝宝才1岁，工作也不闲，这一年生了宝宝晚上也睡不好觉，身体开销也很大，最近胃又不太好。

忙里偷闲，工作不忙的时候，每天晚饭后看一个小时，加上2020年春节期间的肺炎公司2.3-2.10多放了一个星期的假，总算搞完一个粗糙的工程，先投入使用再慢慢迭代吧。

上几张投入使用的效果图看下：

https://meiliqin.oss-cn-beijing.aliyuncs.com/blog/IMG20200827_101003.png?Expires=1598495014&OSSAccessKeyId=TMP.3KjbknXcR6cz3Qfk2Zv7fyiZXDoEb2CXxBHwpgREZ2G1sXdTsJWMKYjvUm5KpbGHKpCThqTgTGMcKn4jaB1rjhmgomXfY1&Signature=naXS14qvPUy0NkCHagG9pZNsD1o%3D

https://meiliqin.oss-cn-beijing.aliyuncs.com/blog/IMG20200827_101743.png?Expires=1598495028&OSSAccessKeyId=TMP.3KjbknXcR6cz3Qfk2Zv7fyiZXDoEb2CXxBHwpgREZ2G1sXdTsJWMKYjvUm5KpbGHKpCThqTgTGMcKn4jaB1rjhmgomXfY1&Signature=mPyLJyPIZbuyZAZTr16colPoamo%3D

https://meiliqin.oss-cn-beijing.aliyuncs.com/blog/IMG20200827_101748.png?Expires=1598495048&OSSAccessKeyId=TMP.3KjbknXcR6cz3Qfk2Zv7fyiZXDoEb2CXxBHwpgREZ2G1sXdTsJWMKYjvUm5KpbGHKpCThqTgTGMcKn4jaB1rjhmgomXfY1&Signature=PVghl8DxNcC8ukjBxk1b3Tshn0Y%3D

https://meiliqin.oss-cn-beijing.aliyuncs.com/blog/IMG20200827_134457.png?Expires=1598507673&OSSAccessKeyId=TMP.3KjbknXcR6cz3Qfk2Zv7fyiZXDoEb2CXxBHwpgREZ2G1sXdTsJWMKYjvUm5KpbGHKpCThqTgTGMcKn4jaB1rjhmgomXfY1&Signature=SwIO5UdsHlEyEib%2FmT2sp30Wb2U%3D
