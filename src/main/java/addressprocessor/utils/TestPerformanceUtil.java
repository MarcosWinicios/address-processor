package addressprocessor.utils;

import addressprocessor.tools.PerformanceMethodData;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.TreeSet;

public class TestPerformanceUtil {
    private static long startTime = 0;
    private static long endTime = 0;
    private static final TreeSet<PerformanceMethodData> methodList = new TreeSet<>(
            Comparator.comparingLong(PerformanceMethodData::getNanosecondsDuration));

    /***
     * @param time
     */
    public static void setTimeValues(long time){
        setTimeValues(time, "");
    }

    /***
     * @param time
     * @param methodName
     */
    public static void setTimeValues(long time, String methodName){
        if(startTime == 0){
            startTime = time;
        }else{
            endTime = time;
            calculateDurationTime(methodName);
        }
    }

    public static void printMethodsInAnalysis(){
        verifyMethodList();
        methodList.forEach(System.out::println);
    }

    /***
     * Prints the fastest method and the slowest method among the methods under analysis
     */
    public static void printSlowerAndFasterMethod(){
        System.out.println();

        verifyMethodList();

        PerformanceMethodData faster = methodList.first();
        PerformanceMethodData slower = methodList.last();

        System.err.println("Faster: " + PerformanceMethodData.getResumeObj(faster));
        System.err.println("Slower: " + PerformanceMethodData.getResumeObj(slower));

        long difference = slower.getNanosecondsDuration() - faster.getNanosecondsDuration();
        System.err.println("Difference: " + new DecimalFormat("#,###").format(difference));

    }

    private static void verifyMethodList(){
        if(methodList.isEmpty()){
            throw new RuntimeException("No methods were analyzed.");
        }

        if(methodList.size() == 1){
            throw new RuntimeException("Only one method was analyzed.");
        }
    }

    private static void calculateDurationTime(String methodName){
        long durationInNanoseconds =  endTime - startTime;
        double durationInMilliseconds = durationInNanoseconds / 1_000_000.0;

        methodList.add(new PerformanceMethodData(methodName, durationInNanoseconds, durationInMilliseconds));
//        methodList.sort(Comparator.comparing(PerformanceMethodData::getNanosecondsDuration));
        resetTimes();
    }

    private static void resetTimes(){
        startTime = 0;
        endTime = 0;
    }

    public static void resetMethodList(){
        methodList.clear();
    }

}
