package core;

public class Literal {
    private static String hexValue = "";
    private static String MemoryValue = "";
    public static String literal ="";
    private static int bytes;

    public Literal(String value, String address) {
        Literal.setMemoryValue(address);
        Literal.setLiteral(value);

    }

    public static void setLiteral(String literal) {
        Literal.literal = literal;
    }

    public static String getLiteral() {
        return literal;
    }

    public static void setMemoryValue(String memoryValue) {
        MemoryValue = memoryValue;
    }

    public static String getHexValue() {
        return hexValue;
    }

    public static void calculateLiteralLength(){
        if(literal.startsWith("=x") || literal.startsWith("=X"))
            bytes = (literal.length() - 4);
        bytes = (3);
    }

    public static void literalToHex (){
        String hexaLiteral ="";
        String zeroes = "000000";
        StringBuilder builder = new StringBuilder();

        if(literal.startsWith("=c") || literal.startsWith("=C")) {
            literal = literal.substring(3, literal.length()-1);
            int len = literal.length();
            if(len < 3){
                builder.append(zeroes.substring(0, 6 - (2 * len)));
            }
            char[] characters = literal.toCharArray();
            for (char c : characters) {
                int i = (int) c;
                builder.append(Integer.toHexString(i).toUpperCase());
            }
            hexValue.concat(builder.toString());
            System.out.println(builder.toString());
            return;
        }

        else if(literal.startsWith("=w") || literal.startsWith("=W")){
            literal = literal.substring(3,literal.length()-1);
            int decimal =  (Integer.parseInt(literal));
            hexaLiteral = Integer.toHexString(decimal);
            int len = (hexaLiteral).length();
            if(len < 6){
                builder.append(zeroes.substring(0, 6 - len));
            }
            builder.append(hexaLiteral);
            hexValue = builder.toString();
            return;
        }

        hexValue = (literal.substring(3, literal.length()-1));
    }
}
