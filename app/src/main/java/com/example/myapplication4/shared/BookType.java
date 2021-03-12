package com.example.myapplication4.shared;

import android.util.Log;

import java.util.Random;

public class BookType {
    static String logTag = "BookType";
    private static final Random random = new Random();
    private static final String[] AllBookSmallTypeArray = {"科普", "互联网", "科学", "科技", "科普", "用户体验", "通信", "交互", "旅行", "王小波", "生活", "励志", "成长", "悬疑", "武侠", "韩寒", "奇幻", "青春文学"};
    private static final String[] LiteratureBookSmallTypeArray = {"海明威", "小说", "中国文学", "村上春树", "王小波", "余华", "鲁迅", "米兰·昆德拉", "外国文学", "经典", "童话", "儿童文学", "名著", "外国名著", "杜拉斯", "文学", "散文", "诗歌", "张爱玲", "钱钟书", "诗词", "港台", "随笔", "日本文学", "杂文", "古典文学", "当代文学", "茨威格", "米兰·昆德拉", "杜拉斯", "港台"};
    private static final String[] PopularBookSmallTypeArray = {"漫画", "绘本", "推理", "青春", "言情", "科幻", "东野圭吾", "悬疑", "武侠", "韩寒", "奇幻", "日本漫画", "耽美", "亦舒", " 三毛", "安妮宝贝", "网络小说", "郭敬明", "推理小说", "穿越", "金庸", "轻小说", "几米", "阿加莎·克里斯蒂", "张小娴", "幾米", "魔幻", "青春文学", "J.K.罗琳", "科幻小说", "高木直子", "古龙", "沧月", "蔡康永", "落落", "张悦然"};
    private static final String[] CultureBookSmallTypeArray = {"历史", "心理学", "哲学", "传记", "文化", "社会学", "艺术", "设计", "政治", "社会", "建筑", "宗教", "电影", "数学", "政治学", "回忆录", "思想", "国学", "中国历史", "音乐", "人文", "戏剧", "人物传记", "绘画", "艺术史", "佛教", "军事", "西方哲学", "近代史", "二战", "自由主义", "考古", "美术"};
    private static final String[] LifeBookSmallTypeArray = {"爱情", "旅行", "生活", "励志", "成长", "心理", "摄影", "女性", "职场", "美食", "教育", "游记", "灵修", "情感", "健康", "手工", "养生", "两性", "人际关系", "家居", "自助游"};

    public static String[] getBookSmallTypeArray(String bookBigType) {
        switch (bookBigType) {
            case "book_All":
                return AllBookSmallTypeArray;
            case "book_Literature":
                return LiteratureBookSmallTypeArray;
            case "book_Popular":
                return PopularBookSmallTypeArray;
            case "book_Culture":
                return CultureBookSmallTypeArray;
            case "book_Life":
                return LifeBookSmallTypeArray;
            default:
                return AllBookSmallTypeArray;
        }
    }

    public static String getRandomBookSmallType(String[] bookSmallTypeArray) {
        int i = random.nextInt(bookSmallTypeArray.length);
        return bookSmallTypeArray[i];
    }
}
