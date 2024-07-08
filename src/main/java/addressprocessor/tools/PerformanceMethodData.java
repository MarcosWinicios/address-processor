package addressprocessor.tools;

import lombok.Data;

import java.text.DecimalFormat;
import java.util.List;

@Data
public class PerformanceMethodData {

    private String methodName;
    private long nanosecondsDuration;
    private double millisecondsDuration;

    public PerformanceMethodData(){}

    /***
     *
     * @param methodName String Name of the method to be analyzed
     * @param nanosecondsDuration long Duration time of the method in nanoseconds
     * @param millisecondsDuration double Duration time of the method in milliseconds
     */
    public PerformanceMethodData(String methodName, long nanosecondsDuration, double millisecondsDuration) {
        this.methodName = methodName;
        this.nanosecondsDuration = nanosecondsDuration;
        this.millisecondsDuration = millisecondsDuration;
    }

    @Override
    public String toString() {
        return  "\n_________\n" + methodName + " -> {\n" +
                "   nanosecondsDuration: " + this.formatDurationValue(nanosecondsDuration) + ",\n"
                + "   millisecondsDuration: " + this.formatDurationValue(millisecondsDuration) + "\n"
                + '}';
    }

    public static String getResumeObj(PerformanceMethodData obj){
        return obj.methodName + ": " + obj.formatDurationValue(obj.nanosecondsDuration);
    }


    public static void printMethodList(List<PerformanceMethodData> list) {
        list.stream()
                .map((item) -> item.methodName + ": " + item.formatDurationValue(item.nanosecondsDuration))
                .forEach(System.out::println);

    }

    private String formatDurationValue(long value){
        return new DecimalFormat("#,###").format(value);
    }

    private String formatDurationValue(double value){
        return new DecimalFormat("#,###.##").format(value);
    }
}
