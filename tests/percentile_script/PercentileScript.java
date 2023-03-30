class PercentileScript {
    private static void printPercentile(List<Integer> percentileList, int p) {
        double percentileMillis = findPercentile(percentileList, p);
        System.out.println(percentileMillis);
    }
    private static double findPercentile(List<Integer> percentileList, int p) {
        int index = (int) Math.round((percentileList.size() - 1) * (p / 100.0));
        return percentileList.get(index) / 1000.0;
    }
}