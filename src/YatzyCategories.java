public enum YatzyCategories {
    ONES("Enere"),TWOES("Toere"), THREES("Treere"), FOURS("Firere"), FIVES("Femmere"), SIXES("Seksere"),
    BONUS("Bonus"), ONE_PAIR("1 par"), TWO_PAIRS("2 par"), THREE_PAIRS("3 par"), THREE_OF_A_KIND("3 ens"),
    FOUR_OF_A_KIND("4 ens"), TWO_TIMES_THREE_OF_A_KIND("2x3 ens"), SMALL("1,2,3,4,5"), LARGE("2,3,4,5,6"),
    ROYAL("1,2,3,4,5,6"), FULL_HOUSE("Fuldt Hus"), CHANCE("Chance"), YATZY("Yatzy"), TOTAL("Total");

    private String fieldName;

    YatzyCategories(String fieldName) {
        this.fieldName = fieldName;
    }

    public String toString(){
        return fieldName;
    }
}
