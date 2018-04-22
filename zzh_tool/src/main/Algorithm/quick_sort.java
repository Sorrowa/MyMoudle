package com.example.zzh_tool.Algorithm;

public class quick_sort {

    /**
     * 对int类型的数组进行排序
     *
     * @param array 需要排序的数组
     * @param i     排序的开头
     * @param j     排序的结尾
     * @throws Exception 当数组内容为空的时候发送这个错误
     */
    public void quick_sort(int[] array, int i, int j) throws Exception {
        if (array == null) {
            throw new Exception("所需排序的数组为空");
        }
        int first_num = i;
        int final_num = j;
        int m = array[i];
        while (i < j) {
            while ((array[j] > m) && (j > i))
                j--;
            if (i < j) {
                array[i] = array[j];
                i++;
            }
            while (array[i] <= m && i < j)
                i++;
            if (i < j) {
                array[j] = array[i];
            }
        }
        array[i] = m;
        quick_sort(array, first_num, i - 1);
        quick_sort(array, i + 1, final_num);
    }

}
