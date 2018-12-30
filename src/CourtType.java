public enum CourtType {
    COMMON, // sąd powszechny
    SUPREME, // Sąd Najwyższy
    PROVINCIAL_ADMINISTRATIVE, // wojewodzki sąd administracyjny
    SUPREME_ADMINISTRATIVE, // wojewodzki sąd administracyjny
    CONSTITUTIONAL_TRIBUNAL,// Trybunał Konstytucyjny
    NATIONAL_APPEAL_CHAMBER; // Krajowa Izba Odwoławcza

    private int number = 1; // number of cases in specific court type

    public void increment() {
        this.number++;
    }

    public int getNumber() {
        return this.number;
    }

    public String toString() {
        switch (this) {
            case COMMON:
                return "Sąd powszechny";
            case SUPREME:
                return "Sąd najwyższy";
            case PROVINCIAL_ADMINISTRATIVE:
                return "Wojewódzki sąd administracyjny";
            case SUPREME_ADMINISTRATIVE:
                return "Naczelny sąd administracyjny";
            case CONSTITUTIONAL_TRIBUNAL:
                return "Trybunał konstytucyjny";
            case NATIONAL_APPEAL_CHAMBER:
                return "Krajowa Izba Odwłowacza";
        }
        return null;
    }
}
