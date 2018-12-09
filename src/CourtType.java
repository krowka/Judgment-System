public enum CourtType {
    COMMON, // sąd powszechny
    SUPREME, // Sąd Najwyższy
    ADMINISTRATIVE, // sąd administracyjny
    CONSTITUTIONAL_TRIBUNAL,// Trybunał Konstytucyjny
    NATIONAL_APPEAL_CHAMBER; // Krajowa Izba Odwoławcza

    public String toString(){
        switch(this){
            case COMMON:
                return "Sąd powszechny";
            case SUPREME:
                return "Sąd najwyższy";
            case ADMINISTRATIVE:
                return "Sąd administracyjny";
            case CONSTITUTIONAL_TRIBUNAL:
                return "Trybunał konstytucyjny";
            case NATIONAL_APPEAL_CHAMBER:
                return "Krajowa Izba Odwłowacza";
        }
        return null;
    }
}
