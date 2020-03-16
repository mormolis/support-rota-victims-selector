package persistance.csv;

public enum CsvHeader {
    NAME("Name"),
    SUB_TEAM("SubTeam"),
    LOCATION("Location"),
    LAST_SERVED("LastServed"),
    SHIFTS_COUNTER("ShiftsCounter"),
    IS_EXCLUDED("IsExcluded");

    private String onCsvValue;

    CsvHeader(String onCsvValue) {
        this.onCsvValue = onCsvValue;
    }

    public String getOnCsvValue() {
        return onCsvValue;
    }

    public static String getHeader() {
        StringBuilder sb = new StringBuilder();
        for (CsvHeader header : CsvHeader.values()) {
            sb.append(header.onCsvValue);
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
}
