package com.sai.gittrends.Utilities;

/**
 * Created by SAI_U2 on 16/08/2018.
 */

public class Settings {
    public static final int PageItem = 50;

    public static class URLs {
        public static final String root = "https://api.github.com/";

        //        public static final String bestTrends = "search/repositories?q=a+language:{lang}&sort=stars&order=desc";
        public static final String bestTrends = "search/repositories?q=android&sort=score&order=desc&per_page=" + PageItem;
    }
}
