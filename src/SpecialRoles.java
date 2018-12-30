public enum SpecialRoles {
    PRESIDING_JUDGE, // przewodniczacy składu sędziowskiego ;
    REPORTING_JUDGE, // sędzia sprawozdawca
    REASONS_FOR_JUDGMENT_AUTHOR; // autor uzasadnienia

    public String toString() {
        switch (this) {
            case PRESIDING_JUDGE:
                return "Sędzia przewodniczący";
            case REPORTING_JUDGE:
                return "Sędzia sprawozdawca";
            case REASONS_FOR_JUDGMENT_AUTHOR:
                return "Autor uzasadnienia";
        }
        return null;
    }
//    private String text;
//   SpecialRoles(String text){
//        this.text = text;
//    }
//
//    public String getText() {
//        return this.text;
//    }
//
//    public static SpecialRoles toSpecialRole(String string){
//        for (SpecialRoles sr : SpecialRoles.values()) {
//            if (sr.text.equalsIgnoreCase(string)) {
//                return sr;
//            }
//        }
//        return null;
}
