public class Year {
    private int year;
    public int[] months; // months[0] store the number of judgments in current year

    public Year(int year) {
        this.year = year;
        this.months = new int[13];
        for (int i = 0; i < 13; i++)
            months[i] = 0;
    }

    public StringBuilder getMonths() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < months.length; i++) {
            sb.append(intToMonth(i) + ": " + months[i] + "\n");
        }
        return sb;
    }

    public void addJudgment(int month) {
        //String [] date = judgmentDate.split("-");
        //int month = Integer.parseInt(date[1]);
        months[month]++;
        months[0]++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Year that = (Year) o;
        return this.year == that.year;
    }

    private String intToMonth(int m) {
        switch (m) {
            case 0:
                return "Razem";
            case 1:
                return "Styczeń";
            case 2:
                return "Luty";
            case 3:
                return "Marzec";
            case 4:
                return "Kwiecień";
            case 5:
                return "Maj";
            case 6:
                return "Czerwiec";
            case 7:
                return "Lipiec";
            case 8:
                return "Sierpień";
            case 9:
                return "Wrzesień";
            case 10:
                return "Październik";
            case 11:
                return "Listopad";
            case 12:
                return "Grudzień";
            default:
                return "Invalid month";
        }
    }
}
