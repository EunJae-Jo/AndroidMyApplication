package com.example.myapplication.setting;

import java.util.ArrayList;

public class UseGoalPaginator {
    ArrayList<UseGoalORM> itemArrayList;
    int TOTAL_NUM_ITEMS = 10;
    final int ITEMS_PER_PAGES = 10;



    public UseGoalPaginator(ArrayList<UseGoalORM> itemArrayList) {
        this.itemArrayList = itemArrayList;
        TOTAL_NUM_ITEMS = itemArrayList.size();
    }

    public int getTotalPages() {
        int remainingItems = TOTAL_NUM_ITEMS % ITEMS_PER_PAGES;
        if(remainingItems>0)
        {
            return TOTAL_NUM_ITEMS / ITEMS_PER_PAGES;
        }
        return (TOTAL_NUM_ITEMS / ITEMS_PER_PAGES) - 1;
    }

    public ArrayList<UseGoalORM> getCurrent(int currentPage){
        int startItem = currentPage * ITEMS_PER_PAGES;
        int lastItem = startItem + ITEMS_PER_PAGES;

        ArrayList<UseGoalORM> current = new ArrayList<UseGoalORM>();

        try {
            for(int i=0;i<itemArrayList.size();i++)
            {
                if(i>= startItem && i < lastItem){
                    current.add(itemArrayList.get(i));
                }

            }
            return current;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
